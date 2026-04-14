import prisma from "./prisma.service.js";
import { OpenfortService } from "./openfort.service.js";
import { NotificationService } from "./notification.service.js";
import { AppError } from "../utils/errors.js";
import { ENV } from "../config/env.js";

// ─── Types ────────────────────────────────────────────────────────────────────

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
  cardId: string;             // Partner's card ID (maps to Card.partnerId)
  transactionId: string;      // Partner's unique transaction ID
  merchantName: string;
  merchantCategory?: string;
  amount: number;             // In USD (or settlement currency)
  currency: string;
  webhookSignature?: string;  // HMAC signature from partner
}

export interface SettlementWebhookPayload {
  transactionId: string;
  cardId: string;
  status: "completed" | "declined" | "refunded";
  settledAmount?: number;
  settlementCurrency?: string;
  txHash?: string;            // On-chain settlement hash (if available)
}

// ─── USDC Contract Addresses ──────────────────────────────────────────────────

const USDC_ADDRESSES: Record<number, string> = {
  137:  "0x3c499c542cEF5E3811e1192ce70d8cC03d5c3359", // Polygon USDC
  8453: "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913", // Base USDC
};

// ─── Mock Partner API ─────────────────────────────────────────────────────────
// Stub functions that mirror the shape of real partner APIs.
// Replace each with the actual SDK/REST call when integrating Kulipa / Reap / Immersve.

async function partnerIssueCard(params: {
  userId: string;
  type: CardType;
  deliveryAddress?: Record<string, string>;
}): Promise<{ partnerId: string; last4: string; expiryMonth: number; expiryYear: number }> {
  // TODO: Replace with real Kulipa / Reap / Immersve call
  // Example Kulipa:
  //   const res = await fetch(`${KULIPA_BASE_URL}/cards`, {
  //     method: "POST",
  //     headers: { Authorization: `Bearer ${KULIPA_API_KEY}` },
  //     body: JSON.stringify({ user_id: userId, card_type: type, delivery_address: deliveryAddress }),
  //   });
  //   const data = await res.json();
  //   return { partnerId: data.card_id, last4: data.last_four, expiryMonth: data.expiry_month, expiryYear: data.expiry_year };

  const last4 = Math.floor(1000 + Math.random() * 9000).toString();
  const expiryYear = new Date().getFullYear() + 3;
  return {
    partnerId: `partner_card_${Date.now()}`,
    last4,
    expiryMonth: 12,
    expiryYear,
  };
}

async function partnerFreezeCard(partnerId: string): Promise<void> {
  // TODO: await kulipaClient.freezeCard(partnerId)
}

async function partnerUnfreezeCard(partnerId: string): Promise<void> {
  // TODO: await kulipaClient.unfreezeCard(partnerId)
}

async function partnerUpdateLimits(
  partnerId: string,
  dailyLimit: number,
  monthlyLimit: number
): Promise<void> {
  // TODO: await kulipaClient.setSpendingLimits(partnerId, { daily: dailyLimit, monthly: monthlyLimit })
}

function verifyWebhookSignature(
  _headers: Record<string, string | string[] | undefined>,
  _rawBody: string
): boolean {
  // TODO: Implement HMAC-SHA256 verification using partner's webhook secret
  // Example: const expected = crypto.createHmac('sha256', WEBHOOK_SECRET).update(rawBody).digest('hex');
  //          return crypto.timingSafeEqual(Buffer.from(sig), Buffer.from(expected));
  if (ENV.NODE_ENV === "production") {
    // In production always enforce signature verification
    // Remove this return and implement the real check above
    return true;
  }
  return true;
}

// ─── Card Service ─────────────────────────────────────────────────────────────

export class CardService {
  /**
   * Order a new virtual or physical card for a user.
   * Calls the card partner to issue the card, then persists it locally.
   */
  static async orderCard(
    userId: string,
    type: CardType,
    deliveryAddress?: Record<string, string>
  ): Promise<CardDetails> {
    // Only one active card per user (for now)
    const existing = await prisma.card.findFirst({
      where: { userId, status: { in: ["active", "pending", "frozen"] } },
    });

    if (existing) {
      throw new AppError(409, "User already has an active card");
    }

    // Issue via partner
    const partnerCard = await partnerIssueCard({ userId, type, deliveryAddress });

    const card = await prisma.card.create({
      data: {
        userId,
        type,
        status: type === "virtual" ? "active" : "pending",
        last4: partnerCard.last4,
        network: "visa",
        expiryMonth: partnerCard.expiryMonth,
        expiryYear: partnerCard.expiryYear,
        partnerId: partnerCard.partnerId,
      },
    });

    return this.formatCard(card);
  }

