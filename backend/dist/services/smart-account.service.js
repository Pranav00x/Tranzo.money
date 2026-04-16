import { generatePrivateKey } from "viem/accounts";
import prisma from "./prisma.service.js";
export class SmartAccountService {
    /**
     * Create a new smart account (mock for testing)
     */
    static async createAccount(privateKey) {
        const key = privateKey || generatePrivateKey();
        const mockAddress = "0x" + generatePrivateKey().slice(2, 42);
        console.log(`[SmartAccount] Created account: ${mockAddress}`);
        return { address: mockAddress, privateKey: key };
    }
    /**
     * Get or create a ZeroDev smart account for a user.
     */
    static async getOrCreateSmartAccount(userId, email) {
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
     * Send a gasless transaction (mock)
     */
    static async sendGaslessTransaction(userId, tx) {
        console.log(`[SmartAccount] Mock gasless tx for user ${userId}`);
        return { hash: "0x" + "0".repeat(64), status: "pending" };
    }
}
//# sourceMappingURL=smart-account.service.js.map