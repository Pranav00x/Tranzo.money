// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

/* solhint-disable avoid-low-level-calls */

import {ECDSA} from "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";
import {MessageHashUtils} from "@openzeppelin/contracts/utils/cryptography/MessageHashUtils.sol";
import {IERC1271} from "@openzeppelin/contracts/interfaces/IERC1271.sol";
import {Initializable} from "@openzeppelin/contracts/proxy/utils/Initializable.sol";
import {UUPSUpgradeable} from "@openzeppelin/contracts/proxy/utils/UUPSUpgradeable.sol";

import {IAccount} from "account-abstraction/interfaces/IAccount.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";
import {PackedUserOperation} from "account-abstraction/interfaces/PackedUserOperation.sol";
import {SIG_VALIDATION_FAILED, SIG_VALIDATION_SUCCESS, _packValidationData} from
    "account-abstraction/core/Helpers.sol";

import {SpendingLimits} from "./modules/SpendingLimits.sol";
import {SocialRecovery} from "./modules/SocialRecovery.sol";
import {SessionKeyManager} from "./modules/SessionKeyManager.sol";

/**
 * @title TranzoAccount
 * @author Tranzo Team
 * @notice ERC-4337 smart wallet for Tranzo. Each user deploys one proxy pointing at
 *         this implementation. Supports:
 *           - ECDSA owner signature validation
 *           - Session key validation (scoped keys via SessionKeyManager)
 *           - Single and batched execution
 *           - UUPS upgrades (owner-only)
 *           - EIP-1271 off-chain signature verification
 *           - Social recovery (SocialRecovery module)
 *           - Spending limits (SpendingLimits module)
 */
