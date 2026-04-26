/**
 * Wrapper around the Openfort SDK.
 * DEPRECATED: Migration to ZeroDev Kernel SDK in progress.
 * Handles player creation, smart account management, and transaction intents.
 */
export declare class OpenfortService {
    private static checkInitialized;
    /**
     * Create a new Openfort player and their smart account.
     * Returns the player ID and counterfactual smart account address.
     * DEPRECATED: Use ZeroDev Kernel SDK instead.
     */
    static createPlayer(email: string): Promise<{
        playerId: string;
        smartAccountAddress: string;
    }>;
    /**
     * Get a player's smart account address for a specific chain.
     * DEPRECATED: Use ZeroDev Kernel SDK instead.
     */
    static getAccount(playerId: string, chainId?: number): Promise<any>;
    /**
     * Submit a token transfer via Openfort.
     * Openfort handles UserOp construction, bundler submission, and gas sponsorship.
     */
    static sendToken(params: {
        playerId: string;
        to: string;
        tokenAddress: string;
        amount: string;
        chainId?: number;
    }): Promise<import("@openfort/openfort-node").TransactionIntentResponse>;
    /**
     * Execute an arbitrary contract interaction.
     */
    static executeInteraction(params: {
        playerId: string;
        contract: string;
        functionName: string;
        functionArgs: any[];
        chainId?: number;
        value?: string;
    }): Promise<import("@openfort/openfort-node").TransactionIntentResponse>;
    /**
     * Check the status of a transaction intent.
     */
    static getTransactionStatus(intentId: string): Promise<import("@openfort/openfort-node").TransactionIntentResponse>;
    /**
     * List recent transaction intents for a player.
     */
    static getTransactionHistory(playerId: string, limit?: number): Promise<import("@openfort/openfort-node").TransactionIntentListResponse>;
    /**
     * Create a signature request for the user to sign on-device.
     * Used for operations that require the user's key (e.g., high-value transfers).
     */
    static createSignatureRequest(params: {
        playerId: string;
        message: string;
        chainId?: number;
    }): Promise<any>;
}
