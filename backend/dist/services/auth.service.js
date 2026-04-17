import jwt from "jsonwebtoken";
import crypto from "crypto";
import { OAuth2Client } from "google-auth-library";
import { ENV } from "../config/env.js";
import prisma from "./prisma.service.js";
import { EmailService } from "./email.service.js";
import { SmartAccountService } from "./smart-account.service.js";
import { generatePrivateKey } from "viem/accounts";
const ACCESS_TOKEN_EXPIRY = "15m";
const REFRESH_TOKEN_EXPIRY_DAYS = 30;
const OTP_EXPIRY_MINUTES = 10;
// scrypt params for password-less — we use OTP/social primarily
const SCRYPT_KEYLEN = 64;
const SCRYPT_COST = 16384;
const googleClient = new OAuth2Client();
export class AuthService {
    // ─── Email + OTP Flow ────────────────────────────────────────
    /**
     * Send an OTP to the given email. Creates user if not exists (lazy signup).
     */
    static async sendOtp(email) {
        const normalizedEmail = email.toLowerCase().trim();
        if (normalizedEmail === "test@test.in") {
            console.log(`[Auth] OTP Bypass for test account: 000000`);
            return { success: true };
        }
        // Generate 6-digit OTP (000000-999999)
        const otp = Math.floor(Math.random() * 1000000).toString().padStart(6, "0");
        const tokenHash = crypto.createHash("sha256").update(otp).digest("hex");
        const expiresAt = new Date(Date.now() + 10 * 60_000);
        console.log(`[Auth] Generated OTP for ${normalizedEmail}: ${otp} (expires at ${expiresAt.toISOString()})`);
        // Save OTP immediately
        await prisma.otpToken.create({
            data: {
                target: normalizedEmail,
                tokenHash,
                type: "EMAIL_VERIFICATION",
                expiresAt,
            },
        });
        console.log(`[Auth] OTP saved to database for ${normalizedEmail}`);
        // Send email asynchronously (don't block the response)
        EmailService.sendOTP(email, otp).catch((err) => {
            console.error(`[Auth] Failed to send OTP to ${email}:`, err.message);
        });
        return { success: true };
    }
    /**
     * Verify OTP and create/login user. Returns tokens.
     */
    static async verifyOtp(email, otp) {
        const normalizedEmail = email.toLowerCase().trim();
        // Bypass for test account
        if (normalizedEmail === "test@test.in" && otp === "000000") {
            console.log(`[Auth] OTP Verified via Bypas for test account`);
        }
        else {
            const tokenHash = crypto.createHash("sha256").update(otp).digest("hex");
            console.log(`[Auth] Looking for OTP hash: ${tokenHash} for email: ${normalizedEmail}`);
            const record = await prisma.otpToken.findFirst({
                where: {
                    target: normalizedEmail,
                    tokenHash,
                    type: "EMAIL_VERIFICATION",
                    used: false,
                    expiresAt: { gt: new Date() },
                },
            });
            if (!record) {
                console.error(`[Auth] OTP not found or expired for ${normalizedEmail}`);
                throw new Error("Invalid or expired OTP");
            }
            console.log(`[Auth] OTP found and valid for ${normalizedEmail}`);
            // Mark OTP as used
            await prisma.otpToken.update({
                where: { id: record.id },
                data: { used: true },
            });
        }
        // Find or create user
        let user = await prisma.user.findUnique({
            where: { email: normalizedEmail },
        });
        let isNewUser = false;
        if (!user) {
            // Create smart account via ZeroDev
            const privateKey = generatePrivateKey();
            const { address: smartAccountAddress } = await SmartAccountService.createAccount(privateKey);
            user = await prisma.user.create({
                data: {
                    email: normalizedEmail,
                    emailVerified: true,
                    smartAccount: smartAccountAddress,
                    signerPrivateKey: privateKey,
                },
            });
            isNewUser = true;
        }
        else {
            // Mark email as verified
            if (!user.emailVerified) {
                await prisma.user.update({
                    where: { id: user.id },
                    data: { emailVerified: true },
                });
            }
        }
        const accessToken = this.signAccessToken({
            sub: user.id,
            smartAccount: user.smartAccount,
            chainId: ENV.DEFAULT_CHAIN_ID,
        });
        const refreshToken = await this.createRefreshToken(user.id);
        return { accessToken, refreshToken, isNewUser };
    }
    // ─── Google OAuth ────────────────────────────────────────────
    /**
     * Verify Google ID token and create/login user.
     */
    static async loginWithGoogle(idToken) {
        if (!ENV.GOOGLE_CLIENT_ID) {
            throw new Error("Google OAuth not configured");
        }
        const ticket = await googleClient.verifyIdToken({
            idToken,
            audience: ENV.GOOGLE_CLIENT_ID,
        });
        const payload = ticket.getPayload();
        if (!payload?.email || !payload.sub) {
            throw new Error("Invalid Google token");
        }
        const email = payload.email.toLowerCase().trim();
        const googleId = payload.sub;
        // Check for existing user by email or Google ID
        let user = await prisma.user.findFirst({
            where: {
                OR: [
                    { email },
                    { socialAccounts: { some: { provider: "GOOGLE", providerId: googleId } } },
                ],
            },
            include: { socialAccounts: true },
        });
        let isNewUser = false;
        if (!user) {
            // New user — create ZeroDev smart account
            const privateKey = generatePrivateKey();
            const { address: smartAccountAddress } = await SmartAccountService.createAccount(privateKey);
            user = await prisma.user.create({
                data: {
                    email,
                    emailVerified: true,
                    displayName: payload.name,
                    avatarUrl: payload.picture,
                    smartAccount: smartAccountAddress,
                    signerPrivateKey: privateKey,
                    socialAccounts: {
                        create: { provider: "GOOGLE", providerId: googleId },
                    },
                },
                include: { socialAccounts: true },
            });
            isNewUser = true;
        }
        else {
            // Link Google if not already linked
            const hasGoogle = user.socialAccounts?.some((sa) => sa.provider === "GOOGLE");
            if (!hasGoogle) {
                await prisma.socialAccount.create({
                    data: { userId: user.id, provider: "GOOGLE", providerId: googleId },
                });
            }
        }
        const accessToken = this.signAccessToken({
            sub: user.id,
            smartAccount: user.smartAccount,
            chainId: ENV.DEFAULT_CHAIN_ID,
        });
        const refreshToken = await this.createRefreshToken(user.id);
        return { accessToken, refreshToken, isNewUser };
    }
    // ─── JWT ─────────────────────────────────────────────────────
    static signAccessToken(payload) {
        return jwt.sign(payload, ENV.JWT_SECRET, {
            expiresIn: ACCESS_TOKEN_EXPIRY,
        });
    }
    static verifyAccessToken(token) {
        return jwt.verify(token, ENV.JWT_SECRET);
    }
    // ─── Refresh Tokens ──────────────────────────────────────────
    static async createRefreshToken(userId) {
        const token = crypto.randomBytes(32).toString("hex");
        const family = crypto.randomUUID();
        const expiresAt = new Date(Date.now() + REFRESH_TOKEN_EXPIRY_DAYS * 24 * 60 * 60_000);
        await prisma.refreshToken.create({
            data: { token, userId, family, expiresAt },
        });
        return token;
    }
    static async rotateRefreshToken(oldToken) {
        const existing = await prisma.refreshToken.findUnique({
            where: { token: oldToken },
            include: { user: true },
        });
        if (!existing) {
            throw new Error("Refresh token not found");
        }
        // Detect reuse — revoke entire family
        if (existing.revoked) {
            await prisma.refreshToken.updateMany({
                where: { family: existing.family },
                data: { revoked: true },
            });
            throw new Error("Token reuse detected — session revoked");
        }
        if (existing.expiresAt < new Date()) {
            throw new Error("Refresh token expired");
        }
        // Revoke old token
        await prisma.refreshToken.update({
            where: { id: existing.id },
            data: { revoked: true },
        });
        // Issue new token in same family
        const newToken = crypto.randomBytes(32).toString("hex");
        const expiresAt = new Date(Date.now() + REFRESH_TOKEN_EXPIRY_DAYS * 24 * 60 * 60_000);
        await prisma.refreshToken.create({
            data: {
                token: newToken,
                userId: existing.userId,
                family: existing.family,
                expiresAt,
            },
        });
        const accessToken = this.signAccessToken({
            sub: existing.userId,
            smartAccount: existing.user.smartAccount,
            chainId: ENV.DEFAULT_CHAIN_ID,
        });
        return { accessToken, refreshToken: newToken };
    }
    static async revokeAllTokens(userId) {
        await prisma.refreshToken.updateMany({
            where: { userId, revoked: false },
            data: { revoked: true },
        });
    }
}
//# sourceMappingURL=auth.service.js.map