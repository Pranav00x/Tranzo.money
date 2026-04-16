export interface SwapQuote {
    fromToken: string;
    toToken: string;
    fromAmount: string;
    toAmount: string;
    toAmountFormatted: string;
    price: string;
    priceImpact: string;
    gas: string;
    estimatedGasUsd: string;
    sources: Array<{
        name: string;
        proportion: string;
    }>;
    data: string;
    to: string;
    value: string;
    validUntil: number;
    quoteId: string;
}
export interface SwapResult {
    intentId: string;
    txHash?: string;
    status: string;
    fromToken: string;
    toToken: string;
    fromAmount: string;
    estimatedToAmount: string;
    chainId: number;
}
export declare class SwapService {
    /**
     * Get a swap quote for a given token pair and amount.
     * Uses 0x API (or mock in dev).
     *
     * @param fromToken  Token address or symbol to sell
     * @param toToken    Token address or symbol to buy
     * @param amount     Amount to sell in token's smallest unit (wei / µUSDC)
     * @param chainId    Chain ID (137 = Polygon, 8453 = Base)
     */
    static getQuote(fromToken: string, toToken: string, amount: string, chainId?: number, takerAddress?: string): Promise<SwapQuote>;
    /**
     * Execute a swap by building and submitting a UserOp via Openfort.
     *
     * Flow:
     *  1. Fetch fresh quote (re-quotes to avoid stale data)
     *  2. Build ERC-20 approve + swap calldata interactions
     *  3. Submit via OpenfortService.executeInteraction
     *  4. Log UserOp
     *
     * Note: For native → token swaps, approval step is skipped.
     */
    static executeSwap(userId: string, fromToken: string, toToken: string, amount: string, chainId?: number, slippageBps?: number): Promise<SwapResult>;
}
