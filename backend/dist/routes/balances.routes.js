import { Router } from "express";
import { requireAuth } from "../middleware/auth.js";
import { BalanceService } from "../services/balance.service.js";
const router = Router();
/**
 * GET /balances — All token balances for the authenticated user's smart account.
 */
router.get("/", requireAuth, async (req, res) => {
    try {
        const chainId = req.query.chainId
            ? parseInt(req.query.chainId)
            : undefined;
        const balances = await BalanceService.getBalances(req.user.smartAccount, chainId);
        res.json({ balances });
    }
    catch (err) {
        res.status(500).json({ error: err.message });
    }
});
/**
 * GET /tokens — List of supported tokens per chain.
 */
router.get("/tokens", (_req, res) => {
    res.json({
        137: BalanceService.getTokens(137),
        8453: BalanceService.getTokens(8453),
    });
});
export default router;
//# sourceMappingURL=balances.routes.js.map