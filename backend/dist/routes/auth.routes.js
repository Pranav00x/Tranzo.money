import { Router } from "express";
import { z } from "zod";
import { AuthService } from "../services/auth.service.js";
import { requireAuth } from "../middleware/auth.js";
import prisma from "../services/prisma.service.js";
const router = Router();
// ─── Email + OTP ───────────────────────────────────────────────
const emailSchema = z.object({
    email: z.string().email(),
});
router.post("/email", async (req, res) => {
    try {
        const { email } = emailSchema.parse(req.body);
        await AuthService.sendOtp(email);
        res.json({ message: "OTP sent" });
    }
    catch (err) {
        res.status(400).json({ error: err.message });
    }
});
const verifyOtpSchema = z.object({
    email: z.string().email(),
    otp: z.string().length(6),
});
router.post("/verify-otp", async (req, res) => {
    try {
        const { email, otp } = verifyOtpSchema.parse(req.body);
        const result = await AuthService.verifyOtp(email, otp);
        res.json(result);
    }
    catch (err) {
        res.status(400).json({ error: err.message });
    }
});
// ─── Google OAuth ──────────────────────────────────────────────
const googleSchema = z.object({
    idToken: z.string(),
});
router.post("/google", async (req, res) => {
    try {
        const { idToken } = googleSchema.parse(req.body);
        const result = await AuthService.loginWithGoogle(idToken);
        res.json(result);
    }
    catch (err) {
        res.status(400).json({ error: err.message });
    }
});
// ─── Refresh Token ─────────────────────────────────────────────
const refreshSchema = z.object({
    refreshToken: z.string(),
});
router.post("/refresh", async (req, res) => {
    try {
        const { refreshToken } = refreshSchema.parse(req.body);
        const result = await AuthService.rotateRefreshToken(refreshToken);
        res.json(result);
    }
    catch (err) {
        res.status(401).json({ error: err.message });
    }
});
// ─── Current User ──────────────────────────────────────────────
router.get("/me", requireAuth, async (req, res) => {
    try {
        const user = await prisma.user.findUniqueOrThrow({
            where: { id: req.user.sub },
            select: {
                id: true,
                email: true,
                phone: true,
                displayName: true,
                avatarUrl: true,
                smartAccount: true,
                kycStatus: true,
                emailVerified: true,
                createdAt: true,
            },
        });
        res.json(user);
    }
    catch (err) {
        res.status(404).json({ error: "User not found" });
    }
});
// ─── Logout ────────────────────────────────────────────────────
router.post("/logout", requireAuth, async (req, res) => {
    await AuthService.revokeAllTokens(req.user.sub);
    res.json({ message: "Logged out" });
});
export default router;
//# sourceMappingURL=auth.routes.js.map