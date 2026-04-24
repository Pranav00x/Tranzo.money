import { ENV } from "../config/env.js";
/**
 * Send emails via Resend API (without React dependency)
 */
export class EmailService {
    static RESEND_API_URL = "https://api.resend.com/emails";
    /**
     * Send OTP via Resend API - Minimal design (text only, no logos)
     */
    static async sendOTP(email, otp) {
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
                    subject: `Your Tranzo OTP: ${otp.substring(0, 3)}***`,
                    html: `
<!DOCTYPE html>
<html>
<head>
    <style>
        body { font-family: system-ui, -apple-system, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 40px 20px; }
        .container { background: white; padding: 40px; border-radius: 8px; }
        .logo { font-size: 20px; font-weight: bold; margin-bottom: 30px; }
        .otp-box { background: #f5f5f5; padding: 30px; text-align: center; border-radius: 6px; margin: 30px 0; }
        .otp-code { font-size: 32px; font-weight: bold; letter-spacing: 2px; font-family: monospace; }
        .footer { margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; font-size: 12px; color: #666; text-align: center; }
        a { color: #4A9DFF; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">Tranzo</div>
        <p>Your One-Time Password (OTP) for Tranzo:</p>
        <div class="otp-box">
            <div class="otp-code">${otp}</div>
            <p style="font-size: 12px; color: #999; margin: 15px 0 0 0;">Expires in 10 minutes</p>
        </div>
        <p style="color: #666;">If you didn't request this, ignore this email.</p>
        <div class="footer">
            <p>Questions? Email <a href="mailto:hi@tranzo.money">hi@tranzo.money</a></p>
            <p><a href="https://tranzo.app">Visit tranzo.app</a></p>
            <p>© 2024 Tranzo. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
          `,
                    text: `Tranzo OTP\n\nYour One-Time Password (OTP) for Tranzo is:\n\n${otp}\n\nThis code expires in 10 minutes.\n\nIf you didn't request this, please ignore this email.\n\n---\n\nQuestions? Email hi@tranzo.money\nVisit: tranzo.app\n\n© 2024 Tranzo. All rights reserved.`,
                }),
            });
            if (!response.ok) {
                const error = await response.json();
                throw new Error(`Resend API error: ${error?.message || 'Unknown error'}`);
            }
            console.log(`[Email] OTP sent to ${email}`);
        }
        catch (error) {
            console.error(`[Email] Failed to send OTP to ${email}:`, error?.message || error);
            throw error;
        }
    }
    /**
     * Send welcome email via Resend API - Minimal design
     */
    static async sendWelcome(email, name) {
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
                    subject: `Welcome to Tranzo${name ? `, ${name}` : ""}! 🎉`,
                    html: `
<!DOCTYPE html>
<html>
<head>
    <style>
        body { font-family: system-ui, -apple-system, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 40px 20px; }
        .container { background: white; padding: 40px; border-radius: 8px; }
        .logo { font-size: 20px; font-weight: bold; margin-bottom: 30px; }
        .footer { margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; font-size: 12px; color: #666; text-align: center; }
        a { color: #4A9DFF; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">Tranzo</div>
        <p>Welcome${name ? ` ${name}` : ""}! 👋</p>
        <p>Your Tranzo account has been created successfully!</p>
        <p style="margin-top: 20px;">You now have access to:</p>
        <ul style="color: #666;">
            <li>Secure crypto wallet with smart account abstraction</li>
            <li>Tranzo debit card for spending crypto</li>
            <li>Fast and low-cost transactions</li>
            <li>Complete financial control</li>
        </ul>
        <p style="margin-top: 20px;">Get started by setting up your wallet and exploring Tranzo.</p>
        <div class="footer">
            <p><a href="https://tranzo.app">Visit Tranzo.app</a></p>
            <p>Questions? Email <a href="mailto:hi@tranzo.money">hi@tranzo.money</a></p>
            <p>© 2024 Tranzo. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
          `,
                    text: `Welcome${name ? ` ${name}` : ""}!\n\nYour Tranzo account has been created successfully!\n\nYou now have access to:\n- Secure crypto wallet with smart account abstraction\n- Tranzo debit card for spending crypto\n- Fast and low-cost transactions\n- Complete financial control\n\nGet started by setting up your wallet and exploring Tranzo.\n\n---\n\nVisit: tranzo.app\nQuestions? Email hi@tranzo.money\n\n© 2024 Tranzo. All rights reserved.`,
                }),
            });
            if (!response.ok) {
                const error = await response.json();
                throw new Error(`Resend API error: ${error?.message || 'Unknown error'}`);
            }
            console.log(`[Email] Welcome email sent to ${email}`);
        }
        catch (error) {
            console.error(`[Email] Failed to send welcome email to ${email}:`, error?.message || error);
            throw error;
        }
    }
}
//# sourceMappingURL=email.service.js.map