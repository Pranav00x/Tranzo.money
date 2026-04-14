import { Router, Request, Response } from "express";
import { z } from "zod";
import { requireAuth } from "../middleware/auth.js";
import { sensitiveLimiter } from "../middleware/rateLimit.js";
import { SwapService } from "../services/swap.service.js";
import { AppError } from "../utils/errors.js";

const router = Router();

// ─── Validation Schemas ───────────────────────────────────────────────────────

const quoteSchema = z.object({
  fromToken: z.string().min(1, "fromToken is required"),
  toToken: z.string().min(1, "toToken is required"),
  amount: z.string().regex(/^\d+$/, "amount must be a positive integer string (in smallest units)"),
  chainId: z.number().int().positive().optional(),
});

const executeSwapSchema = z.object({
  fromToken: z.string().min(1),
  toToken: z.string().min(1),
  amount: z.string().regex(/^\d+$/, "amount must be a positive integer string (in smallest units)"),
  chainId: z.number().int().positive().optional(),
  slippageBps: z.number().int().min(0).max(1000).optional(), // 0–10% expressed in bps
});

// ─── Routes ───────────────────────────────────────────────────────────────────

/**
 * POST /swap/quote
 *
 * Get a swap quote for a given token pair and amount.
 * Does not execute anything on-chain.
 *
 * Body:
 *   fromToken   - Token address (0x…) or symbol to sell
 *   toToken     - Token address (0x…) or symbol to buy
 *   amount      - Amount to sell in token's smallest unit (e.g. "1000000" for 1 USDC)
 *   chainId     - Optional. Defaults to server DEFAULT_CHAIN_ID (137 = Polygon)
 *
 * Response includes estimated output, price impact, gas, calldata, and a quoteId
 * that can be passed to /swap/execute to confirm the swap.
 */
router.post(
  "/quote",
  requireAuth,
  async (req: Request, res: Response): Promise<void> => {
    try {
      const params = quoteSchema.parse(req.body);

      const quote = await SwapService.getQuote(
        params.fromToken,
        params.toToken,
        params.amount,
        params.chainId,
        req.user!.smartAccount
      );

      res.json({ success: true, quote });
    } catch (err: any) {
      if (err instanceof AppError) {
        res.status(err.statusCode).json({ error: err.message });
        return;
      }
      if (err.name === "ZodError") {
        res.status(400).json({ error: "Invalid request", details: err.errors });
        return;
      }
      res.status(500).json({ error: err.message });
    }
  }
);

/**
 * POST /swap/execute
 *
 * Execute a token swap via the user's Openfort smart account.
 * Fetches a fresh quote internally and submits a UserOp.
 *
 * Body:
 *   fromToken   - Token address (0x…) or symbol to sell
 *   toToken     - Token address (0x…) or symbol to buy
 *   amount      - Amount to sell in token's smallest unit
 *   chainId     - Optional chain ID (default: 137)
 *   slippageBps - Optional slippage tolerance in basis points (default: 50 = 0.5%)
 *
 * Returns:
 *   intentId          - Openfort transaction intent ID
 *   txHash            - On-chain tx hash (may be null if still pending)
 *   status            - "submitted" | "confirmed" etc.
 *   estimatedToAmount - Expected output before on-chain execution
 */
router.post(
  "/execute",
  requireAuth,
  sensitiveLimiter,
  async (req: Request, res: Response): Promise<void> => {
    try {
      const params = executeSwapSchema.parse(req.body);

      const result = await SwapService.executeSwap(
        req.user!.sub,
        params.fromToken,
        params.toToken,
        params.amount,
        params.chainId,
        params.slippageBps
      );

      res.status(202).json({ success: true, swap: result });
    } catch (err: any) {
      if (err instanceof AppError) {
        res.status(err.statusCode).json({ error: err.message });
        return;
      }
      if (err.name === "ZodError") {
        res.status(400).json({ error: "Invalid request", details: err.errors });
        return;
      }
      res.status(500).json({ error: err.message });
    }
  }
);

export default router;
