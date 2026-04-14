// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {Test, console} from "forge-std/Test.sol";
import {ECDSA} from "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";
import {MessageHashUtils} from "@openzeppelin/contracts/utils/cryptography/MessageHashUtils.sol";
import {ERC1967Proxy} from "@openzeppelin/contracts/proxy/ERC1967/ERC1967Proxy.sol";

import {TranzoAccount} from "../src/TranzoAccount.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";
import {PackedUserOperation} from "account-abstraction/interfaces/PackedUserOperation.sol";

/// @dev Minimal mock EntryPoint for unit testing
contract MockEntryPoint {
    mapping(address => uint256) public balances;
    mapping(address => uint256) public nonces;

    function getNonce(address account, uint192) external view returns (uint256) {
        return nonces[account];
    }

    function depositTo(address account) external payable {
        balances[account] += msg.value;
    }

    function addStake(uint32) external payable {}
    function unlockStake() external {}
    function withdrawStake(address payable) external {}
    function withdrawTo(address payable, uint256) external {}

    // Allow sending ETH to this mock
    receive() external payable {}
}

contract MockTarget {
    uint256 public value;
    bool public shouldRevert;

    function setValue(uint256 v) external {
        value = v;
    }

    function setShouldRevert(bool v) external {
        shouldRevert = v;
    }

    function revertingCall() external view {
        require(!shouldRevert, "MockTarget: forced revert");
    }

    receive() external payable {}
}

