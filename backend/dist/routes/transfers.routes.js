import { Router } from "express";
import { z } from "zod";
import { requireAuth } from "../middleware/auth.js";
import { TransactionService } from "../services/transaction.service.js";
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
        if (!user.smartAccount) {
            res.status(400).json({ error: "No smart account created" });
            return;
        }
        // Submit transfer via ZeroDev
        const result = await TransactionService.sendToken({
            userId: user.id,
            to: params.to,
            tokenAddress: params.tokenAddress,
            amount: params.amount,
            chainId: params.chainId,
        });
        // Log the UserOp
        await prisma.userOpLog.create({
            data: {
                userId: user.id,
                opHash: result.opHash,
                type: "transfer",
                status: result.status,
                chainId: result.chainId,
            },
        });
        res.json({
            opHash: result.opHash,
            status: result.status,
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
        if (!user.smartAccount) {
            res.json({ transactions: [] });
            return;
        }
        const history = await TransactionService.getTransactionHistory(user.id, parseInt(req.query.limit) || 20);
        res.json({ transactions: history.data });
    }
    catch (err) {
        res.status(500).json({ error: err.message });
    }
});
// ─── UserOp Status ─────────────────────────────────────────────
router.get("/status/:opHash", requireAuth, async (req, res) => {
    try {
        const status = await TransactionService.getTransactionStatus(req.params.opHash);
        res.json(status);
    }
    catch (err) {
        res.status(404).json({ error: err.message });
    }
});
export default router;
//# sourceMappingURL=transfers.routes.js.map