export declare class SmartAccountService {
    /**
     * Create a new ZeroDev smart account (production-ready)
     */
    static createAccount(signerPrivateKey?: string): Promise<{
        address: `0x${string}`;
        privateKey: string;
    }>;
    /**
     * Get or create a ZeroDev smart account for a user.
     */
    static getOrCreateSmartAccount(userId: string, email: string): Promise<string>;
    /**
     * Send a gasless transaction via ZeroDev (Unified Signature)
     */
    static sendGaslessTransaction(signerPrivateKey: `0x${string}`, to: `0x${string}`, value: bigint, data: `0x${string}`, chainId?: number): Promise<any>;
}
