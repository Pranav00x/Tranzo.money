// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {Script, console} from "forge-std/Script.sol";
import {TranzoDripper} from "../src/TranzoDripper.sol";
import {TranzoAccountFactory} from "../src/TranzoAccountFactory.sol";
import {TranzoPaymaster} from "../src/TranzoPaymaster.sol";
import {IEntryPoint} from "account-abstraction/interfaces/IEntryPoint.sol";

/**
 * @title Deploy
 * @notice Deploys the full Tranzo smart-contract stack.
 *
 * Required environment variables:
 *   DEPLOYER_PRIVATE_KEY   — deployer EOA private key
 *   ENTRY_POINT_ADDRESS    — ERC-4337 EntryPoint (v0.7) on the target network
 *   BACKEND_SIGNER_ADDRESS — Tranzo backend hot-wallet that signs paymaster authorisations
 *   USDC_ADDRESS           — USDC token address on the target network
 *
 * Optional:
 *   PAYMASTER_DEPOSIT_ETH  — Initial ETH deposit into EntryPoint for the paymaster (default: 0)
 *
 * Deployment order:
 *   1. TranzoAccountFactory  (deploys TranzoAccount implementation internally)
 *   2. TranzoPaymaster
 *   3. TranzoDripper
 */
contract Deploy is Script {
    function run() external {
        uint256 deployerPk = vm.envUint("DEPLOYER_PRIVATE_KEY");
        address deployer = vm.addr(deployerPk);

        address entryPointAddr = vm.envAddress("ENTRY_POINT_ADDRESS");
        address backendSigner = vm.envAddress("BACKEND_SIGNER_ADDRESS");
        address usdcAddr = vm.envAddress("USDC_ADDRESS");

        // Optional initial paymaster deposit
        uint256 paymasterDeposit = vm.envOr("PAYMASTER_DEPOSIT_ETH", uint256(0));

        console.log("Deploying Tranzo contracts...");
        console.log("  Deployer:       ", deployer);
        console.log("  EntryPoint:     ", entryPointAddr);
        console.log("  BackendSigner:  ", backendSigner);
        console.log("  USDC:           ", usdcAddr);

        vm.startBroadcast(deployerPk);

        // ── 1. Account Factory ────────────────────────────────────
        TranzoAccountFactory factory = new TranzoAccountFactory(
            IEntryPoint(entryPointAddr)
        );
        console.log("TranzoAccountFactory deployed at:", address(factory));
        console.log(
            "  TranzoAccount implementation at:",
            address(factory.accountImplementation())
        );

        // ── 2. Paymaster ──────────────────────────────────────────
        TranzoPaymaster paymaster = new TranzoPaymaster(
            IEntryPoint(entryPointAddr),
            backendSigner,
            usdcAddr,
            deployer // owner — transfer later if desired
        );
        console.log("TranzoPaymaster deployed at:", address(paymaster));

        // Fund the paymaster's EntryPoint deposit if configured
        if (paymasterDeposit > 0) {
            paymaster.depositToEntryPoint{value: paymasterDeposit}();
            console.log("  Deposited into EntryPoint:", paymasterDeposit);
        }

        // ── 3. Dripper ────────────────────────────────────────────
        TranzoDripper dripper = new TranzoDripper();
        console.log("TranzoDripper deployed at:", address(dripper));

        vm.stopBroadcast();

        // ── Summary ───────────────────────────────────────────────
        console.log("\n=== Deployment Summary ===");
        console.log("Factory:    ", address(factory));
        console.log("Paymaster:  ", address(paymaster));
        console.log("Dripper:    ", address(dripper));
    }
}

/**
 * @title DeployDripper
 * @notice Legacy standalone dripper deployment (kept for backwards compatibility).
 */
contract DeployDripper is Script {
    function run() external {
        uint256 deployerPrivateKey = vm.envUint("DEPLOYER_PRIVATE_KEY");

        vm.startBroadcast(deployerPrivateKey);

        TranzoDripper dripper = new TranzoDripper();
        console.log("TranzoDripper deployed at:", address(dripper));

        vm.stopBroadcast();
    }
}
