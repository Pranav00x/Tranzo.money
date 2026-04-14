import { OpenfortService } from "./openfort.service.js";
import { AppError } from "../utils/errors.js";
import { ENV } from "../config/env.js";
import prisma from "./prisma.service.js";

// ─── Types ────────────────────────────────────────────────────────────────────

export interface SwapQuote {
  fromToken: string;
  toToken: string;
  fromAmount: string;       // In token's native units (wei / smallest unit)
  toAmount: string;         // Estimated output in token's native units
  toAmountFormatted: string;
  price: string;            // toToken per fromToken
  priceImpact: string;      // e.g. "0.12" (%)
  gas: string;              // Estimated gas in wei
  estimatedGasUsd: string;
  sources: Array<{ name: string; proportion: string }>;
  data: string;             // ABI-encoded calldata for the swap router
  to: string;               // Swap router address
  value: string;            // Native value to send (for native token swaps)
  validUntil: number;       // Unix timestamp — quote expires after this
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

// ─── Supported Chains ─────────────────────────────────────────────────────────

const SWAP_ROUTERS: Record<number, string> = {
  137:  "0xDef1C0ded9bec7F1a1670819833240f027b25EfF", // 0x Router v4 — Polygon
  8453: "0xDef1C0ded9bec7F1a1670819833240f027b25EfF", // 0x Router v4 — Base
};

// 0x API base URLs
const ZEROX_API_URLS: Record<number, string> = {
  137:  "https://polygon.api.0x.org",
  8453: "https://base.api.0x.org",
};

// ─── Mock / Real Quote Providers ─────────────────────────────────────────────
//
// Real 0x API integration is structured here.
// Replace MOCK_MODE with a real 0x/1inch call by setting ZEROX_API_KEY in env.

const MOCK_MODE = !process.env.ZEROX_API_KEY;

async function fetchQuoteFrom0x(params: {
  sellToken: string;
  buyToken: string;
  sellAmount: string;
  chainId: number;
  takerAddress?: string;
}): Promise<SwapQuote> {
  if (MOCK_MODE) {
    return buildMockQuote(params);
  }

  const baseUrl = ZEROX_API_URLS[params.chainId];
  if (!baseUrl) {
    throw new AppError(400, `Chain ${params.chainId} not supported for swaps`);
  }

  const qs = new URLSearchParams({
    sellToken: params.sellToken,
    buyToken: params.buyToken,
    sellAmount: params.sellAmount,
    ...(params.takerAddress && { takerAddress: params.takerAddress }),
  });

  const res = await fetch(`${baseUrl}/swap/v1/quote?${qs}`, {
    headers: {
      "0x-api-key": process.env.ZEROX_API_KEY!,
      Accept: "application/json",
    },
  });

  if (!res.ok) {
    const err = await res.json().catch(() => ({ reason: res.statusText }));
    throw new AppError(res.status, `0x API error: ${(err as any).reason ?? res.statusText}`);
  }

  const data = await res.json() as any;

  return {
    fromToken: params.sellToken,
    toToken: params.buyToken,
    fromAmount: params.sellAmount,
    toAmount: data.buyAmount,
    toAmountFormatted: data.buyAmount, // caller can format with decimals
    price: data.price,
    priceImpact: data.estimatedPriceImpact ?? "0",
    gas: data.estimatedGas,
    estimatedGasUsd: data.estimatedGasUsd ?? "0",
    sources: data.sources ?? [],
    data: data.data,
    to: data.to,
    value: data.value ?? "0",
    validUntil: Math.floor(Date.now() / 1000) + 30, // 30-second TTL
    quoteId: `0x_${Date.now()}`,
  };
}

function buildMockQuote(params: {
  sellToken: string;
  buyToken: string;
  sellAmount: string;
  chainId: number;
}): SwapQuote {
  // Approximate 1:1 conversion for mock (USDC / stables scenario)
  const mockOutput = (BigInt(params.sellAmount) * 997n) / 1000n; // 0.3% slippage mock

  return {
    fromToken: params.sellToken,
    toToken: params.buyToken,
    fromAmount: params.sellAmount,
    toAmount: mockOutput.toString(),
    toAmountFormatted: mockOutput.toString(),
    price: "0.997",
    priceImpact: "0.30",
    gas: "150000",
    estimatedGasUsd: "0.05",
    sources: [{ name: "Uniswap_V3", proportion: "1" }],
    data: "0x",       // Would be real calldata in prod
    to: SWAP_ROUTERS[params.chainId] ?? "0x0000000000000000000000000000000000000000",
    value: "0",
    validUntil: Math.floor(Date.now() / 1000) + 30,
    quoteId: `mock_${Date.now()}`,
  };
}

// ─── Swap Service ─────────────────────────────────────────────────────────────

export class SwapService {
  /**
   * Get a swap quote for a given token pair and amount.
   * Uses 0x API (or mock in dev).
   *
   * @param fromToken  Token address or symbol to sell
   * @param toToken    Token address or symbol to buy
   * @param amount     Amount to sell in token's smallest unit (wei / µUSDC)
   * @param chainId    Chain ID (137 = Polygon, 8453 = Base)
   */
  static async getQuote(
    fromToken: string,
    toToken: string,
    amount: string,
    chainId: number = ENV.DEFAULT_CHAIN_ID,
    takerAddress?: string
  ): Promise<SwapQuote> {
    if (!amount || BigInt(amount) <= 0n) {
      throw new AppError(400, "Amount must be greater than zero");
    }

    if (!ZEROX_API_URLS[chainId] && !MOCK_MODE) {
      throw new AppError(400, `Chain ${chainId} not supported for swaps`);
    }

    return fetchQuoteFrom0x({
      sellToken: fromToken,
      buyToken: toToken,
      sellAmount: amount,
      chainId,
      takerAddress,
    });
  }

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
  static async executeSwap(
    userId: string,
    fromToken: string,
    toToken: string,
    amount: string,
    chainId: number = ENV.DEFAULT_CHAIN_ID,
    slippageBps = 50   // 0.5% default slippage
  ): Promise<SwapResult> {
    const user = await prisma.user.findUniqueOrThrow({
      where: { id: userId },
    });

    if (!user.openfortPlayer) {
      throw new AppError(400, "No smart account linked to this user");
    }

    const quote = await this.getQuote(
      fromToken,
      toToken,
      amount,
      chainId,
      user.smartAccount
    );

    // Validate the quote is still fresh
    if (quote.validUntil < Math.floor(Date.now() / 1000)) {
      throw new AppError(408, "Quote has expired — please request a new quote");
    }

    const swapRouter = quote.to;
    const isNativeFrom = fromToken.toLowerCase() === "native" ||
                         fromToken === "0xEeeeeEeeeEeEeeEeEeEeeEEEeeeeEeeeeeeeEEeE";

    // Build interaction list:
    // For ERC-20 sells: [approve swapRouter, swap]
    // For native sells: [swap with value]
    const interactions: Array<{
      contract: string;
      functionName: string;
      functionArgs: any[];
      value?: string;
    }> = [];

    if (!isNativeFrom) {
      // Step 1: Approve the swap router to spend fromToken
      interactions.push({
        contract: fromToken,
        functionName: "approve",
        functionArgs: [swapRouter, amount],
      });
    }

    // Step 2: Execute the swap via the router's encoded calldata
    // Since Openfort takes (contract, functionName, functionArgs), we use a raw
    // `fallback` call with the pre-encoded data from 0x.
    // In production with real 0x data, pass the raw calldata using Openfort's
    // rawTransaction or custom interaction type.
    interactions.push({
      contract: swapRouter,
      functionName: "transformERC20",  // 0x v4 entry-point (simplification)
      functionArgs: [
        fromToken,
        toToken,
        amount,
        // minBuyAmount with slippage applied
        (BigInt(quote.toAmount) * BigInt(10000 - slippageBps) / 10000n).toString(),
        [], // transformations — would be populated from 0x calldata in prod
      ],
      ...(isNativeFrom && { value: amount }),
    });

    const intent = await OpenfortService.executeInteraction({
      playerId: user.openfortPlayer,
      contract: swapRouter,
      functionName: "transformERC20",
      functionArgs: interactions[interactions.length - 1].functionArgs,
      chainId,
      ...(isNativeFrom && { value: amount }),
    });

    // Log the UserOp
    await prisma.userOpLog.create({
      data: {
        userId: user.id,
        openfortIntentId: intent.id,
        type: "swap",
        status: "SUBMITTED",
        txHash: intent.response?.transactionHash ?? null,
        chainId,
      },
    });

    return {
      intentId: intent.id,
      txHash: intent.response?.transactionHash ?? undefined,
      status: (intent.response?.status?.toString()) ?? "submitted",
      fromToken,
      toToken,
      fromAmount: amount,
      estimatedToAmount: quote.toAmount,
      chainId,
    };
  }
}
