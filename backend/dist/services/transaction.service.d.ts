/**
 * ZeroDev-based Transaction Service
 * Handles smart account transactions via Kernel SDK
 */
export declare class TransactionService {
    private static readonly CHAIN;
    /**
     * Submit a token transfer via ZeroDev smart account
     */
    static sendToken(params: {
        userId: string;
        to: string;
        tokenAddress: string;
        amount: string;
        chainId?: number;
    }): Promise<{
        opHash: any;
        status: "SUBMITTED";
        chainId: number;
    }>;
    /**
     * Get transaction history from UserOpLog
     */
    static getTransactionHistory(userId: string, limit?: number): Promise<{
        data: {
            id: string;
            type: string;
            status: import("@prisma/client").$Enums.UserOpStatus;
            txHash: string;
            opHash: string;
            createdAt: Date;
            chainId: number;
        }[];
    }>;
    /**
     * Get transaction status
     */
    static getTransactionStatus(opHash: string): Promise<{
        id: string;
        status: import("@prisma/client").$Enums.UserOpStatus;
        transactionHash: string;
        type: string;
    }>;
}
