import { Router, Request, Response } from "express";
import { authMiddleware } from "../middleware/auth";
import { sensitiveRateLimit } from "../middleware/rateLimit";

const router = Router();

/**
 * Tranzo Card Routes
 *
 * Card issuance and management via partner APIs:
 *  - Kulipa:   Virtual/Physical Visa card issuance (EU/UK)
 *  - Reap:     Card-to-crypto settlement infrastructure (APAC)
 *  - Immersve: Decentralized card protocol (self-custody spend)
 *
 * Settlement flow:
 *  1. User swipes card at merchant
 *  2. Authorization request hits our webhook
 *  3. We verify user's on-chain balance via smart account
 *  4. Approve/decline in <500ms
 *  5. On approval: debit crypto from smart account → convert to USDC → settle fiat
 *
 * Key principle: User's funds NEVER leave their smart account
 * until the moment of purchase. True self-custody spending.
 */

// ── Card Management ────────────────────────────────────────────

/**
 * GET /card
 * Get user's card info (status, last4, limits, etc.)
 */
router.get("/", authMiddleware, async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    // TODO: Fetch from card partner API (Kulipa/Reap/Immersve)
    // const card = await kulipaService.getCard(userId);
    res.json({
      success: true,
      card: {
        id: "card_placeholder",
        last4: "0000",
        type: "virtual",
        status: "pending_kyc",
        network: "visa",
        dailyLimit: 1000,
        monthlyLimit: 10000,
        dailySpent: 0,
        monthlySpent: 0,
      },
    });
  } catch (err: any) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * POST /card/order
 * Order a new card (virtual or physical).
 * Requires KYC to be completed.
 *
 * Body: { type: "virtual" | "physical", deliveryAddress?: { ... } }
 */
router.post(
  "/order",
  authMiddleware,
  sensitiveRateLimit,
  async (req: Request, res: Response) => {
    try {
      const userId = (req as any).userId;
      const { type, deliveryAddress } = req.body;

      if (!type || !["virtual", "physical"].includes(type)) {
        return res.status(400).json({ error: "Invalid card type" });
      }

      // TODO: Integration flow:
      // 1. Check KYC status via Kulipa/Reap
      // 2. If not KYC'd, return KYC redirect URL
      // 3. If KYC'd, issue card via partner API
      // 4. For physical: submit delivery address
      //
      // Kulipa example:
      // const kycStatus = await kulipaService.getKycStatus(userId);
      // if (kycStatus !== 'approved') {
      //   return res.json({ requiresKyc: true, kycUrl: kulipaService.getKycUrl(userId) });
      // }
      // const card = await kulipaService.issueCard(userId, type);
      //
      // Immersve example (decentralized):
      // const card = await immersveService.createCard(smartAccountAddress, type);

      res.json({
        success: true,
        card: {
          id: "card_new",
          last4: "4291",
          type,
          status: type === "virtual" ? "active" : "shipping",
          network: "visa",
        },
        message:
          type === "virtual"
            ? "Virtual card issued! Add to Apple Pay."
            : "Physical card ordered! Ships in 5-7 days.",
      });
    } catch (err: any) {
      res.status(500).json({ error: err.message });
    }
  }
);

/**
 * POST /card/freeze
 * Temporarily freeze the card.
 */
router.post(
  "/freeze",
  authMiddleware,
  async (req: Request, res: Response) => {
    try {
      const userId = (req as any).userId;
      // TODO: await kulipaService.freezeCard(userId);
      res.json({ success: true, status: "frozen" });
    } catch (err: any) {
      res.status(500).json({ error: err.message });
    }
  }
);

/**
 * POST /card/unfreeze
 * Reactivate a frozen card.
 */
router.post(
  "/unfreeze",
  authMiddleware,
  async (req: Request, res: Response) => {
    try {
      const userId = (req as any).userId;
      // TODO: await kulipaService.unfreezeCard(userId);
      res.json({ success: true, status: "active" });
    } catch (err: any) {
      res.status(500).json({ error: err.message });
    }
  }
);

/**
 * PUT /card/limits
 * Update spending limits.
 *
 * Body: { dailyLimit?: number, monthlyLimit?: number }
 */
router.put(
  "/limits",
  authMiddleware,
  async (req: Request, res: Response) => {
    try {
      const userId = (req as any).userId;
      const { dailyLimit, monthlyLimit } = req.body;
      // TODO: await kulipaService.updateLimits(userId, { dailyLimit, monthlyLimit });
      res.json({ success: true, dailyLimit, monthlyLimit });
    } catch (err: any) {
      res.status(500).json({ error: err.message });
    }
  }
);

/**
 * GET /card/transactions
 * Get card transaction history.
 *
 * Query: ?limit=50&offset=0
 */
router.get(
  "/transactions",
  authMiddleware,
  async (req: Request, res: Response) => {
    try {
      const userId = (req as any).userId;
      const limit = parseInt(req.query.limit as string) || 50;
      const offset = parseInt(req.query.offset as string) || 0;
      // TODO: const txns = await kulipaService.getTransactions(userId, limit, offset);
      res.json({
        success: true,
        transactions: [],
        total: 0,
      });
    } catch (err: any) {
      res.status(500).json({ error: err.message });
    }
  }
);

// ── Webhooks (from card partners) ──────────────────────────────

/**
 * POST /card/webhook/authorization
 * Called by Kulipa/Reap/Immersve when user swipes card at merchant.
 *
 * This is the critical path — we must respond in <500ms:
 * 1. Look up user by card ID
 * 2. Check on-chain balance via smart account
 * 3. Verify spending limits
 * 4. Approve or decline
 * 5. If approved: queue crypto debit via Openfort
 */
router.post("/webhook/authorization", async (req: Request, res: Response) => {
  try {
    const {
      cardId,
      merchantName,
      merchantCategory,
      amount,
      currency,
      transactionId,
    } = req.body;

    // TODO: Verify webhook signature from partner
    // const isValid = kulipaService.verifyWebhookSignature(req.headers, req.body);

    // TODO: Critical authorization flow:
    // 1. const user = await prisma.user.findFirst({ where: { cardId } });
    // 2. const balance = await balanceService.getBalance(user.smartAccount, 'USDC');
    // 3. const withinLimits = checkLimits(user, amount);
    // 4. if (balance >= amount && withinLimits) {
    //      await openfortService.createTransactionIntent(user, { debit: amount });
    //      return res.json({ approved: true });
    //    }
    // 5. return res.json({ approved: false, reason: 'insufficient_funds' });

    console.log(
      `[Card Auth] ${merchantName} — ${currency} ${amount} — tx: ${transactionId}`
    );

    res.json({
      approved: true,
      transactionId,
    });
  } catch (err: any) {
    // On error, DECLINE — never approve if we can't verify
    res.json({ approved: false, reason: "internal_error" });
  }
});

/**
 * POST /card/webhook/settlement
 * Called after a transaction is fully settled (crypto → fiat complete).
 */
router.post("/webhook/settlement", async (req: Request, res: Response) => {
  try {
    const { transactionId, status, settledAmount, settlementCurrency } =
      req.body;

    // TODO: Update transaction record in DB
    // await prisma.cardTransaction.update({
    //   where: { transactionId },
    //   data: { status: 'settled', settledAmount, settlementCurrency },
    // });

    console.log(`[Card Settlement] tx: ${transactionId} — ${status}`);

    res.json({ received: true });
  } catch (err: any) {
    res.status(500).json({ error: err.message });
  }
});

export default router;
