// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {ERC1967Proxy} from "@openzeppelin/contracts/proxy/ERC1967/ERC1967Proxy.sol";
import {Create2} from "@openzeppelin/contracts/utils/Create2.sol";

import {TranzoAccount} from "./TranzoAccount.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";

/**
 * @title TranzoAccountFactory
 * @author Tranzo Team
 * @notice Deterministic ERC-4337 smart wallet factory using CREATE2.
 *
 * Addresses are computed off-chain (counterfactual) before the first transaction.
 * The bundler calls createAccount() in the initCode of the first UserOp; subsequent
 * calls to createAccount() with the same (owner, salt) are no-ops (returns existing address).
 *
 * Deployment: Factory → createAccount() → ERC1967Proxy → TranzoAccount implementation.
 */
contract TranzoAccountFactory {
    // ─── Storage ─────────────────────────────────────────────────

    /// @notice The shared TranzoAccount implementation contract (logic contract).
    TranzoAccount public immutable accountImplementation;

    // ─── Events ──────────────────────────────────────────────────

    event AccountCreated(address indexed account, address indexed owner, uint256 salt);

    // ─── Constructor ─────────────────────────────────────────────

    /**
     * @param entryPoint The ERC-4337 EntryPoint. Passed to the TranzoAccount implementation.
     */
    constructor(IEntryPoint entryPoint) {
        accountImplementation = new TranzoAccount(entryPoint);
    }

    // ─── Deployment ───────────────────────────────────────────────

    /**
     * @notice Deploy a new TranzoAccount proxy for `owner` using CREATE2 salt derived
     *         from `owner` and `salt`. If the account already exists, returns its address.
     *
     * @param owner              The initial owner of the new account.
     * @param salt               Caller-supplied salt (allows multiple accounts per owner).
     * @param dailyLimit         Daily spending limit (0 = no limit).
     * @param perTxLimit         Per-tx spending limit (0 = no limit).
     * @param guardians          Social recovery guardians (may be empty).
     * @param recoveryThreshold  Recovery threshold (ignored when guardians is empty).
     * @return account           Address of the deployed (or already-deployed) account.
     */
    function createAccount(
        address owner,
        uint256 salt,
        uint256 dailyLimit,
        uint256 perTxLimit,
        address[] calldata guardians,
        uint256 recoveryThreshold
    ) external returns (TranzoAccount account) {
        address predicted = getAddress(owner, salt);

        // Lazy deployment: if the account already exists, just return it.
        if (predicted.code.length > 0) {
            return TranzoAccount(payable(predicted));
        }

        bytes memory initData = abi.encodeCall(
            TranzoAccount.initialize,
            (owner, dailyLimit, perTxLimit, guardians, recoveryThreshold)
        );

        // Deploy proxy via CREATE2; salt is a hash of (owner, user-supplied salt) so
        // different owners with salt=0 get different addresses.
        bytes32 create2Salt = keccak256(abi.encodePacked(owner, salt));

        ERC1967Proxy proxy = new ERC1967Proxy{salt: create2Salt}(
            address(accountImplementation),
            initData
        );

        account = TranzoAccount(payable(address(proxy)));
        emit AccountCreated(address(account), owner, salt);
    }

    // ─── Counterfactual address ───────────────────────────────────

    /**
     * @notice Compute the deterministic address for a (owner, salt) pair without deploying.
     *         The result is stable — it is the same address createAccount() would deploy to.
     *
     * @param owner  The intended account owner.
     * @param salt   Caller-supplied salt.
     * @return       The counterfactual address.
     */
    function getAddress(address owner, uint256 salt) public view returns (address) {
        bytes32 create2Salt = keccak256(abi.encodePacked(owner, salt));

        bytes memory initData = abi.encodeCall(
            TranzoAccount.initialize,
            (owner, 0, 0, new address[](0), 0)
        );

        // The proxy bytecode deployed by `new ERC1967Proxy{salt}(impl, initData)`.
        bytes memory proxyBytecode = abi.encodePacked(
            type(ERC1967Proxy).creationCode,
            abi.encode(address(accountImplementation), initData)
        );

        return Create2.computeAddress(create2Salt, keccak256(proxyBytecode), address(this));
    }
    /**
     * @notice Alias for getAddress to match conventional naming.
     */
    function counterfactualAddress(address owner) external view returns (address) {
        return getAddress(owner, 0);
    }
}
