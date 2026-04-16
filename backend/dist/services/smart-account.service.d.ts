export declare class SmartAccountService {
    /**
     * Create a new smart account (mock for testing)
     */
    static createAccount(privateKey?: string): Promise<{
        address: string;
        privateKey: string;
    }>;
    /**
     * Get or create a ZeroDev smart account for a user.
     */
    static getOrCreateSmartAccount(userId: string, email: string): Promise<string>;
    /**
     * Send a gasless transaction (mock)
     */
    static sendGaslessTransaction(userId: string, tx: any): Promise<{
        hash: string;
        status: string;
    }>;
}