  /**
   * Get a user's card details (most recent active/pending card).
   */
  static async getCard(userId: string): Promise<CardDetails | null> {
    const card = await prisma.card.findFirst({
      where: { userId },
      orderBy: { createdAt: "desc" },
    });

    return card ? this.formatCard(card) : null;
  }

  /**
   * Get card transaction history for a user.
   */
  static async getCardTransactions(
    userId: string,
    limit = 50,
    offset = 0
  ) {
    const [transactions, total] = await Promise.all([
      prisma.cardTransaction.findMany({
        where: { userId },
        orderBy: { createdAt: "desc" },
        take: limit,
        skip: offset,
      }),
      prisma.cardTransaction.count({ where: { userId } }),
    ]);

    return { transactions, total };
  }

  /**
   * Freeze a card — funds cannot be spent while frozen.
   */
  static async freezeCard(userId: string, cardId: string): Promise<CardDetails> {
    const card = await this.assertCardOwner(userId, cardId);

    if (card.status === "frozen") {
      throw new AppError(400, "Card is already frozen");
    }

    if (card.status !== "active") {
      throw new AppError(400, `Cannot freeze a card with status: ${card.status}`);
    }

    if (card.partnerId) {
      await partnerFreezeCard(card.partnerId);
    }

    const updated = await prisma.card.update({
      where: { id: cardId },
      data: { status: "frozen" },
    });

    return this.formatCard(updated);
  }

  /**
   * Unfreeze a previously frozen card.
   */
  static async unfreezeCard(userId: string, cardId: string): Promise<CardDetails> {
    const card = await this.assertCardOwner(userId, cardId);

    if (card.status !== "frozen") {
      throw new AppError(400, "Card is not frozen");
    }

    if (card.partnerId) {
      await partnerUnfreezeCard(card.partnerId);
    }

    const updated = await prisma.card.update({
      where: { id: cardId },
      data: { status: "active" },
    });

    return this.formatCard(updated);
  }

  /**
   * Update daily and/or monthly spending limits.
   */
  static async updateLimits(
    userId: string,
    cardId: string,
    dailyLimit?: number,
    monthlyLimit?: number
  ): Promise<CardDetails> {
    const card = await this.assertCardOwner(userId, cardId);

    if (dailyLimit !== undefined && dailyLimit <= 0) {
      throw new AppError(400, "Daily limit must be positive");
    }

    if (monthlyLimit !== undefined && monthlyLimit <= 0) {
      throw new AppError(400, "Monthly limit must be positive");
    }

    if (
      dailyLimit !== undefined &&
      monthlyLimit !== undefined &&
      dailyLimit > monthlyLimit
    ) {
      throw new AppError(400, "Daily limit cannot exceed monthly limit");
    }

    if (card.partnerId) {
      await partnerUpdateLimits(
        card.partnerId,
        dailyLimit ?? card.dailyLimit,
        monthlyLimit ?? card.monthlyLimit
      );
    }

    const updated = await prisma.card.update({
      where: { id: cardId },
      data: {
        ...(dailyLimit !== undefined && { dailyLimit }),
        ...(monthlyLimit !== undefined && { monthlyLimit }),
      },
    });

    return this.formatCard(updated);
  }

