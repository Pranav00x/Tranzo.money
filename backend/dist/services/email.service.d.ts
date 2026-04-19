/**
 * Send emails via Resend API (without React dependency)
 */
export declare class EmailService {
    private static readonly RESEND_API_URL;
    /**
     * Send OTP via Resend API - Minimal design (text only, no logos)
     */
    static sendOTP(email: string, otp: string): Promise<void>;
    /**
     * Send welcome email via Resend API - Minimal design
     */
    static sendWelcome(email: string, name?: string): Promise<void>;
}
