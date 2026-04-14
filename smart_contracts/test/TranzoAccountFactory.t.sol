// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {Test} from "forge-std/Test.sol";
import {ERC1967Proxy} from "@openzeppelin/contracts/proxy/ERC1967/ERC1967Proxy.sol";

import {TranzoAccount} from "../src/TranzoAccount.sol";
import {TranzoAccountFactory} from "../src/TranzoAccountFactory.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";

contract MockEntryPointForFactory {
    mapping(address => uint256) public nonces;

    function getNonce(address account, uint192) external view returns (uint256) {
        return nonces[account];
    }

    function depositTo(address) external payable {}
    function addStake(uint32) external payable {}
    function unlockStake() external {}
    function withdrawStake(address payable) external {}
    function withdrawTo(address payable, uint256) external {}

    receive() external payable {}
}

contract TranzoAccountFactoryTest is Test {
    MockEntryPointForFactory public ep;
    TranzoAccountFactory public factory;

    address public alice = address(0xA11CE);
    address public bob = address(0xB0B);

    function setUp() public {
        ep = new MockEntryPointForFactory();
        factory = new TranzoAccountFactory(IEntryPoint(address(ep)));
    }

    // ─── Helper ───────────────────────────────────────────────────

    function _create(address owner, uint256 salt) internal returns (address) {
        return address(factory.createAccount(owner, salt, 0, 0, new address[](0), 0));
    }

    // ─── Deployment ───────────────────────────────────────────────

    function test_createAccount_deploysProxy() public {
        address addr = _create(alice, 0);
        assertTrue(addr.code.length > 0, "account should have code");
    }

    function test_createAccount_setsOwner() public {
        address addr = _create(alice, 0);
        assertEq(TranzoAccount(payable(addr)).owner(), alice);
    }

    function test_createAccount_idempotent() public {
        address addr1 = _create(alice, 0);
        address addr2 = _create(alice, 0);
        assertEq(addr1, addr2, "should return same address on second call");
    }

    // ─── Counterfactual address ───────────────────────────────────

    function test_getAddress_matchesDeployedAddress() public {
        address predicted = factory.getAddress(alice, 0);
        address deployed = _create(alice, 0);
        assertEq(predicted, deployed, "predicted and deployed addresses should match");
    }

    function test_getAddress_beforeDeployment_noCode() public {
        address predicted = factory.getAddress(alice, 0);
        assertEq(predicted.code.length, 0, "should have no code before deployment");
    }

    // ─── Salt isolation ───────────────────────────────────────────

    function test_differentSalts_differentAddresses() public view {
        address addr0 = factory.getAddress(alice, 0);
        address addr1 = factory.getAddress(alice, 1);
        assertNotEq(addr0, addr1, "different salts should yield different addresses");
    }

    function test_differentOwners_differentAddresses() public view {
        address addrAlice = factory.getAddress(alice, 0);
        address addrBob = factory.getAddress(bob, 0);
        assertNotEq(addrAlice, addrBob, "different owners should yield different addresses");
    }

    // ─── Multiple accounts per user ──────────────────────────────

    function test_multipleAccountsPerOwner() public {
        address acc0 = _create(alice, 0);
        address acc1 = _create(alice, 1);
        assertNotEq(acc0, acc1);
        assertEq(TranzoAccount(payable(acc0)).owner(), alice);
        assertEq(TranzoAccount(payable(acc1)).owner(), alice);
    }

    // ─── Implementation reference ─────────────────────────────────

    function test_implementationIsNotProxy() public view {
        address impl = address(factory.accountImplementation());
        assertTrue(impl != address(0));
    }

    // ─── Events ──────────────────────────────────────────────────

    function test_emitsAccountCreatedEvent() public {
        address predicted = factory.getAddress(alice, 42);
        vm.expectEmit(true, true, false, true);
        emit TranzoAccountFactory.AccountCreated(predicted, alice, 42);
        factory.createAccount(alice, 42, 0, 0, new address[](0), 0);
    }
}