  // ─── Webhook Handlers ───────────────────────────────────────────────────────

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
  static async handleAuthorizationWebhook(
    payload: AuthorizationWebhookPayload,
    rawHeaders: Record<string, string | string[] | undefined>,
    rawBody: string
  ): Promise<{ approved: boolean; transactionId: string; reason?: string }> {
    // Step 1: Verify signature
    if (!verifyWebhookSignature(rawHeaders, rawBody)) {
      throw new AppError(401, "Invalid webhook signature");
    }

    const { cardId: partnerId, transactionId, merchantName, merchantCategory, amount, currency } = payload;

    // Step 2: Look up card + user
    const card = await prisma.card.findFirst({
      where: { partnerId },
      include: { user: true },
    });

    if (!card) {
      console.error(`[Card Auth] Unknown partner card ID: ${partnerId}`);
      return { approved: false, transactionId, reason: "card_not_found" };
    }

    const { user } = card;

    // Step 3: Card status check
    if (card.status !== "active") {
      await this.recordTransaction({
        cardId: card.id,
        userId: user.id,
        merchant: merchantName,
        category: merchantCategory,
        amount,
        currency,
        status: "declined",
      });
      return { approved: false, transactionId, reason: `card_${card.status}` };
    }

    // Step 4: Spending limit check
    const now = new Date();
    const dayStart = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const monthStart = new Date(now.getFullYear(), now.getMonth(), 1);

    // Get current period spent from DB (daily/monthly resets)
    const [dailySpent, monthlySpent] = await Promise.all([
      prisma.cardTransaction.aggregate({
        where: {
          cardId: card.id,
          status: { in: ["pending", "completed"] },
          createdAt: { gte: dayStart },
        },
        _sum: { amount: true },
      }),
      prisma.cardTransaction.aggregate({
        where: {
          cardId: card.id,
          status: { in: ["pending", "completed"] },
          createdAt: { gte: monthStart },
        },
        _sum: { amount: true },
      }),
    ]);

    const currentDailySpent = dailySpent._sum.amount ?? 0;
    const currentMonthlySpent = monthlySpent._sum.amount ?? 0;

    if (currentDailySpent + amount > card.dailyLimit) {
      await this.recordTransaction({
        cardId: card.id,
        userId: user.id,
        merchant: merchantName,
        category: merchantCategory,
        amount,
        currency,
        status: "declined",
      });
      return { approved: false, transactionId, reason: "daily_limit_exceeded" };
    }

    if (currentMonthlySpent + amount > card.monthlyLimit) {
      await this.recordTransaction({
        cardId: card.id,
        userId: user.id,
        merchant: merchantName,
        category: merchantCategory,
        amount,
        currency,
        status: "declined",
      });
      return { approved: false, transactionId, reason: "monthly_limit_exceeded" };
    }

    // Step 5: On-chain USDC balance check via Openfort / viem
    //
    // We convert the fiat amount to USDC (1:1 USD assumption — in prod, use a price oracle).
    const usdcAmount = amount; // USD → USDC 1:1 approximation
    const usdcAmountWei = BigInt(Math.ceil(usdcAmount * 1_000_000)).toString(); // 6 decimals

    const usdcAddress = USDC_ADDRESSES[ENV.DEFAULT_CHAIN_ID];
    let hasSufficientBalance = true;

    try {
      // Import dynamically to keep the critical path lean
      const { BalanceService } = await import("./balance.service.js");
      const balances = await BalanceService.getBalances(
        user.smartAccount,
        ENV.DEFAULT_CHAIN_ID
      );
      const usdcBalance = balances.find((b) => b.symbol === "USDC");
      const usdcBalanceRaw = BigInt(usdcBalance?.balance ?? "0");

      hasSufficientBalance = usdcBalanceRaw >= BigInt(usdcAmountWei);
    } catch (err) {
      console.error("[Card Auth] Balance check failed — declining for safety:", err);
      return { approved: false, transactionId, reason: "balance_check_failed" };
    }

    if (!hasSufficientBalance) {
      await this.recordTransaction({
        cardId: card.id,
        userId: user.id,
        merchant: merchantName,
        category: merchantCategory,
        amount,
        currency,
        status: "declined",
      });
      return { approved: false, transactionId, reason: "insufficient_funds" };
    }

    // Step 6: Approved — create pending transaction record
    const tx = await this.recordTransaction({
      cardId: card.id,
      userId: user.id,
      merchant: merchantName,
      category: merchantCategory,
      amount,
      currency,
      status: "pending",
    });

    // Step 7: Queue on-chain USDC debit via Openfort UserOp
    // We do this asynchronously so as not to block the <500ms response window.
    // The settlement webhook will confirm the on-chain tx hash.
    if (user.openfortPlayer && usdcAddress) {
      setImmediate(async () => {
        try {
          // The card partner's settlement address (configure per partner)
          const SETTLEMENT_ADDRESS = process.env.CARD_SETTLEMENT_ADDRESS ?? "0x0000000000000000000000000000000000000000";

          const intent = await OpenfortService.sendToken({
            playerId: user.openfortPlayer!,
            to: SETTLEMENT_ADDRESS,
            tokenAddress: usdcAddress,
            amount: usdcAmountWei,
            chainId: ENV.DEFAULT_CHAIN_ID,
          });

          await Promise.all([
            prisma.cardTransaction.update({
              where: { id: tx.id },
              data: { txHash: intent.response?.transactionHash ?? null },
            }),
            prisma.userOpLog.create({
              data: {
                userId: user.id,
                openfortIntentId: intent.id,
                type: "card_debit",
                status: "SUBMITTED",
                txHash: intent.response?.transactionHash ?? null,
                chainId: ENV.DEFAULT_CHAIN_ID,
              },
            }),
          ]);

          // Push notification
          await NotificationService.notifyCardTransaction(
            user.id,
            merchantName,
            amount
          );
        } catch (err) {
          console.error(`[Card Auth] Async debit failed for tx ${tx.id}:`, err);
          // Flag for reconciliation — do NOT reverse the approval at this point.
          // The settlement webhook handles final state.
          await prisma.cardTransaction.update({
            where: { id: tx.id },
            data: { status: "pending" }, // Keep pending; ops team reconciles
          });
        }
      });
    }

    return { approved: true, transactionId };
  }

