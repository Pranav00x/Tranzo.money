export interface FcmMessage {
    token: string;
    notification: {
        title: string;
        body: string;
    };
    data?: Record<string, string>;
}
export declare class NotificationService {
    /**
     * Register or refresh an FCM device token for a user.
     */
    static registerToken(userId: string, token: string, platform: "android" | "ios"): Promise<void>;
    /**
     * Remove a specific FCM token (on logout / token refresh).
     */
    static removeToken(userId: string, token: string): Promise<void>;
    /**
     * Send a push notification to all of a user's registered devices.
     * Silently drops stale/invalid tokens.
     */
    static sendPushNotification(userId: string, title: string, body: string, data?: Record<string, string>): Promise<void>;
    /**
     * Notify user when an on-chain transaction is confirmed.
     */
    static notifyTransactionConfirmed(userId: string, txHash: string, type: "transfer" | "swap" | "card_settlement" | "dripper_withdraw" | string): Promise<void>;
    /**
     * Notify user of a card authorization (real-time spend alert).
     */
    static notifyCardTransaction(userId: string, merchant: string, amount: number): Promise<void>;
    /**
     * Notify user of a salary stream withdrawal.
     */
    static notifyStreamWithdrawal(userId: string, streamId: string, amount: string): Promise<void>;
    /**
     * Notify user of a declined card authorization.
     */
    static notifyCardDeclined(userId: string, merchant: string, reason: string): Promise<void>;
}
