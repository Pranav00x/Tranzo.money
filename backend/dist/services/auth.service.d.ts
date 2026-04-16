import type { AuthPayload } from "../middleware/auth.js";
export declare class AuthService {
    /**
     * Send an OTP to the given email. Creates user if not exists (lazy signup).
     */
    static sendOtp(email: string): Promise<{
        success: boolean;
    }>;
    /**
     * Verify OTP and create/login user. Returns tokens.
     */
    static verifyOtp(email: string, otp: string): Promise<{
        accessToken: string;
        refreshToken: string;
        isNewUser: boolean;
    }>;
    /**
     * Verify Google ID token and create/login user.
     */
    static loginWithGoogle(idToken: string): Promise<{
        accessToken: string;
        refreshToken: string;
        isNewUser: boolean;
    }>;
    static signAccessToken(payload: AuthPayload): string;
    static verifyAccessToken(token: string): AuthPayload;
    static createRefreshToken(userId: string): Promise<string>;
    static rotateRefreshToken(oldToken: string): Promise<{
        accessToken: string;
        refreshToken: string;
    }>;
    static revokeAllTokens(userId: string): Promise<void>;
}
