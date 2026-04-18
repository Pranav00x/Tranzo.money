export declare class StreamService {
    /**
     * Create a new salary stream via the TranzoDripper contract.
     */
    static createStream(params: {
        employerId: string;
        employeeAddress: string;
        tokenAddress: string;
        amountPerSecond: string;
        startTime: Date;
        endTime: Date;
    }): Promise<{
        stream: {
            status: import("@prisma/client").$Enums.StreamStatus;
            id: string;
            createdAt: Date;
            tokenAddress: string;
            txHash: string | null;
            onChainStreamId: number | null;
            employeeAddress: string;
            amountPerSecond: string;
            startTime: Date;
            endTime: Date;
            totalWithdrawn: string;
            employerId: string;
            employeeId: string | null;
        };
        intent: import("@openfort/openfort-node").TransactionIntentResponse;
    }>;
    /**
     * Withdraw accrued amount from a stream.
     */
    static withdrawFromStream(streamId: string, userId: string): Promise<{
        intent: import("@openfort/openfort-node").TransactionIntentResponse;
    }>;
    /**
     * Cancel a stream (employer only).
     */
    static cancelStream(streamId: string, userId: string): Promise<{
        intent: import("@openfort/openfort-node").TransactionIntentResponse;
    }>;
    /**
     * List streams for a user (as employer or employee).
     */
    static listStreams(userId: string, role: "employer" | "employee"): Promise<{
        status: import("@prisma/client").$Enums.StreamStatus;
        id: string;
        createdAt: Date;
        tokenAddress: string;
        txHash: string | null;
        onChainStreamId: number | null;
        employeeAddress: string;
        amountPerSecond: string;
        startTime: Date;
        endTime: Date;
        totalWithdrawn: string;
        employerId: string;
        employeeId: string | null;
    }[]>;
    /**
     * Get a single stream by ID.
     */
    static getStream(streamId: string): Promise<{
        status: import("@prisma/client").$Enums.StreamStatus;
        id: string;
        createdAt: Date;
        tokenAddress: string;
        txHash: string | null;
        onChainStreamId: number | null;
        employeeAddress: string;
        amountPerSecond: string;
        startTime: Date;
        endTime: Date;
        totalWithdrawn: string;
        employerId: string;
        employeeId: string | null;
    }>;
}
