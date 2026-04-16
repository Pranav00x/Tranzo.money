export declare class BalanceService {
    /**
     * Get all token balances for a smart account address.
     */
    static getBalances(address: string, chainId?: number): Promise<{
        symbol: string;
        address: string;
        decimals: number;
        balance: string;
        formatted: string;
    }[]>;
    /**
     * Get supported tokens for a chain.
     */
    static getTokens(chainId?: number): {
        symbol: string;
        address: string;
        decimals: number;
    }[];
}
