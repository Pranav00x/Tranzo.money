import { Router } from "express";
import { z } from "zod";
import { requireAuth } from "../middleware/auth.js";
import { OpenfortService } from "../services/openfort.service.js";
import prisma from "../services/prisma.service.js";
const router = Router();
// ─── Send Token ────────────────────────────────────────────────
const sendSchema = z.object({
    to: z.string().startsWith("0x"),
    tokenAddress: z.string(),
    amount: z.string(),
    chainId: z.number().optional(),
});
router.post("/send", requireAuth, async (req, res) => {
    try {
        const params = sendSchema.parse(req.body);
        const user = await prisma.user.findUniqueOrThrow({
            where: { id: req.user.sub },
        });
        if (!user.openfortPlayer) {
            res.status(400).json({ error: "No Openfort player linked" });
            return;
        }
        const intent = await OpenfortService.sendToken({
            playerId: user.openfortPlayer,
            to: params.to,
            tokenAddress: params.tokenAddress,
            amount: params.amount,
            chainId: params.chainId,
        });
        // Log the UserOp
        await prisma.userOpLog.create({
            data: {
                userId: user.id,
                openfortIntentId: intent.id,
                type: "transfer",
                status: "SUBMITTED",
                txHash: intent.response?.transactionHash,
                chainId: params.chainId ?? 137,
            },
        });
        res.json({
            intentId: intent.id,
            txHash: intent.response?.transactionHash,
            status: intent.response?.status,
        });
    }
    catch (err) {
        res.status(400).json({ error: err.message });
    }
});
// ─── Transaction History ───────────────────────────────────────
router.get("/history", requireAuth, async (req, res) => {
    try {
        const user = await prisma.user.findUniqueOrThrow({
            where: { id: req.user.sub },
        });
        if (!user.openfortPlayer) {
            res.json({ transactions: [] });
            return;
        }
        const history = await OpenfortService.getTransactionHistory(user.openfortPlayer, parseInt(req.query.limit) || 20);
        res.json({ transactions: history.data });
    }
    catch (err) {
        res.status(500).json({ error: err.message });
    }
});
// ─── UserOp Status ─────────────────────────────────────────────
router.get("/status/:intentId", requireAuth, async (req, res) => {
    try {
        const status = await OpenfortService.getTransactionStatus(req.params.intentId);
        res.json(status);
    }
    catch (err) {
        res.status(404).json({ error: err.message });
    }
});
export default router;
//# sourceMappingURL=transfers.routes.js.map