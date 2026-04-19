/**
 * EmailService - Handles all email communications
 * Sends OTP emails with minimal, clean design
 */
declare class EmailService {
    private transporter;
    private otpTemplate;
    constructor();
    /**
     * Load email template from file
     */
    private loadTemplate;
    /**
     * Send OTP email
     */
    sendOtpEmail(email: string, otp: string, userName?: string): Promise<{
        success: boolean;
        message: string;
    }>;
    /**
     * Send password reset email
     */
    sendPasswordResetEmail(email: string, resetLink: string): Promise<{
        success: boolean;
        message: string;
    }>;
    /**
     * Send welcome email to new users
     */
    sendWelcomeEmail(email: string, firstName?: string): Promise<{
        success: boolean;
        message: string;
    }>;
    /**
     * Plain text fallback for OTP
     */
    private getPlainTextOtp;
    /**
     * Fallback OTP template (plain HTML)
     */
    private getFallbackOtpTemplate;
    /**
     * Verify email transporter connection
     */
    verifyConnection(): Promise<boolean>;
}
export declare const emailService: EmailService;
export default EmailService;
