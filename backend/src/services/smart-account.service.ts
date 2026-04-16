import { generatePrivateKey } from "viem/accounts";
import prisma from "./prisma.service.js";

export class SmartAccountService {
  /**
   * Create a new smart account (mock for testing)
   */
  static async createAccount(userId: string) {
    const mockAddress = "0x" + generatePrivateKey().slice(2, 42);
    const privateKey = generatePrivateKey();
    
    await prisma.user.update({
      where: { id: userId },
      data: {
        smartAccount: mockAddress,
        signerPrivateKey: privateKey,
      },
    });

    console.log(`[SmartAccount] Created account: ${mockAddress}`);
    return { address: mockAddress, privateKey };
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

    return this.createAccount(userId).then(acc => acc.address);
  }

  /**
   * Send a gasless transaction (mock)
   */
  static async sendGaslessTransaction(userId: string, tx: any) {
    console.log(`[SmartAccount] Mock gasless tx for user ${userId}`);
    return { hash: "0x" + "0".repeat(64), status: "pending" };
  }
}