contract TranzoAccountTest is Test {
    using ECDSA for bytes32;
    using MessageHashUtils for bytes32;

    MockEntryPoint public ep;
    TranzoAccount public impl;
    TranzoAccount public account;
    MockTarget public target;

    uint256 public ownerPk = 0xA11CE;
    address public owner;

    // ─── Setup ───────────────────────────────────────────────────

    function setUp() public {
        owner = vm.addr(ownerPk);
        ep = new MockEntryPoint();
        impl = new TranzoAccount(IEntryPoint(address(ep)));

        bytes memory initData = abi.encodeCall(
            TranzoAccount.initialize,
            (owner, 0, 0, new address[](0), 0)
        );
        ERC1967Proxy proxy = new ERC1967Proxy(address(impl), initData);
        account = TranzoAccount(payable(address(proxy)));

        target = new MockTarget();
        // Fund account
        vm.deal(address(account), 10 ether);
    }

    // ─── Initialisation ──────────────────────────────────────────

    function test_initialOwner() public view {
        assertEq(account.owner(), owner);
    }

    function test_cannotInitializeTwice() public {
        vm.expectRevert();
        account.initialize(address(0xBEEF), 0, 0, new address[](0), 0);
    }

    // ─── execute ─────────────────────────────────────────────────

    function test_execute_ownerCanCall() public {
        bytes memory data = abi.encodeCall(MockTarget.setValue, (42));
        vm.prank(owner);
        account.execute(address(target), 0, data);
        assertEq(target.value(), 42);
    }

    function test_execute_entryPointCanCall() public {
        bytes memory data = abi.encodeCall(MockTarget.setValue, (99));
        vm.prank(address(ep));
        account.execute(address(target), 0, data);
        assertEq(target.value(), 99);
    }

    function test_execute_revertsIfUnauthorized() public {
        vm.prank(address(0xDEAD));
        vm.expectRevert(TranzoAccount.Unauthorized.selector);
        account.execute(address(target), 0, "");
    }

    function test_execute_forwardsValue() public {
        uint256 before = address(target).balance;
        vm.prank(owner);
        account.execute(address(target), 1 ether, "");
        assertEq(address(target).balance - before, 1 ether);
    }

    function test_execute_revertsOnFailedCall() public {
        target.setShouldRevert(true);
        bytes memory data = abi.encodeCall(MockTarget.revertingCall, ());
        vm.prank(owner);
        vm.expectRevert();
        account.execute(address(target), 0, data);
    }

    // ─── executeBatch ────────────────────────────────────────────

    function test_executeBatch_multipleTargets() public {
        MockTarget t2 = new MockTarget();
        address[] memory dests = new address[](2);
        dests[0] = address(target);
        dests[1] = address(t2);

        uint256[] memory values = new uint256[](2);

        bytes[] memory funcs = new bytes[](2);
        funcs[0] = abi.encodeCall(MockTarget.setValue, (1));
        funcs[1] = abi.encodeCall(MockTarget.setValue, (2));

        vm.prank(owner);
        account.executeBatch(dests, values, funcs);

        assertEq(target.value(), 1);
        assertEq(t2.value(), 2);
    }

    function test_executeBatch_revertsOnLengthMismatch() public {
        address[] memory dests = new address[](2);
        uint256[] memory values = new uint256[](1);
        bytes[] memory funcs = new bytes[](2);

        vm.prank(owner);
        vm.expectRevert(TranzoAccount.ArrayLengthMismatch.selector);
        account.executeBatch(dests, values, funcs);
    }

    // ─── Ownership ────────────────────────────────────────────────

    function test_transferOwnership() public {
        address newOwner = address(0xBEEF);
        vm.prank(owner);
        account.transferOwnership(newOwner);
        assertEq(account.owner(), newOwner);
    }

    function test_transferOwnership_revertsIfNotOwner() public {
        vm.prank(address(0xDEAD));
        vm.expectRevert(TranzoAccount.Unauthorized.selector);
        account.transferOwnership(address(0xBEEF));
    }

    function test_transferOwnership_revertsZeroAddress() public {
        vm.prank(owner);
        vm.expectRevert(TranzoAccount.AccountZeroAddress.selector);
        account.transferOwnership(address(0));
    }

    // ─── validateUserOp ──────────────────────────────────────────

    function _buildUserOp(address _account, bytes memory sig)
        internal
        pure
        returns (PackedUserOperation memory)
    {
        return PackedUserOperation({
            sender: _account,
            nonce: 0,
            initCode: "",
            callData: "",
            accountGasLimits: bytes32(0),
            preVerificationGas: 0,
            gasFees: bytes32(0),
            paymasterAndData: "",
            signature: sig
        });
    }

    function test_validateUserOp_validOwnerSignature() public {
        bytes32 userOpHash = keccak256("test-op");
        bytes32 ethHash = userOpHash.toEthSignedMessageHash();
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(ownerPk, ethHash);
        bytes memory sig = abi.encodePacked(r, s, v);

        PackedUserOperation memory op = _buildUserOp(address(account), sig);

        vm.prank(address(ep));
        uint256 result = account.validateUserOp(op, userOpHash, 0);
        assertEq(result, 0); // SIG_VALIDATION_SUCCESS
    }

    function test_validateUserOp_invalidSignature() public {
        bytes32 userOpHash = keccak256("test-op");
        // Sign with wrong key
        uint256 wrongPk = 0xBAD;
        bytes32 ethHash = userOpHash.toEthSignedMessageHash();
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(wrongPk, ethHash);
        bytes memory sig = abi.encodePacked(r, s, v);

        PackedUserOperation memory op = _buildUserOp(address(account), sig);

        vm.prank(address(ep));
        uint256 result = account.validateUserOp(op, userOpHash, 0);
        assertEq(result, 1); // SIG_VALIDATION_FAILED
    }

    function test_validateUserOp_revertsIfNotEntryPoint() public {
        PackedUserOperation memory op = _buildUserOp(address(account), "");
        vm.prank(address(0xDEAD));
        vm.expectRevert(TranzoAccount.Unauthorized.selector);
        account.validateUserOp(op, bytes32(0), 0);
    }

    function test_validateUserOp_paysPrefund() public {
        bytes32 userOpHash = keccak256("test-op");
        bytes32 ethHash = userOpHash.toEthSignedMessageHash();
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(ownerPk, ethHash);
        bytes memory sig = abi.encodePacked(r, s, v);

        PackedUserOperation memory op = _buildUserOp(address(account), sig);
        uint256 missing = 0.01 ether;
        uint256 epBefore = address(ep).balance;

        vm.prank(address(ep));
        account.validateUserOp(op, userOpHash, missing);

        assertEq(address(ep).balance - epBefore, missing);
    }

    // ─── EIP-1271 ─────────────────────────────────────────────────

    function test_isValidSignature_owner() public {
        bytes32 hash = keccak256("hello");
        bytes32 ethHash = hash.toEthSignedMessageHash();
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(ownerPk, ethHash);
        bytes memory sig = abi.encodePacked(r, s, v);

        bytes4 result = account.isValidSignature(hash, sig);
        assertEq(result, bytes4(0x1626ba7e));
    }

    function test_isValidSignature_wrongSigner() public {
        bytes32 hash = keccak256("hello");
        bytes32 ethHash = hash.toEthSignedMessageHash();
        (uint8 v, bytes32 r, bytes32 s) = vm.sign(0xBAD, ethHash);
        bytes memory sig = abi.encodePacked(r, s, v);

        bytes4 result = account.isValidSignature(hash, sig);
        assertEq(result, bytes4(0xffffffff));
    }

    // ─── SpendingLimits ───────────────────────────────────────────

    function test_spendingLimits_perTxEnforced() public {
        // Deploy account with a 0.5 ETH per-tx limit
        bytes memory initData = abi.encodeCall(
            TranzoAccount.initialize,
            (owner, 0, 0.5 ether, new address[](0), 0)
        );
        ERC1967Proxy proxy = new ERC1967Proxy(address(impl), initData);
        TranzoAccount limitedAccount = TranzoAccount(payable(address(proxy)));
        vm.deal(address(limitedAccount), 10 ether);

        vm.prank(owner);
        vm.expectRevert();
        limitedAccount.execute(address(target), 1 ether, "");
    }

    function test_spendingLimits_canSetLimits() public {
        vm.prank(owner);
        account.setSpendingLimits(10 ether, 1 ether);
        assertEq(account.dailyLimit(), 10 ether);
        assertEq(account.perTxLimit(), 1 ether);
    }

    // ─── Receive ETH ─────────────────────────────────────────────

    function test_receiveEth() public {
        uint256 before = address(account).balance;
        (bool ok,) = address(account).call{value: 1 ether}("");
        assertTrue(ok);
        assertEq(address(account).balance - before, 1 ether);
    }

    // ─── Session keys ─────────────────────────────────────────────

    function test_addRevokeSessionKey() public {
        address sessionKey = address(0x5E55);
        uint256 validUntil = block.timestamp + 1 days;

        vm.prank(owner);
        account.addSessionKey(
            sessionKey,
            new address[](0),
            new bytes4[](0),
            0,
            validUntil
        );

        assertTrue(account.isActiveSessionKey(sessionKey));

        vm.prank(owner);
        account.revokeSessionKey(sessionKey);
        assertFalse(account.isActiveSessionKey(sessionKey));
    }
}
