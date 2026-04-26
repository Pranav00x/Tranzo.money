import { Router } from "express";
import { z } from "zod";
import { parseEther } from "viem";
import { requireAuth } from "../middleware/auth.js";
import { sensitiveLimiter } from "../middleware/rateLimit.js";
import { CardService } from "../services/card.service.js";
import { AppError } from "../utils/errors.js";
const router = Router();
/**
 * Tranzo Card Routes
 *
 * Card issuance and management via partner APIs:
 *  - Kulipa:   Virtual/Physical Visa card issuance (EU/UK)
 *  - Reap:     Card-to-crypto settlement infrastructure (APAC)
 *  - Immersve: Decentralised card protocol (self-custody spend)
 *
 * Settlement flow:
 *  1. User swipes card at merchant
 *  2. Authorization request hits our webhook
 *  3. We verify on-chain balance via smart account
 *  4. Approve/decline in <500ms
 *  5. On approval: debit USDC from smart account via Openfort UserOp
 */
// ─── Validation Schemas ──────────────────────────────────────────────────────
const orderCardSchema = z.object({
    type: z.enum(["virtual", "physical"]),
    deliveryAddress: z
        .object({
        line1: z.string(),
        line2: z.string().optional(),
        city: z.string(),
        state: z.string().optional(),
        postalCode: z.string(),
        country: z.string().length(2, "Use ISO 3166-1 alpha-2 country code"),
    })
        .optional(),
});
const limitsSchema = z.object({
    dailyLimit: z.number().positive().optional(),
    monthlyLimit: z.number().positive().optional(),
});
const cardIdParamSchema = z.object({
    cardId: z.string().uuid(),
});
const webhookAuthSchema = z.object({
    cardId: z.string(),
    transactionId: z.string(),
    merchantName: z.string(),
    merchantCategory: z.string().optional(),
    amount: z.number().positive(),
    currency: z.string(),
});
const webhookSettlementSchema = z.object({
    transactionId: z.string(),
    cardId: z.string(),
    status: z.enum(["completed", "declined", "refunded"]),
    settledAmount: z.number().optional(),
    settlementCurrency: z.string().optional(),
    txHash: z.string().optional(),
});
// ─── Card Management ─────────────────────────────────────────────────────────
/**
 * GET /card
 * Get user's card info (status, last4, limits, spend totals).
 */
router.get("/", requireAuth, async (req, res) => {
    try {
        const card = await CardService.getCard(req.user.sub);
        if (!card) {
            res.json({ success: true, card: null, hasCard: false });
            return;
        }
        res.json({ success: true, card, hasCard: true });
    }
    catch (err) {
        if (err instanceof AppError) {
            res.status(err.statusCode).json({ error: err.message });
            return;
        }
        res.status(500).json({ error: err.message });
    }
});
/**
 * POST /card/order
 * Order a new virtual or physical card.
 * Body: { type: "virtual" | "physical", deliveryAddress?: { ... } }
 */
