import prisma from "./prisma.service.js";
// ─── Mock FCM Client ──────────────────────────────────────────────────────────
// Replace sendFcmMessage() with the actual Firebase Admin SDK call.
//
// Real implementation:
//   import admin from "firebase-admin";
//   admin.initializeApp({ credential: admin.credential.cert(serviceAccount) });
//
//   async function sendFcmMessage(message: FcmMessage): Promise<string> {
//     return admin.messaging().send(message);
//   }
async function sendFcmMessage(message) {
    // TODO: Replace with real Firebase Admin SDK call
    if (process.env.NODE_ENV !== "test") {
        console.log(`[FCM] → ${message.token.slice(0, 12)}... | ${message.notification.title}: ${message.notification.body}`);
    }
    // Return a mock message ID
    return `mock_fcm_${Date.now()}`;
}
// ─── Notification Service ─────────────────────────────────────────────────────
export class NotificationService {
    /**
     * Register or refresh an FCM device token for a user.
     */
    static async registerToken(userId, token, platform) {
        // Upsert by token — one row per device token, tied to a user
        const existing = await prisma.notificationToken.findFirst({
            where: { token },
        });
        if (existing) {
            // Reassign to current user (e.g. device logged out and back in)
            if (existing.userId !== userId) {
                await prisma.notificationToken.update({
                    where: { id: existing.id },
                    data: { userId },
                });
            }
            return;
        }
        await prisma.notificationToken.create({
            data: { userId, token, platform },
        });
    }
    /**
     * Remove a specific FCM token (on logout / token refresh).
     */
    static async removeToken(userId, token) {
        await prisma.notificationToken.deleteMany({
            where: { userId, token },
        });
    }
    /**
     * Send a push notification to all of a user's registered devices.
     * Silently drops stale/invalid tokens.
     */
    static async sendPushNotification(userId, title, body, data) {
        const tokens = await prisma.notificationToken.findMany({
            where: { userId },
        });
        if (tokens.length === 0) {
            return; // User has no registered devices — not an error
        }
        const results = await Promise.allSettled(tokens.map((t) => sendFcmMessage({
            token: t.token,
            notification: { title, body },
            ...(data && { data }),
        })));
        // Log failures (stale tokens should be removed in prod via FCM feedback)
        results.forEach((result, i) => {
            if (result.status === "rejected") {
                console.warn(`[Notifications] Failed to send to token ${tokens[i]?.token.slice(0, 12)}...: ${result.reason}`);
            }
        });
    }
    /**
     * Notify user when an on-chain transaction is confirmed.
     */
    static async notifyTransactionConfirmed(userId, txHash, type) {
        const typeLabels = {
            transfer: "Transfer confirmed",
            swap: "Swap completed",
            card_settlement: "Card payment settled",
            dripper_withdraw: "Salary withdrawal confirmed",
        };
        const title = typeLabels[type] ?? "Transaction confirmed";
        const shortHash = txHash ? `${txHash.slice(0, 8)}…` : "";
        const body = shortHash
            ? `Your transaction ${shortHash} has been confirmed on-chain.`
            : "Your transaction has been confirmed on-chain.";
        await this.sendPushNotification(userId, title, body, {
            type,
            txHash: txHash ?? "",
        });
    }
    /**
     * Notify user of a card authorization (real-time spend alert).
     */
    static async notifyCardTransaction(userId, merchant, amount) {
        const formattedAmount = amount.toLocaleString("en-US", {
            style: "currency",
            currency: "USD",
        });
        await this.sendPushNotification(userId, "Card payment", `${formattedAmount} at ${merchant}`, {
            type: "card_authorization",
            merchant,
            amount: amount.toString(),
        });
    }
    /**
     * Notify user of a salary stream withdrawal.
     */
    static async notifyStreamWithdrawal(userId, streamId, amount) {
        await this.sendPushNotification(userId, "Salary withdrawn", `${amount} USDC has been withdrawn from your stream.`, {
            type: "stream_withdrawal",
            streamId,
            amount,
        });
    }
    /**
     * Notify user of a declined card authorization.
     */
    static async notifyCardDeclined(userId, merchant, reason) {
        const reasonLabels = {
            insufficient_funds: "Insufficient balance",
            daily_limit_exceeded: "Daily limit reached",
            monthly_limit_exceeded: "Monthly limit reached",
            card_frozen: "Card is frozen",
        };
        const displayReason = reasonLabels[reason] ?? "Transaction declined";
        await this.sendPushNotification(userId, "Card declined", `${displayReason} — ${merchant}`, {
            type: "card_declined",
            merchant,
            reason,
        });
    }
}
//# sourceMappingURL=notification.service.js.map