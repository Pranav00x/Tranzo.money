import { generatePrivateKey, privateKeyToAccount } from "viem/accounts";
import { createPublicClient, http } from "viem";
import { baseSepolia } from "viem/chains";
import prisma from "./prisma.service.js";
import { ENV } from "../config/env.js";
// ZeroDev SDK - kernel account creation
import { createKernelAccount, createKernelAccountClient } from "@zerodev/sdk";
// @ts-ignore
import { signerToEcdsaValidator } from "@zerodev/ecdsa-validator";
import { getEntryPoint, KERNEL_V3_1 } from "@zerodev/sdk/constants";
export class SmartAccountService {
    /**
     * Create a new ZeroDev smart account (production-ready)
     */
    static async createAccount(signerPrivateKey) {
        if (!ENV.ZERODEV_PROJECT_ID || !ENV.ZERODEV_RPC_URL) {
            throw new Error("ZERODEV_PROJECT_ID and ZERODEV_RPC_URL must be configured for production");
        }
        console.log(`[SmartAccount] Creating account with ProjectID: ${ENV.ZERODEV_PROJECT_ID.substring(0, 10)}...`);
        const key = signerPrivateKey || generatePrivateKey();
        const signer = privateKeyToAccount(key);
        console.log(`[SmartAccount] Generated signer: ${signer.address}`);
        try {
            // Step 1: Create public client
            console.log(`[SmartAccount] Creating public client with RPC: ${ENV.ZERODEV_RPC_URL.substring(0, 50)}...`);
            const publicClient = createPublicClient({
                chain: baseSepolia,
                transport: http(ENV.ZERODEV_RPC_URL),
            });
            console.log(`[SmartAccount] ✓ Public client created`);
            // Step 2: Get correct entry point for v0.7
            console.log(`[SmartAccount] Getting EntryPoint v0.7...`);
            const entryPoint = getEntryPoint("0.7");
            // Step 3: Create ECDSA validator
            console.log(`[SmartAccount] Creating ECDSA validator...`);
            // @ts-ignore - ZeroDev SDK types compatibility
            const ecdsaValidator = await signerToEcdsaValidator(publicClient, {
                signer,
                entryPoint,
                kernelVersion: KERNEL_V3_1,
            });
            console.log(`[SmartAccount] ✓ ECDSA validator created`);
            // Step 4: Create kernel account
            console.log(`[SmartAccount] Creating kernel account...`);
            // @ts-ignore - ZeroDev SDK types compatibility
            const account = await createKernelAccount(publicClient, {
                plugins: { sudo: ecdsaValidator },
                entryPoint,
                kernelVersion: KERNEL_V3_1,
            });
            console.log(`[SmartAccount] ✓ Kernel account created`);
            const address = account.address;
            console.log(`[SmartAccount] ✅ Smart Account Address: ${address}`);
            console.log(`[SmartAccount] Chain: Base Sepolia (84532), Signer: ${signer.address}`);
            console.log(`[SmartAccount] Private key: ${key.substring(0, 10)}...`);
            return { address, privateKey: key };
        }
        catch (err) {
            console.error("[SmartAccount] ❌ Failed to create ZeroDev account");
            console.error("[SmartAccount] Error message:", err.message);
            console.error("[SmartAccount] Error name:", err.name);
            if (err.stack)
                console.error("[SmartAccount] Stack:", err.stack);
            throw new Error(`Failed to create smart account: ${err.message}`);
        }
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
     * Send a gasless transaction via ZeroDev (Unified Signature)
     */
    static async sendGaslessTransaction(signerPrivateKey, to, value, data, chainId = ENV.DEFAULT_CHAIN_ID) {
        try {
            const signer = privateKeyToAccount(signerPrivateKey);
            const publicClient = createPublicClient({
                chain: baseSepolia,
                transport: http(ENV.ZERODEV_RPC_URL),
            });
            const entryPoint = getEntryPoint("0.7");
            // @ts-ignore
            const ecdsaValidator = await signerToEcdsaValidator(publicClient, {
                signer,
                entryPoint,
                kernelVersion: KERNEL_V3_1,
            });
            // @ts-ignore
            const account = await createKernelAccount(publicClient, {
                plugins: { sudo: ecdsaValidator },
                entryPoint,
                kernelVersion: KERNEL_V3_1,
            });
            const walletClient = createKernelAccountClient({
                account,
                chain: baseSepolia,
                bundlerTransport: http(ENV.ZERODEV_RPC_URL),
            });
            console.log(`[SmartAccount] Sending gasless tx to ${to}`);
            const hash = await walletClient.sendTransaction({
                to,
                value,
                data,
            });
            return hash;
        }
        catch (err) {
            console.error("[SmartAccount] Failed to send transaction:", err);
            throw new Error(`Failed to send transaction: ${err.message}`);
        }
    }
}
//# sourceMappingURL=smart-account.service.js.map