router.post("/order", requireAuth, sensitiveLimiter, async (req, res) => {
    try {
        const { type, deliveryAddress } = orderCardSchema.parse(req.body);
        if (type === "physical" && !deliveryAddress) {
            res
                .status(400)
                .json({ error: "Delivery address is required for physical cards" });
            return;
        }
        const card = await CardService.orderCard(req.user.sub, type, deliveryAddress);
        res.status(201).json({
            success: true,
            card,
            message: type === "virtual"
                ? "Virtual card issued. Add to Apple Pay or Google Pay."
                : "Physical card ordered. Ships in 5–7 business days.",
        });
    }
    catch (err) {
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
});
/**
 * POST /card/:cardId/freeze
 * Temporarily freeze the card. Funds cannot be spent while frozen.
 */
router.post("/:cardId/freeze", requireAuth, async (req, res) => {
    try {
        const { cardId } = cardIdParamSchema.parse(req.params);
        const card = await CardService.freezeCard(req.user.sub, cardId);
        res.json({ success: true, card });
    }
    catch (err) {
        if (err instanceof AppError) {
            res.status(err.statusCode).json({ error: err.message });
            return;
        }
        res.status(500).json({ error: err.message });
    }
});
/**
 * POST /card/:cardId/unfreeze
 * Reactivate a frozen card.
 */
router.post("/:cardId/unfreeze", requireAuth, async (req, res) => {
    try {
        const { cardId } = cardIdParamSchema.parse(req.params);
        const card = await CardService.unfreezeCard(req.user.sub, cardId);
        res.json({ success: true, card });
    }
    catch (err) {
        if (err instanceof AppError) {
            res.status(err.statusCode).json({ error: err.message });
            return;
        }
        res.status(500).json({ error: err.message });
    }
});
/**
 * POST /card/:cardId/activate
 * CTO LOGIC: Perform the one-time on-chain session key installation.
 * Body: { spendLimitEth: string }
 */
router.post("/:cardId/activate", requireAuth, async (req, res) => {
    try {
        const { cardId } = cardIdParamSchema.parse(req.params);
        const { spendLimitEth } = z.object({ spendLimitEth: z.string() }).parse(req.body);
        const result = await CardService.activateCardOnChain(req.user.sub, cardId, spendLimitEth);
        res.json({ success: true, ...result });
    }
    catch (err) {
        if (err instanceof AppError) {
            res.status(err.statusCode).json({ error: err.message });
            return;
        }
        res.status(500).json({ error: err.message });
    }
});
/**
 * POST /card/pay
 * CTO LOGIC: Merchant endpoint to process a card payment via session key.
 * Body: { cardNumber: string, cvv: string, merchantAddress: string, amount: string }
 */
router.post("/pay", async (req, res) => {
    try {
        const { cardNumber, cvv, merchantAddress, amount } = z.object({
            cardNumber: z.string(),
            cvv: z.string(),
            merchantAddress: z.string(),
            amount: z.string(), // ETH amount
        }).parse(req.body);
        const amountWei = parseEther(amount);
        const result = await CardService.processCardPayment(cardNumber, cvv, merchantAddress, amountWei);
        res.json({ success: true, ...result });
    }
    catch (err) {
        res.status(500).json({ error: err.message });
    }
});
/**
 * PUT /card/:cardId/limits
 * Update spending limits.
 * Body: { dailyLimit?: number, monthlyLimit?: number }
 */
router.put("/:cardId/limits", requireAuth, async (req, res) => {
    try {
        const { cardId } = cardIdParamSchema.parse(req.params);
        const { dailyLimit, monthlyLimit } = limitsSchema.parse(req.body);
        if (dailyLimit === undefined && monthlyLimit === undefined) {
            res
                .status(400)
                .json({ error: "At least one of dailyLimit or monthlyLimit is required" });
            return;
        }
        const card = await CardService.updateLimits(req.user.sub, cardId, dailyLimit, monthlyLimit);
        res.json({ success: true, card });
    }
    catch (err) {
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
});
/**
 * GET /card/transactions
 * Get card transaction history.
 * Query: ?limit=50&offset=0
 */
router.get("/transactions", requireAuth, async (req, res) => {
    try {
        const limit = Math.min(parseInt(req.query.limit) || 50, 200);
        const offset = parseInt(req.query.offset) || 0;
        const { transactions, total } = await CardService.getCardTransactions(req.user.sub, limit, offset);
        res.json({ success: true, transactions, total, limit, offset });
    }
    catch (err) {
        res.status(500).json({ error: err.message });
    }
});
// ─── Webhooks ────────────────────────────────────────────────────────────────
// These endpoints are called by the card partner — no JWT auth, but signature-verified.
/**
 * POST /card/webhook/authorization
 *
 * Called when user swipes card. Critical path — must respond in <500ms.
 * We verify balance on-chain and return approve/decline synchronously.
 * The crypto debit (UserOp) is dispatched asynchronously after approval.
 */
router.post("/webhook/authorization", async (req, res) => {
    const startTime = Date.now();
    try {
        const payload = webhookAuthSchema.parse(req.body);
        const rawBody = JSON.stringify(req.body);
        const result = await CardService.handleAuthorizationWebhook(payload, req.headers, rawBody);
        const elapsed = Date.now() - startTime;
        console.log(`[Card Auth] ${payload.merchantName} $${payload.amount} — ${result.approved ? "APPROVED" : "DECLINED"} (${elapsed}ms)`);
        res.json(result);
    }
    catch (err) {
        const elapsed = Date.now() - startTime;
        console.error(`[Card Auth] Error after ${elapsed}ms:`, err.message);
        // ALWAYS decline on error — never approve if we can't verify
        res.json({
            approved: false,
            transactionId: req.body?.transactionId ?? "unknown",
            reason: "internal_error",
        });
    }
});
/**
 * POST /card/webhook/settlement
 *
 * Called after a transaction is fully settled (crypto → fiat complete).
 * Updates the CardTransaction record with final status and on-chain hash.
 */
router.post("/webhook/settlement", async (req, res) => {
    try {
        const payload = webhookSettlementSchema.parse(req.body);
        await CardService.handleSettlementWebhook(payload);
        res.json({ received: true });
    }
    catch (err) {
        if (err.name === "ZodError") {
            res.status(400).json({ error: "Invalid webhook payload", details: err.errors });
            return;
        }
        console.error("[Card Settlement] Error:", err.message);
        // Always ACK the webhook to prevent retries — we'll reconcile via logs
        res.json({ received: true, error: err.message });
    }
});
export default router;
//# sourceMappingURL=card.routes.js.map