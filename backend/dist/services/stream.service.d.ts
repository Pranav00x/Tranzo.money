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
        stream: any;
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
    static listStreams(userId: string, role: "employer" | "employee"): Promise<any>;
    /**
     * Get a single stream by ID.
     */
    static getStream(streamId: string): Promise<any>;
}