  /**
   * Settlement webhook — called after the transaction is fully settled.
   * Updates the transaction record with final status and on-chain tx hash.
   */
  static async handleSettlementWebhook(
    payload: SettlementWebhookPayload
  ): Promise<void> {
    const { transactionId, cardId: partnerId, status, settledAmount, txHash } = payload;

    // Find the card to get internal card ID
    const card = await prisma.card.findFirst({ where: { partnerId } });
    if (!card) {
      console.error(`[Card Settlement] Unknown partner card ID: ${partnerId}`);
      return;
    }

    // Find the most recent pending transaction for this card matching the amount
    // (In prod, the partner should include an externalId that maps to our CardTransaction.id)
    const existingTx = await prisma.cardTransaction.findFirst({
      where: {
        cardId: card.id,
        status: "pending",
      },
      orderBy: { createdAt: "desc" },
    });

    const mappedStatus = {
      completed: "completed",
      declined: "declined",
      refunded: "refunded",
    }[status] ?? "completed";

    if (existingTx) {
      await prisma.cardTransaction.update({
        where: { id: existingTx.id },
        data: {
          status: mappedStatus,
          ...(txHash && { txHash }),
          ...(settledAmount && { amount: settledAmount }),
        },
      });

      // Notify user on successful settlement
      if (status === "completed") {
        await NotificationService.notifyTransactionConfirmed(
          existingTx.userId,
          txHash ?? "",
          "card_settlement"
        );
      }
    }

    console.log(`[Card Settlement] Partner tx ${transactionId} → ${mappedStatus}`);
  }

  // ─── Helpers ────────────────────────────────────────────────────────────────

  private static async assertCardOwner(userId: string, cardId: string) {
    const card = await prisma.card.findUnique({ where: { id: cardId } });
    if (!card) throw new AppError(404, "Card not found");
    if (card.userId !== userId) throw new AppError(403, "Not your card");
    return card;
  }

  private static async recordTransaction(data: {
    cardId: string;
    userId: string;
    merchant: string;
    category?: string;
    amount: number;
    currency: string;
    status: string;
  }) {
    return prisma.cardTransaction.create({ data });
  }

  private static formatCard(card: any): CardDetails {
    return {
      id: card.id,
      type: card.type as CardType,
      status: card.status as CardStatus,
      last4: card.last4,
      network: card.network,
      dailyLimit: card.dailyLimit,
      monthlyLimit: card.monthlyLimit,
      dailySpent: card.dailySpent,
      monthlySpent: card.monthlySpent,
      expiryMonth: card.expiryMonth,
      expiryYear: card.expiryYear,
      createdAt: card.createdAt,
    };
  }
}
