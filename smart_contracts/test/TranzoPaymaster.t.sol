// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {Test, console} from "forge-std/Test.sol";
import {ECDSA} from "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";
import {MessageHashUtils} from "@openzeppelin/contracts/utils/cryptography/MessageHashUtils.sol";
import {ERC20} from "@openzeppelin/contracts/token/ERC20/ERC20.sol";

import {TranzoPaymaster} from "../src/TranzoPaymaster.sol";
import {IPaymaster} from "account-abstraction/interfaces/IPaymaster.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";
import {PackedUserOperation} from "account-abstraction/interfaces/PackedUserOperation.sol";

// ─── Mocks ───────────────────────────────────────────────────

contract MockEntryPointForPaymaster {
    mapping(address => uint256) public deposits;
    mapping(address => uint256) public nonces;

    function getNonce(address account, uint192) external view returns (uint256) {
        return nonces[account];
    }

    function depositTo(address account) external payable {
        deposits[account] += msg.value;
    }

    function addStake(uint32) external payable {}
    function unlockStake() external {}
    function withdrawStake(address payable to) external {
        uint256 bal = address(this).balance;
        (bool ok,) = to.call{value: bal}("");
        require(ok);
    }
    function withdrawTo(address payable to, uint256 amount) external {
        (bool ok,) = to.call{value: amount}("");
        require(ok);
    }

    receive() external payable {}
}

contract MockUSDC is ERC20 {
    constructor() ERC20("USD Coin", "USDC") {}

    function mint(address to, uint256 amount) external {
        _mint(to, amount);
    }

    function decimals() public pure override returns (uint8) {
        return 6;
    }
}

// ─── Helper to build paymasterData ───────────────────────────

library PaymasterDataBuilder {
    function build(
        uint8 mode,
        uint48 validUntil,
        uint48 validAfter,
        bytes memory sig
    ) internal pure returns (bytes memory) {
        return abi.encodePacked(mode, validUntil, validAfter, sig);
    }
}

// ─── Tests ───────────────────────────────────────────────────

