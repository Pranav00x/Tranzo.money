// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

/* solhint-disable avoid-low-level-calls */

import {ECDSA} from "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";
import {MessageHashUtils} from "@openzeppelin/contracts/utils/cryptography/MessageHashUtils.sol";
import {IERC20} from "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import {SafeERC20} from "@openzeppelin/contracts/token/ERC20/utils/SafeERC20.sol";
import {Ownable} from "@openzeppelin/contracts/access/Ownable.sol";

import {IPaymaster} from "account-abstraction/interfaces/IPaymaster.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";
import {PackedUserOperation} from "account-abstraction/interfaces/PackedUserOperation.sol";
import {UserOperationLib} from "account-abstraction/core/UserOperationLib.sol";
import {
    _packValidationData,
    SIG_VALIDATION_FAILED,
    SIG_VALIDATION_SUCCESS
} from "account-abstraction/core/Helpers.sol";

/**
 * @title TranzoPaymaster
 * @author Tranzo Team
 * @notice Verifying paymaster that sponsors gas for Tranzo users.
 *
 * Two operating modes (encoded in paymasterData):
 *
 *   Mode 0 — Sponsored: Tranzo backend pays the gas entirely. Users need no ETH.
 *   Mode 1 — USDC deduction: Gas cost is deducted from the user's USDC balance
 *             held in the paymaster escrow.
 *
 * In both cases the Tranzo backend signs a {userOpHash, mode, validUntil, validAfter}
 * tuple before the UserOp is submitted. The paymaster validates this signature
 * on-chain so a rogue bundler cannot fabricate authorisations.
 *
 * PaymasterData layout (packed):
 *   [0]      uint8  mode        (0 = sponsored, 1 = USDC deduction)
 *   [1..6]   uint48 validUntil
 *   [7..12]  uint48 validAfter
 *   [13..77] bytes  backendSig  (65 bytes ECDSA)
 *
 * PostOp context layout (when mode == 1):
 *   abi.encode(address sender, uint256 gasPriceAtValidation)
 */
