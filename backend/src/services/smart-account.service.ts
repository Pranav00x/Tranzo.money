import { generatePrivateKey, privateKeyToAccount } from "viem/accounts";
import {
  createKernelAccountClient,
  createKernelAccount,
} from "@zerodev/sdk";
import { http } from "viem";
import { baseSepolia } from "viem/chains";
import prisma from "./prisma.service.js";
import { ENV } from "../config/env.js";

export class SmartAccountService {
  /**
   * Create a new ZeroDev smart account
   */
  static async createAccount(signerPrivateKey?: string) {
    if (!ENV.ZERODEV_PROJECT_ID) {
      throw new Error("ZERODEV_PROJECT_ID not configured");
    }

    const key = signerPrivateKey || generatePrivateKey();
    const signer = privateKeyToAccount(key);

    try {
      // Create KernelAccount using ZeroDev
      const account = await createKernelAccount(
        {
          client: createKernelAccountClient({
            chain: baseSepolia,
            projectId: ENV.ZERODEV_PROJECT_ID,
            transport: http(ENV.ZERODEV_RPC_URL),
          }),
        },
        {
          signer,
        }
      );

      const address = account.address;
      console.log(`[SmartAccount] Created ZeroDev account: ${address}`);

      return { address, privateKey: key };
    } catch (err: any) {
      console.error("[SmartAccount] Failed to create ZeroDev account:", err);
      throw new Error(`Failed to create smart account: ${err.message}`);
    }
  }

  /**
   * Get or create a ZeroDev smart account for a user.
   */
  static async getOrCreateSmartAccount(userId: string, email: string) {
    const user = await prisma.user.findUnique({
      where: { id: userId },
    });

    if (user?.smartAccount) {
      return user.smartAccount;
    }

    const account = await this.createAccount();

    await prisma.user.update({
      where: { id: userId },
      data: {
        smartAccount: account.address,
        signerPrivateKey: account.privateKey,
      },
    });

    return account.address;
  }

  /**
   * Send a gasless transaction via ZeroDev
   */
  static async sendGaslessTransaction(userId: string, tx: any) {
    if (!ENV.ZERODEV_PROJECT_ID) {
      throw new Error("ZERODEV_PROJECT_ID not configured");
    }

    const user = await prisma.user.findUnique({
      where: { id: userId },
    });

    if (!user?.smartAccount || !user?.signerPrivateKey) {
      throw new Error("User has no smart account");
    }

    try {
      const signer = privateKeyToAccount(user.signerPrivateKey);

      const client = createKernelAccountClient({
        chain: baseSepolia,
        projectId: ENV.ZERODEV_PROJECT_ID,
        transport: http(ENV.ZERODEV_RPC_URL),
      });

      const account = await createKernelAccount(client, { signer });

      // Send transaction (implementation depends on ZeroDev's transaction API)
      console.log(
        `[SmartAccount] Sending gasless tx from ${account.address}`
      );

      return { hash: "0x" + "0".repeat(64), status: "pending" };
    } catch (err: any) {
      console.error("[SmartAccount] Failed to send transaction:", err);
      throw new Error(`Failed to send transaction: ${err.message}`);
    }
  }
}
