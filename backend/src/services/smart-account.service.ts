import { http, createPublicClient, type Address, type Chain } from "viem";
import { privateKeyToAccount } from "viem/accounts";
import { polygon, base, polygonAmoy, baseSepolia } from "viem/chains";
import { createKernelAccount, createKernelAccountClient, createZeroDevPaymasterClient } from "@zerodev/sdk";
import { ENTRYPOINT_ADDRESS_V07 } from "permissionless";
import { KERNEL_V3_1 } from "@zerodev/sdk/constants";
import { signerToEcdsaValidator } from "@zerodev/sdk/permissions";
import { ENV } from "../config/env.js";

// Chain configuration mapping
const CHAINS: Record<number, Chain> = {
  137: polygon,
  8453: base,
  80002: polygonAmoy,
  84532: baseSepolia,
};

export class SmartAccountService {
  /**
   * Create a smart account (ZeroDev Kernel) for a user.
   * In a real app, the 'signer' would be a passkey or a local key.
   * For this integration, we'll demonstrated generating a signer from a private key.
   */
  static async createAccount(ownerPrivateKey: `0x${string}`, chainId = ENV.DEFAULT_CHAIN_ID) {
    const chain = CHAINS[chainId] || polygon;
    const publicClient = createPublicClient({
      chain,
      transport: http(),
    });

    const signer = privateKeyToAccount(ownerPrivateKey);

    // Create a validator for the account
    const ecdsaValidator = await signerToEcdsaValidator(publicClient, {
      signer,
      entryPoint: ENTRYPOINT_ADDRESS_V07,
      kernelVersion: KERNEL_V3_1,
    });

    // Create the Kernel account
    const account = await createKernelAccount(publicClient, {
      plugins: {
        sudo: ecdsaValidator,
      },
      entryPoint: ENTRYPOINT_ADDRESS_V07,
      kernelVersion: KERNEL_V3_1,
    });

    return {
      address: account.address,
      account,
    };
  }

  /**
   * Send a gasless transaction using ZeroDev Paymaster.
   */
  static async sendGaslessTransaction(
    ownerPrivateKey: `0x${string}`,
    to: Address,
    value: bigint,
    data: `0x${string}` = "0x",
    chainId = ENV.DEFAULT_CHAIN_ID
  ) {
    const chain = CHAINS[chainId] || polygon;
    const publicClient = createPublicClient({
      chain,
      transport: http(),
    });

    const signer = privateKeyToAccount(ownerPrivateKey);
    
    const ecdsaValidator = await signerToEcdsaValidator(publicClient, {
      signer,
      entryPoint: ENTRYPOINT_ADDRESS_V07,
      kernelVersion: KERNEL_V3_1,
    });

    const account = await createKernelAccount(publicClient, {
      plugins: {
        sudo: ecdsaValidator,
      },
      entryPoint: ENTRYPOINT_ADDRESS_V07,
      kernelVersion: KERNEL_V3_1,
    });

    // Create the ZeroDev paymaster client
    const ZERODEV_PROJECT_ID = ENV.ZERODEV_PROJECT_ID || "632e629a-8e5a-40f9-9d03-d12a2dadc70d";
    const ZERODEV_RPC_URL = ENV.ZERODEV_RPC_URL || `https://rpc.zerodev.app/api/v3/${ZERODEV_PROJECT_ID}/chain/${chainId}`;

    const paymasterClient = createZeroDevPaymasterClient({
      chain,
      transport: http(ZERODEV_RPC_URL),
      entryPoint: ENTRYPOINT_ADDRESS_V07,
    });

    const kernelClient = createKernelAccountClient({
      account,
      chain,
      bundlerTransport: http(ZERODEV_RPC_URL),
      entryPoint: ENTRYPOINT_ADDRESS_V07,
      middleware: {
        sponsorUserOperation: paymasterClient.sponsorUserOperation,
      },
    });

    const hash = await kernelClient.sendTransaction({
      to,
      value,
      data,
    });

    return hash;
  }
}
