export declare class EmailService {
    /**
     * Send a 6-digit OTP to the user's email.
     */
    static sendOTP(email: string, otp: string): Promise<void>;
    /**
     * Send a welcome email after account creation.
     */
    static sendWelcome(email: string, name?: string): Promise<void>;
}