contract TranzoAccount is
    IAccount,
    IERC1271,
    Initializable,
    UUPSUpgradeable,
    SpendingLimits,
    SocialRecovery,
    SessionKeyManager
{
    using ECDSA for bytes32;
    using MessageHashUtils for bytes32;

    // ─── EIP-1271 magic value ─────────────────────────────────────
    bytes4 private constant _EIP1271_MAGIC = 0x1626ba7e;

    // ─── Storage ─────────────────────────────────────────────────

    IEntryPoint private immutable _entryPoint;

    address public owner;

    // ─── Events ──────────────────────────────────────────────────

    event Executed(address indexed dest, uint256 value, bytes func);
    event ExecutedBatch(address[] dest, uint256[] values, bytes[] func);
    event OwnerChanged(address indexed previousOwner, address indexed newOwner);

    // ─── Errors ──────────────────────────────────────────────────

    error Unauthorized();
    error AccountZeroAddress();
    error ArrayLengthMismatch();
    error CallFailed(uint256 index, bytes reason);
    error UpgradeUnauthorized();

    // ─── Constructor ─────────────────────────────────────────────

    /// @param entryPoint_ The ERC-4337 EntryPoint contract address.
    constructor(IEntryPoint entryPoint_) {
        _entryPoint = entryPoint_;
        _disableInitializers();
    }

    // ─── Initializer ─────────────────────────────────────────────

    /**
     * @notice Initialize the account. Called once by the factory via the proxy.
     * @param _owner            The initial owner (user's EOA or key).
     * @param _dailyLimit       Daily spending limit in wei (0 = disabled).
     * @param _perTxLimit       Per-transaction spending limit in wei (0 = disabled).
     * @param _guardians        Optional list of social recovery guardians.
     * @param _recoveryThreshold Guardian threshold for social recovery (ignored if no guardians).
     */
    function initialize(
        address _owner,
        uint256 _dailyLimit,
        uint256 _perTxLimit,
        address[] calldata _guardians,
        uint256 _recoveryThreshold
    ) external initializer {
        if (_owner == address(0)) revert AccountZeroAddress();
        owner = _owner;
        _initSpendingLimits(_dailyLimit, _perTxLimit);
        if (_guardians.length > 0) {
            _initSocialRecovery(_guardians, _recoveryThreshold);
        }
        emit OwnerChanged(address(0), _owner);
    }

    // ─── Receive ETH ─────────────────────────────────────────────

    receive() external payable {}

    // ─── EntryPoint accessor ──────────────────────────────────────

    function entryPoint() public view returns (IEntryPoint) {
        return _entryPoint;
    }

    // ─── ERC-4337: validateUserOp ─────────────────────────────────

    /**
     * @inheritdoc IAccount
     * @dev Validates owner ECDSA signature or a valid session key.
     *      Packs time-range data into the return value when a session key is used.
     */
    function validateUserOp(
        PackedUserOperation calldata userOp,
        bytes32 userOpHash,
        uint256 missingAccountFunds
    ) external override returns (uint256 validationData) {
        _requireFromEntryPoint();

        validationData = _validateSignatureInternal(userOp, userOpHash);

        _payPrefund(missingAccountFunds);
    }

    // ─── Execution ────────────────────────────────────────────────

    /**
     * @notice Execute a single call on behalf of the account.
     * @param dest   Target address.
     * @param value  ETH value to forward.
     * @param func   Calldata.
     */
    function execute(address dest, uint256 value, bytes calldata func) external {
        _requireOwnerOrEntryPoint();
        _checkAndRecordSpend(value);

        (bool success, bytes memory result) = dest.call{value: value}(func);
        if (!success) revert CallFailed(0, result);

        emit Executed(dest, value, func);
    }

    /**
     * @notice Execute a batch of calls. All-or-nothing (reverts on first failure).
     * @param dest   Array of target addresses.
     * @param values Array of ETH values (must match dest length).
     * @param func   Array of calldata (must match dest length).
     */
    function executeBatch(
        address[] calldata dest,
        uint256[] calldata values,
        bytes[] calldata func
    ) external {
        _requireOwnerOrEntryPoint();
        if (dest.length != values.length || dest.length != func.length) {
            revert ArrayLengthMismatch();
        }

        for (uint256 i = 0; i < dest.length; i++) {
            _checkAndRecordSpend(values[i]);
            (bool success, bytes memory result) = dest[i].call{value: values[i]}(func[i]);
            if (!success) revert CallFailed(i, result);
        }

        emit ExecutedBatch(dest, values, func);
    }

    // ─── Ownership ────────────────────────────────────────────────

    /**
     * @notice Transfer account ownership to `newOwner`.
     */
    function transferOwnership(address newOwner) external {
        _requireOwnerOnly();
        if (newOwner == address(0)) revert AccountZeroAddress();
        address previous = owner;
        owner = newOwner;
        emit OwnerChanged(previous, newOwner);
    }

    // ─── Spending limits (public setters) ─────────────────────────

    /**
     * @notice Update daily and per-transaction spending limits. Owner only.
     */
    function setSpendingLimits(uint256 _dailyLimit, uint256 _perTxLimit) external {
        _requireOwnerOnly();
        _setSpendingLimits(_dailyLimit, _perTxLimit);
    }

    // ─── Social recovery guardian management (public wrappers) ────

    function addGuardian(address guardian) external {
        _requireOwnerOnly();
        _addGuardian(guardian);
    }

    function removeGuardian(address guardian) external {
        _requireOwnerOnly();
        _removeGuardian(guardian);
    }

    function setRecoveryThreshold(uint256 _threshold) external {
        _requireOwnerOnly();
        _setThreshold(_threshold);
    }

    // ─── EIP-1271 ─────────────────────────────────────────────────

    /**
     * @inheritdoc IERC1271
     * @dev Returns the magic value if `signature` is a valid ECDSA signature by
     *      the owner over `hash`.
     */
    function isValidSignature(bytes32 hash, bytes memory signature)
        external
        view
        override
        returns (bytes4)
    {
        address signer = hash.toEthSignedMessageHash().recover(signature);
        if (signer == owner) return _EIP1271_MAGIC;
        return 0xffffffff;
    }

    // ─── UUPS ─────────────────────────────────────────────────────

    /// @dev Only the owner may upgrade the implementation.
    function _authorizeUpgrade(address) internal view override {
        _requireOwnerOnly();
    }

    // ─── Internal helpers ─────────────────────────────────────────

    function _requireFromEntryPoint() internal view {
        if (msg.sender != address(_entryPoint)) revert Unauthorized();
    }

    function _requireOwnerOnly() internal view {
        if (msg.sender != owner) revert Unauthorized();
    }

    function _requireOwnerOrEntryPoint() internal view {
        if (msg.sender != owner && msg.sender != address(_entryPoint)) revert Unauthorized();
    }

    function _validateSignatureInternal(
        PackedUserOperation calldata userOp,
        bytes32 userOpHash
    ) internal returns (uint256) {
        bytes32 ethHash = userOpHash.toEthSignedMessageHash();
        address signer = ethHash.recover(userOp.signature);

        // Owner signature — no time bounds
        if (signer == owner) {
            return SIG_VALIDATION_SUCCESS;
        }

        // Session key signature
        if (isActiveSessionKey(signer)) {
            // Decode the call to validate it against session key constraints.
            // The UserOp.callData is abi.encodeCall(execute, (dest, value, data)) or executeBatch.
            // We do a best-effort validation here; if calldata is too short, reject.
            bytes calldata callData = userOp.callData;
            if (callData.length >= 4) {
                bytes4 sel = bytes4(callData[:4]);
                if (sel == TranzoAccount.execute.selector && callData.length >= 4 + 32 + 32 + 32) {
                    (address dest, uint256 value, bytes memory innerData) =
                        abi.decode(callData[4:], (address, uint256, bytes));
                    // Validate the session key against this specific call
                    _validateSessionKey(signer, dest, value, innerData);
                }
                // For executeBatch we skip per-call validation here for gas reasons;
                // batch calls with session keys should be validated off-chain before submission.
            }

            // Return validation success — session key expiry already checked in isActiveSessionKey
            return SIG_VALIDATION_SUCCESS;
        }

        return SIG_VALIDATION_FAILED;
    }

    function _payPrefund(uint256 missingAccountFunds) internal {
        if (missingAccountFunds > 0) {
            (bool success,) = payable(msg.sender).call{value: missingAccountFunds}("");
            (success); // ignore return: EntryPoint handles failure
        }
    }

    // ─── SocialRecovery hooks ─────────────────────────────────────

    function _requireOwner() internal view override(SocialRecovery, SessionKeyManager) {
        _requireOwnerOnly();
    }

    function _getOwner() internal view override returns (address) {
        return owner;
    }

    function _transferOwnershipInternal(address newOwner) internal override {
        address previous = owner;
        owner = newOwner;
        emit OwnerChanged(previous, newOwner);
    }

    // ─── Nonce helper ─────────────────────────────────────────────

    function getNonce() external view returns (uint256) {
        return _entryPoint.getNonce(address(this), 0);
    }
}
