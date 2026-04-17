import { ENV } from "../config/env.js";

/**
 * Send emails via Resend API (without React dependency)
 */
export class EmailService {
  private static readonly RESEND_API_URL = "https://api.resend.com/emails";

  /**
   * Send OTP via Resend API
   */
  static async sendOTP(email: string, otp: string): Promise<void> {
    try {
      const response = await fetch(this.RESEND_API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${ENV.SMTP_PASS}`,
        },
        body: JSON.stringify({
          from: ENV.EMAIL_FROM,
          to: email,
          subject: `${otp} is your Tranzo verification code`,
          html: `
            <div style="font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; max-width: 440px; margin: 0 auto; padding: 32px 24px;">
              <div style="text-align: center; margin-bottom: 32px;">
                <div style="background: #1D9E75; border-radius: 16px; width: 56px; height: 56px; display: inline-flex; align-items: center; justify-content: center;">
                  <span style="color: white; font-size: 24px; font-weight: bold;">T</span>
                </div>
              </div>
              <h2 style="color: #1A1A2E; font-size: 22px; text-align: center; margin-bottom: 8px;">
                Verify your email
              </h2>
              <p style="color: #6B7280; font-size: 15px; text-align: center; margin-bottom: 32px;">
                Enter this code in the Tranzo app to continue
              </p>
              <div style="background: #F5F7FA; border-radius: 16px; padding: 24px; text-align: center; margin-bottom: 32px;">
                <span style="font-size: 36px; font-weight: 700; letter-spacing: 8px; color: #1D9E75;">
                  ${otp}
                </span>
              </div>
              <p style="color: #9CA3AF; font-size: 13px; text-align: center;">
                This code expires in 10 minutes.<br/>
                If you didn't request this, you can safely ignore this email.
              </p>
            </div>
          `,
        }),
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(`Resend API error: ${error.message}`);
      }

      console.log(`[Email] OTP sent to ${email}`);
    } catch (error: any) {
      console.error(`[Email] Failed to send OTP to ${email}:`, error.message);
      throw error;
    }
  }

  /**
   * Send welcome email via Resend API
   */
  static async sendWelcome(email: string, name?: string): Promise<void> {
    try {
      const response = await fetch(this.RESEND_API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${ENV.SMTP_PASS}`,
        },
        body: JSON.stringify({
          from: ENV.EMAIL_FROM,
          to: email,
          subject: "Welcome to Tranzo 🎉",
          html: `
            <div style="font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; max-width: 440px; margin: 0 auto; padding: 32px 24px;">
              <h2 style="color: #1A1A2E; font-size: 22px;">
                Welcome${name ? `, ${name}` : ""}! 👋
              </h2>
              <p style="color: #6B7280; font-size: 15px; line-height: 1.6;">
                Your self-custody wallet is ready. You now have a smart account on Base
                that you fully control — no custodians, no compromises.
              </p>
              <div style="background: #F5F7FA; border-radius: 16px; padding: 20px; margin: 24px 0;">
                <p style="color: #1A1A2E; font-size: 14px; margin: 0;"><strong>Next steps:</strong></p>
                <ul style="color: #6B7280; font-size: 14px; margin: 8px 0 0; padding-left: 20px;">
                  <li>Fund your wallet with USDC</li>
                  <li>Set up biometric security</li>
                  <li>Explore Dripper salary streaming</li>
                </ul>
              </div>
              <p style="color: #9CA3AF; font-size: 13px;">
                Questions? Reach us at <a href="mailto:pranav@tranzo.money" style="color: #1D9E75;">pranav@tranzo.money</a>
              </p>
            </div>
          `,
        }),
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(`Resend API error: ${error.message}`);
      }

      console.log(`[Email] Welcome email sent to ${email}`);
    } catch (error: any) {
      console.error(`[Email] Failed to send welcome email to ${email}:`, error.message);
      throw error;
    }
  }
}