contract TranzoPaymaster is IPaymaster, Ownable {
    using ECDSA for bytes32;
    using MessageHashUtils for bytes32;
    using SafeERC20 for IERC20;
    using UserOperationLib for PackedUserOperation;

    // ─── Constants ───────────────────────────────────────────────

    uint8 public constant MODE_SPONSORED = 0;
    uint8 public constant MODE_USDC = 1;

    /// @dev paymasterData layout offsets
    uint256 private constant MODE_OFFSET = 0;
    uint256 private constant VALID_UNTIL_OFFSET = 1;
    uint256 private constant VALID_AFTER_OFFSET = 7;
    uint256 private constant SIG_OFFSET = 13;
    uint256 private constant SIG_LENGTH = 65;
    uint256 private constant MIN_PAYMASTER_DATA_LENGTH = SIG_OFFSET + SIG_LENGTH; // 78 bytes

    // ─── Storage ─────────────────────────────────────────────────

    IEntryPoint public immutable entryPoint;

    /// @notice The backend signer address. Backend signs authorisation tuples.
    address public verifyingSigner;

    /// @notice USDC (or equivalent stablecoin) token used for Mode 1.
    IERC20 public usdcToken;

    /// @dev user => USDC balance deposited with this paymaster (in token units).
    mapping(address => uint256) public usdcBalances;

    // ─── Events ──────────────────────────────────────────────────

    event GasSponsored(address indexed sender, bytes32 indexed userOpHash);
    event GasPaidInToken(address indexed sender, bytes32 indexed userOpHash, uint256 tokenAmount);
    event VerifyingSignerUpdated(address indexed oldSigner, address indexed newSigner);
    event UsdcTokenUpdated(address indexed oldToken, address indexed newToken);
    event UsdcDeposited(address indexed user, uint256 amount);
    event UsdcWithdrawn(address indexed user, uint256 amount);
    event EntryPointDeposit(uint256 amount);
    event EntryPointWithdrawal(address indexed to, uint256 amount);

    // ─── Errors ──────────────────────────────────────────────────

    error InvalidPaymasterDataLength(uint256 provided, uint256 required);
    error InvalidMode(uint8 mode);
    error InvalidSignature();
    error InsufficientUsdcBalance(address user, uint256 required, uint256 available);
    error ZeroAddress();
    error WithdrawFailed();

    // ─── Constructor ─────────────────────────────────────────────

    /**
     * @param _entryPoint      ERC-4337 EntryPoint.
     * @param _verifyingSigner Backend hot-wallet that signs authorisation tuples.
     * @param _usdcToken       USDC token address (may be address(0) if Mode 1 never used).
     */
    constructor(
        IEntryPoint _entryPoint,
        address _verifyingSigner,
        address _usdcToken,
        address _owner
    ) Ownable(_owner) {
        if (address(_entryPoint) == address(0)) revert ZeroAddress();
        if (_verifyingSigner == address(0)) revert ZeroAddress();
        entryPoint = _entryPoint;
        verifyingSigner = _verifyingSigner;
        usdcToken = IERC20(_usdcToken);
    }

    // ─── IPaymaster ───────────────────────────────────────────────

    /**
     * @inheritdoc IPaymaster
     * @dev Validates the backend signature and returns the time-range packed into
     *      validationData. For Mode 1 returns a context that postOp will use to
     *      deduct USDC.
     */
    function validatePaymasterUserOp(
        PackedUserOperation calldata userOp,
        bytes32 userOpHash,
        uint256 maxCost
    ) external override returns (bytes memory context, uint256 validationData) {
        _requireFromEntryPoint();

        bytes calldata paymasterData = _paymasterData(userOp);

        if (paymasterData.length < MIN_PAYMASTER_DATA_LENGTH) {
            revert InvalidPaymasterDataLength(paymasterData.length, MIN_PAYMASTER_DATA_LENGTH);
        }

        uint8 mode = uint8(paymasterData[MODE_OFFSET]);
        uint48 validUntil = uint48(bytes6(paymasterData[VALID_UNTIL_OFFSET:VALID_UNTIL_OFFSET + 6]));
        uint48 validAfter = uint48(bytes6(paymasterData[VALID_AFTER_OFFSET:VALID_AFTER_OFFSET + 6]));
        bytes calldata sig = paymasterData[SIG_OFFSET:SIG_OFFSET + SIG_LENGTH];

        if (mode != MODE_SPONSORED && mode != MODE_USDC) revert InvalidMode(mode);

        // Verify backend signature over (userOpHash, mode, validUntil, validAfter, address(this))
        bytes32 digest = _buildDigest(userOpHash, mode, validUntil, validAfter);
        address recovered = digest.toEthSignedMessageHash().recover(sig);
        if (recovered != verifyingSigner) {
            return ("", _packValidationData(true, validUntil, validAfter));
        }

        // Mode 1: check user has enough USDC escrowed
        if (mode == MODE_USDC) {
            address sender = userOp.sender;
            uint256 usdcRequired = _estimateUsdcCost(maxCost);
            if (usdcBalances[sender] < usdcRequired) {
                revert InsufficientUsdcBalance(sender, usdcRequired, usdcBalances[sender]);
            }
            context = abi.encode(sender, maxCost, usdcRequired);
        }

        validationData = _packValidationData(false, validUntil, validAfter);
    }

    /**
     * @inheritdoc IPaymaster
     * @dev For Mode 1: deduct actual USDC cost from user's escrowed balance.
     */
    function postOp(
        PostOpMode mode,
        bytes calldata context,
        uint256 actualGasCost,
        uint256 actualUserOpFeePerGas
    ) external override {
        _requireFromEntryPoint();

        if (context.length == 0) {
            // Mode 0 (sponsored): nothing to do
            return;
        }

        (address sender, , ) = abi.decode(context, (address, uint256, uint256));

        if (mode == PostOpMode.postOpReverted) {
            // postOp itself reverted — do nothing to avoid re-entry loops
            return;
        }

        // Deduct actual cost in USDC
        uint256 usdcDeduction = _estimateUsdcCost(actualGasCost);
        uint256 available = usdcBalances[sender];
        if (usdcDeduction > available) {
            usdcDeduction = available; // drain but do not underflow
        }
        usdcBalances[sender] = available - usdcDeduction;

        bytes32 userOpHash; // Not available in postOp but emit what we have
        emit GasPaidInToken(sender, userOpHash, usdcDeduction);
    }

    // ─── USDC escrow management ───────────────────────────────────

    /**
     * @notice Deposit USDC for gas payments (Mode 1). User must approve this contract first.
     * @param amount Amount of USDC to deposit.
     */
    function depositUsdc(uint256 amount) external {
        usdcToken.safeTransferFrom(msg.sender, address(this), amount);
        usdcBalances[msg.sender] += amount;
        emit UsdcDeposited(msg.sender, amount);
    }

    /**
     * @notice Withdraw previously deposited USDC.
     * @param amount Amount to withdraw.
     */
    function withdrawUsdc(uint256 amount) external {
        uint256 bal = usdcBalances[msg.sender];
        if (bal < amount) revert InsufficientUsdcBalance(msg.sender, amount, bal);
        usdcBalances[msg.sender] = bal - amount;
        usdcToken.safeTransfer(msg.sender, amount);
        emit UsdcWithdrawn(msg.sender, amount);
    }

    // ─── EntryPoint stake/deposit management ─────────────────────

    /**
     * @notice Deposit ETH into the EntryPoint to cover future gas sponsorships.
     */
    function depositToEntryPoint() external payable onlyOwner {
        entryPoint.depositTo{value: msg.value}(address(this));
        emit EntryPointDeposit(msg.value);
    }

    /**
     * @notice Withdraw ETH deposit from the EntryPoint.
     * @param withdrawAddress Recipient.
     * @param amount          Amount to withdraw.
     */
    function withdrawFromEntryPoint(address payable withdrawAddress, uint256 amount)
        external
        onlyOwner
    {
        if (withdrawAddress == address(0)) revert ZeroAddress();
        entryPoint.withdrawTo(withdrawAddress, amount);
        emit EntryPointWithdrawal(withdrawAddress, amount);
    }

    /**
     * @notice Add stake to the EntryPoint (required for paymasters).
     * @param unstakeDelaySec Delay before unstake takes effect.
     */
    function addStake(uint32 unstakeDelaySec) external payable onlyOwner {
        entryPoint.addStake{value: msg.value}(unstakeDelaySec);
    }

    /**
     * @notice Unlock stake (begins unstake delay).
     */
    function unlockStake() external onlyOwner {
        entryPoint.unlockStake();
    }

    /**
     * @notice Withdraw unlocked stake.
     * @param withdrawAddress Recipient.
     */
    function withdrawStake(address payable withdrawAddress) external onlyOwner {
        entryPoint.withdrawStake(withdrawAddress);
    }

    // ─── Admin ────────────────────────────────────────────────────

    /**
     * @notice Update the backend signer address.
     */
    function setVerifyingSigner(address newSigner) external onlyOwner {
        if (newSigner == address(0)) revert ZeroAddress();
        emit VerifyingSignerUpdated(verifyingSigner, newSigner);
        verifyingSigner = newSigner;
    }

    /**
     * @notice Update the USDC token address.
     */
    function setUsdcToken(address newToken) external onlyOwner {
        emit UsdcTokenUpdated(address(usdcToken), newToken);
        usdcToken = IERC20(newToken);
    }

    // ─── Receive ETH ─────────────────────────────────────────────

    receive() external payable {}

    // ─── Internal helpers ─────────────────────────────────────────

    function _requireFromEntryPoint() internal view {
        require(msg.sender == address(entryPoint), "Paymaster: not from EntryPoint");
    }

    function _buildDigest(
        bytes32 userOpHash,
        uint8 mode,
        uint48 validUntil,
        uint48 validAfter
    ) internal view returns (bytes32) {
        return keccak256(abi.encode(userOpHash, mode, validUntil, validAfter, address(this)));
    }

    /**
     * @dev Rough conversion from ETH gas cost to USDC units.
     *      A production implementation would use a Chainlink oracle; here we use
     *      a fixed exchange rate settable by the owner to keep the contract simple.
     *      Rate: 1 USDC (1e6 units) per `ethToUsdcRate` wei of ETH cost.
     */
    uint256 public ethToUsdcRate = 0.0003e18; // default: 1 USDC ≈ 3000 USD, gas in ETH

    function setEthToUsdcRate(uint256 rate) external onlyOwner {
        ethToUsdcRate = rate;
    }

    function _estimateUsdcCost(uint256 gasCostWei) internal view returns (uint256) {
        if (ethToUsdcRate == 0) return 0;
        // usdcUnits = (gasCostWei * 1e6) / ethToUsdcRate
        return (gasCostWei * 1e6) / ethToUsdcRate;
    }

    /// @dev Extract paymasterAndData[52:] — the bytes after (paymaster address + validation/postop gas fields).
    function _paymasterData(PackedUserOperation calldata userOp)
        internal
        pure
        returns (bytes calldata)
    {
        // paymasterAndData = [20 bytes addr][16 bytes gas fields][N bytes data]
        return userOp.paymasterAndData[UserOperationLib.PAYMASTER_DATA_OFFSET:];
    }
}