contract TranzoPaymasterTest is Test {
    using ECDSA for bytes32;
    using MessageHashUtils for bytes32;
    using PaymasterDataBuilder for *;

    MockEntryPointForPaymaster public ep;
    TranzoPaymaster public paymaster;
    MockUSDC public usdc;

    uint256 public signerPk = 0x5133;
    address public signerAddr;
    address public paymasterOwner = address(0x0777);
    address public user = address(0x1234);

    // ─── Setup ───────────────────────────────────────────────────

    function setUp() public {
        signerAddr = vm.addr(signerPk);
        ep = new MockEntryPointForPaymaster();
        usdc = new MockUSDC();

        paymaster = new TranzoPaymaster(
            IEntryPoint(address(ep)),
            signerAddr,
            address(usdc),
            paymasterOwner
        );

        // Fund paymaster
        vm.deal(address(paymaster), 10 ether);
        vm.deal(address(ep), 10 ether);
    }

    // ─── Helpers ─────────────────────────────────────────────────

    function _buildOp(address sender, bytes memory pmData)
        internal
        pure
        returns (PackedUserOperation memory)
    {
        return PackedUserOperation({
            sender: sender,
            nonce: 0,
            initCode: "",
            callData: "",
            accountGasLimits: bytes32(0),
            preVerificationGas: 0,
            gasFees: bytes32(0),
            paymasterAndData: _buildPmAndData(address(0), pmData), // address filled below
            signature: ""
        });
    }

    function _buildOpWithPaymaster(address pm, address sender, bytes memory pmData)
        internal
        pure
        returns (PackedUserOperation memory)
    {
        return PackedUserOperation({
            sender: sender,
            nonce: 0,
            initCode: "",
            callData: "",
            accountGasLimits: bytes32(0),
            preVerificationGas: 0,
            gasFees: bytes32(0),
            paymasterAndData: _buildPmAndData(pm, pmData),
            signature: ""
        });
    }

    /// @dev Build paymasterAndData: [20 bytes addr][16 bytes validationGas][16 bytes postopGas][N bytes custom data]
    ///      Total prefix = 52 bytes = PAYMASTER_DATA_OFFSET
    function _buildPmAndData(address pm, bytes memory data)
        internal
        pure
        returns (bytes memory)
    {
        return abi.encodePacked(pm, bytes16(0), bytes16(0), data);
    }

    function _signForPaymaster(
        bytes32 userOpHash,
        uint8 mode,
        uint48 validUntil,
        uint48 validAfter
    ) internal view returns (bytes memory sig) {
        bytes32 digest = keccak256(
            abi.encode(userOpHash, mode, validUntil, validAfter, address(paymaster))
        );
        bytes32 ethDigest = digest.toEthSignedMessageHash();
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(signerPk, ethDigest);
        sig = abi.encodePacked(r, s, v);
    }

    // ─── Mode 0: Sponsored ───────────────────────────────────────

    function test_validatePaymasterUserOp_sponsored_validSig() public {
        bytes32 userOpHash = keccak256("op1");
        uint48 validUntil = uint48(block.timestamp + 1 hours);
        uint48 validAfter = 0;
        uint8 mode = 0;

        bytes memory sig = _signForPaymaster(userOpHash, mode, validUntil, validAfter);
        bytes memory pmData = abi.encodePacked(mode, validUntil, validAfter, sig);

        PackedUserOperation memory op = _buildOpWithPaymaster(
            address(paymaster), user, pmData
        );

        vm.prank(address(ep));
        (bytes memory context, uint256 validationData) =
            paymaster.validatePaymasterUserOp(op, userOpHash, 0.01 ether);

        // context should be empty for mode 0
        assertEq(context.length, 0);
        // validationData should not indicate signature failure (low bit = 0)
        assertEq(validationData & 1, 0);
    }

    function test_validatePaymasterUserOp_sponsored_invalidSig() public {
        bytes32 userOpHash = keccak256("op1");
        uint48 validUntil = uint48(block.timestamp + 1 hours);
        uint48 validAfter = 0;
        uint8 mode = 0;

        // Sign with wrong key
        bytes32 digest = keccak256(
            abi.encode(userOpHash, mode, validUntil, validAfter, address(paymaster))
        );
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(0xBAD, digest.toEthSignedMessageHash());
        bytes memory sig = abi.encodePacked(r, s, v);
        bytes memory pmData = abi.encodePacked(mode, validUntil, validAfter, sig);

        PackedUserOperation memory op = _buildOpWithPaymaster(
            address(paymaster), user, pmData
        );

        vm.prank(address(ep));
        (, uint256 validationData) =
            paymaster.validatePaymasterUserOp(op, userOpHash, 0);

        // low 160 bits should be 1 (SIG_VALIDATION_FAILED aggregator address)
        assertEq(uint160(validationData), 1);
    }

    function test_validatePaymasterUserOp_revertsIfNotEntryPoint() public {
        PackedUserOperation memory op = _buildOpWithPaymaster(
            address(paymaster), user, bytes(abi.encodePacked(uint8(0)))
        );
        vm.expectRevert();
        paymaster.validatePaymasterUserOp(op, bytes32(0), 0);
    }

    function test_validatePaymasterUserOp_revertsIfDataTooShort() public {
        bytes memory tooShort = "short";
        PackedUserOperation memory op = _buildOpWithPaymaster(
            address(paymaster), user, tooShort
        );
        vm.prank(address(ep));
        vm.expectRevert();
        paymaster.validatePaymasterUserOp(op, bytes32(0), 0);
    }

    // ─── Mode 1: USDC deduction ───────────────────────────────────

    function test_validatePaymasterUserOp_usdc_sufficientBalance() public {
        // Give user some escrowed USDC
        usdc.mint(user, 1000e6);
        vm.prank(user);
        usdc.approve(address(paymaster), type(uint256).max);
        vm.prank(user);
        paymaster.depositUsdc(500e6);

        bytes32 userOpHash = keccak256("op2");
        uint48 validUntil = uint48(block.timestamp + 1 hours);
        uint48 validAfter = 0;
        uint8 mode = 1;

        bytes memory sig = _signForPaymaster(userOpHash, mode, validUntil, validAfter);
        bytes memory pmData = abi.encodePacked(mode, validUntil, validAfter, sig);
        PackedUserOperation memory op = _buildOpWithPaymaster(
            address(paymaster), user, pmData
        );

        vm.prank(address(ep));
        (bytes memory context, uint256 validationData) =
            paymaster.validatePaymasterUserOp(op, userOpHash, 0.001 ether);

        assertTrue(context.length > 0, "context should be non-empty for USDC mode");
        assertEq(validationData & 1, 0, "should be valid signature");
    }

    function test_validatePaymasterUserOp_usdc_insufficientBalance() public {
        // User has zero USDC escrowed
        bytes32 userOpHash = keccak256("op3");
        uint48 validUntil = uint48(block.timestamp + 1 hours);
        uint8 mode = 1;

        bytes memory sig = _signForPaymaster(userOpHash, mode, validUntil, 0);
        bytes memory pmData = abi.encodePacked(mode, validUntil, uint48(0), sig);
        PackedUserOperation memory op = _buildOpWithPaymaster(
            address(paymaster), user, pmData
        );

        vm.prank(address(ep));
        vm.expectRevert();
        paymaster.validatePaymasterUserOp(op, userOpHash, 1 ether);
    }

    // ─── USDC escrow ─────────────────────────────────────────────

    function test_depositUsdc_updatesBalance() public {
        usdc.mint(user, 100e6);
        vm.prank(user);
        usdc.approve(address(paymaster), type(uint256).max);
        vm.prank(user);
        paymaster.depositUsdc(100e6);
        assertEq(paymaster.usdcBalances(user), 100e6);
    }

    function test_withdrawUsdc_updatesBalance() public {
        usdc.mint(user, 100e6);
        vm.startPrank(user);
        usdc.approve(address(paymaster), type(uint256).max);
        paymaster.depositUsdc(100e6);
        paymaster.withdrawUsdc(50e6);
        vm.stopPrank();

        assertEq(paymaster.usdcBalances(user), 50e6);
        assertEq(usdc.balanceOf(user), 50e6);
    }

    function test_withdrawUsdc_revertsOnInsufficient() public {
        vm.prank(user);
        vm.expectRevert();
        paymaster.withdrawUsdc(1e6);
    }

    // ─── Admin ────────────────────────────────────────────────────

    function test_setVerifyingSigner_ownerOnly() public {
        address newSigner = address(0x999);
        vm.prank(paymasterOwner);
        paymaster.setVerifyingSigner(newSigner);
        assertEq(paymaster.verifyingSigner(), newSigner);
    }

    function test_setVerifyingSigner_revertsIfNotOwner() public {
        vm.prank(address(0xDEAD));
        vm.expectRevert();
        paymaster.setVerifyingSigner(address(0x999));
    }

    function test_depositToEntryPoint_ownerOnly() public {
        vm.deal(paymasterOwner, 2 ether);
        vm.prank(paymasterOwner);
        paymaster.depositToEntryPoint{value: 1 ether}();
        // EntryPoint received the deposit
        assertGt(address(ep).balance, 0);
    }
}
