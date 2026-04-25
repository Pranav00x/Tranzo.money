import { createPublicClient, createWalletClient, encodeFunctionData, http } from "viem";
import { baseSepolia } from "viem/chains";
import { privateKeyToAccount } from "viem/accounts";
import { SmartAccountService } from "./smart-account.service.js";
import { ENV } from "../config/env.js";
import prisma from "./prisma.service.js";
import { UserOpStatus } from "@prisma/client";

// ZeroDev SDK
import { createKernelAccount, createKernelAccountClient } from "@zerodev/sdk";
// @ts-ignore
import { signerToEcdsaValidator } from "@zerodev/ecdsa-validator";
import { getEntryPoint, KERNEL_V3_1 } from "@zerodev/sdk/constants";

/**
 * ZeroDev-based Transaction Service
 * Handles smart account transactions via Kernel SDK
 */
export class TransactionService {
  private static readonly CHAIN = baseSepolia;

  /**
   * Submit a token transfer via ZeroDev smart account
   */
  static async sendToken(params: {
    userId: string;
    to: string;
    tokenAddress: string;
    amount: string;
    chainId?: number;
  }) {
    try {
      const user = await prisma.user.findUniqueOrThrow({
        where: { id: params.userId },
      });

      if (!user.smartAccount) {
        throw new Error("User has no smart account");
      }

      if (!user.signerPrivateKey) {
        throw new Error("User has no signer private key");
      }

      // Create signer from stored private key
      const signer = privateKeyToAccount(user.signerPrivateKey as `0x${string}`);

      // Create public client
      const publicClient = createPublicClient({
        chain: this.CHAIN,
        transport: http(ENV.ZERODEV_RPC_URL || ""),
      });

      const entryPoint = getEntryPoint("0.7");

      // Create ECDSA validator
      // @ts-ignore
      const ecdsaValidator = await signerToEcdsaValidator(publicClient, {
        signer,
        entryPoint,
        kernelVersion: KERNEL_V3_1,
      });

      // Re-create the kernel account object from the stored address
      // @ts-ignore
      const account = await createKernelAccount(publicClient, {
        plugins: { sudo: ecdsaValidator },
        entryPoint,
        kernelVersion: KERNEL_V3_1,
        accountAddress: user.smartAccount as `0x${string}`,
      });

      // Create account client
      const accountClient = createKernelAccountClient({
        account,
        chain: this.CHAIN,
        bundlerTransport: http(ENV.ZERODEV_RPC_URL || ""),
        entryPoint,
      });

      // ERC-20 transfer ABI
      const erc20ABI = [
        {
          name: "transfer",
          type: "function",
          inputs: [
            { name: "recipient", type: "address" },
            { name: "amount", type: "uint256" },
          ],
          outputs: [{ name: "", type: "bool" }],
        },
      ];

      // Encode transfer call
      const callData = encodeFunctionData({
        abi: erc20ABI,
        functionName: "transfer",
        args: [params.to as `0x${string}`, BigInt(params.amount)],
      });

      // Send UserOp
      const opHash = await accountClient.sendUserOperation({
        calls: [
          {
            to: params.tokenAddress as `0x${string}`,
            data: callData,
            value: BigInt(0),
          }
        ]
      });

      console.log(`[Transaction] ✅ UserOp submitted: ${opHash}`);

      return {
        opHash,
        status: UserOpStatus.SUBMITTED,
        chainId: params.chainId || ENV.DEFAULT_CHAIN_ID,
      };
    } catch (error: any) {
      console.error("[Transaction] ❌ Transfer failed:", error.message);
      throw new Error(`Transfer failed: ${error.message}`);
    }
  }

  /**
   * Get transaction history from UserOpLog
   */
  static async getTransactionHistory(userId: string, limit = 20) {
    try {
      const transactions = await prisma.userOpLog.findMany({
        where: { userId },
        orderBy: { createdAt: "desc" },
        take: limit,
      });

      return {
        data: transactions.map((tx) => ({
          id: tx.id,
          type: tx.type,
          status: tx.status,
          txHash: tx.txHash,
          opHash: tx.opHash,
          createdAt: tx.createdAt,
          chainId: tx.chainId,
        })),
      };
    } catch (error: any) {
      console.error("[Transaction] ❌ History fetch failed:", error.message);
      throw new Error(`Failed to fetch history: ${error.message}`);
    }
  }

  /**
   * Get transaction status
   */
  static async getTransactionStatus(opHash: string) {
    try {
      const userOp = await prisma.userOpLog.findUnique({
        where: { opHash },
      });

      if (!userOp) {
        throw new Error(`UserOp not found: ${opHash}`);
      }

      return {
        id: userOp.opHash,
        status: userOp.status,
        transactionHash: userOp.txHash,
        type: userOp.type,
      };
    } catch (error: any) {
      console.error("[Transaction] ❌ Status fetch failed:", error.message);
      throw new Error(`Failed to fetch status: ${error.message}`);
    }
  }
}
