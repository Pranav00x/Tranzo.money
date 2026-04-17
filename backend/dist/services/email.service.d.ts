/**
 * Send emails via Resend API (without React dependency)
 */
export declare class EmailService {
    private static readonly RESEND_API_URL;
    /**
     * Send OTP via Resend API
     */
    static sendOTP(email: string, otp: string): Promise<void>;
    /**
     * Send welcome email via Resend API
     */
    static sendWelcome(email: string, name?: string): Promise<void>;
}
