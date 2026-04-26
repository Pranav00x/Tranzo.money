export type CardType = "virtual" | "physical";
export type CardStatus = "active" | "frozen" | "pending" | "cancelled";
export interface CardDetails {
    id: string;
    type: CardType;
    status: CardStatus;
    last4: string;
    network: string;
    dailyLimit: number;
    monthlyLimit: number;
    dailySpent: number;
    monthlySpent: number;
    expiryMonth: number;
    expiryYear: number;
    createdAt: Date;
}
export interface AuthorizationWebhookPayload {
    cardId: string;
    transactionId: string;
    merchantName: string;
    merchantCategory?: string;
    amount: number;
    currency: string;
    webhookSignature?: string;
}
export interface SettlementWebhookPayload {
    transactionId: string;
    cardId: string;
    status: "completed" | "declined" | "refunded";
    settledAmount?: number;
    settlementCurrency?: string;
    txHash?: string;
}
export declare class CardService {
    /**
     * Order a new virtual or physical card for a user.
     * Calls the card partner to issue the card, then persists it locally.
     */
    static orderCard(userId: string, type: CardType, deliveryAddress?: Record<string, string>): Promise<CardDetails>;
    /**
     * CTO LOGIC: Activate a card on-chain by "installing" the session key plugin.
     * This is a one-time master-signed transaction that enables future card swipes
     * to be signatureless.
     */
    static activateCardOnChain(userId: string, cardId: string, spendLimitEth: string): Promise<{
        setupHash: string;
        sessionKeyPK: string;
    }>;
    /**
     * CTO LOGIC: Process a card payment using the stored session key.
     * This mimics the "Merchant Terminal" swiping the card.
     */
    static processCardPayment(cardNumber: string, cvv: string, merchantAddress: `0x${string}`, amountWei: bigint): Promise<{
        userOpHash: string;
    }>;
    /**
     * Get a user's card details (most recent active/pending card).
     */
    static getCard(userId: string): Promise<CardDetails | null>;
    /**
     * Get card transaction history for a user.
     */
    static getCardTransactions(userId: string, limit?: number, offset?: number): Promise<{
        transactions: {
            status: string;
            id: string;
            createdAt: Date;
            amount: number;
            userId: string;
            txHash: string | null;
            currency: string;
            merchant: string;
            category: string | null;
            cardId: string;
        }[];
        total: number;
    }>;
    /**
     * Freeze a card — funds cannot be spent while frozen.
     */
    static freezeCard(userId: string, cardId: string): Promise<CardDetails>;
    /**
     * Unfreeze a previously frozen card.
     */
    static unfreezeCard(userId: string, cardId: string): Promise<CardDetails>;
    /**
     * Update daily and/or monthly spending limits.
     */
    static updateLimits(userId: string, cardId: string, dailyLimit?: number, monthlyLimit?: number): Promise<CardDetails>;
    /**
     * Authorization webhook — called in real time when the user swipes their card.
     *
     * Critical path: must respond in <500ms.
     *
     * Flow:
     *  1. Verify webhook signature
     *  2. Look up card + user by partner card ID
     *  3. Check card status (not frozen/cancelled)
     *  4. Check spending limits
     *  5. Verify on-chain USDC balance via smart account
     *  6. Approve → create pending CardTransaction + queue Openfort UserOp
     *  7. Decline → record declined transaction
     */
    static handleAuthorizationWebhook(payload: AuthorizationWebhookPayload, rawHeaders: Record<string, string | string[] | undefined>, rawBody: string): Promise<{
        approved: boolean;
        transactionId: string;
        reason?: string;
    }>;
    /**
     * Settlement webhook — called after the transaction is fully settled.
     * Updates the transaction record with final status and on-chain tx hash.
     */
    static handleSettlementWebhook(payload: SettlementWebhookPayload): Promise<void>;
    private static assertCardOwner;
    private static recordTransaction;
    private static formatCard;
}
