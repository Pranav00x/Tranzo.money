import nodemailer from 'nodemailer';
import fs from 'fs';
import path from 'path';
/**
 * EmailService - Handles all email communications
 * Sends OTP emails with minimal, clean design
 */
class EmailService {
    transporter;
    otpTemplate;
    constructor() {
        // Setup email transporter (using Resend/SMTP)
        this.transporter = nodemailer.createTransport({
            host: process.env.SMTP_HOST || 'smtp.gmail.com',
            port: parseInt(process.env.SMTP_PORT || '587'),
            secure: false,
            auth: {
                user: process.env.SMTP_USER,
                pass: process.env.SMTP_PASSWORD,
            },
        });
        // Load OTP template
        this.otpTemplate = this.loadTemplate('otp-email.html');
    }
    /**
     * Load email template from file
     */
    loadTemplate(filename) {
        try {
            const templatePath = path.join(process.cwd(), 'src/templates', filename);
            return fs.readFileSync(templatePath, 'utf-8');
        }
        catch (error) {
            console.error(`Failed to load template ${filename}:`, error);
            // Fallback to plain text if template fails to load
            return this.getFallbackOtpTemplate();
        }
    }
    /**
     * Send OTP email
     */
    async sendOtpEmail(email, otp, userName) {
        try {
            // Replace OTP code in template
            const htmlContent = this.otpTemplate.replace('{{OTP_CODE}}', otp);
            // Send email
            const info = await this.transporter.sendMail({
                from: process.env.SMTP_FROM || 'noreply@tranzo.money',
                to: email,
                subject: `Your Tranzo OTP: ${otp.substring(0, 3)}***`,
                html: htmlContent,
                text: this.getPlainTextOtp(otp, userName),
            });
            console.log(`OTP email sent to ${email}:`, info.messageId);
            return {
                success: true,
                message: 'OTP sent successfully',
            };
        }
        catch (error) {
            console.error('Failed to send OTP email:', error);
            return {
                success: false,
                message: error.message || 'Failed to send OTP email',
            };
        }
    }
    /**
     * Send password reset email
     */
    async sendPasswordResetEmail(email, resetLink) {
        try {
            const htmlContent = `
<!DOCTYPE html>
<html>
<head>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Arial, sans-serif; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 40px 20px; background: white; }
        .header { text-align: center; padding-bottom: 30px; border-bottom: 1px solid #eee; }
        .logo { font-size: 24px; font-weight: bold; color: #050505; }
        .content { padding: 20px 0; }
        .button { display: inline-block; background: #050505; color: white; padding: 12px 30px; border-radius: 6px; text-decoration: none; margin: 20px 0; }
        .footer { margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; font-size: 13px; color: #666; text-align: center; }
        .link { color: #4A9DFF; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="logo">Tranzo</h1>
        </div>
        <div class="content">
            <p>We received a request to reset your password.</p>
            <p>Click the button below to reset your password:</p>
            <center>
                <a href="${resetLink}" class="button">Reset Password</a>
            </center>
            <p style="color: #999; font-size: 13px;">Or copy this link: <a href="${resetLink}" class="link">${resetLink}</a></p>
            <p style="color: #666; margin-top: 30px;">This link expires in 24 hours. If you didn't request this, ignore this email.</p>
        </div>
        <div class="footer">
            <p>Questions? Email us at <a href="mailto:hi@tranzo.money" class="link">hi@tranzo.money</a></p>
            <p style="color: #999;">© 2024 Tranzo. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
            `;
            await this.transporter.sendMail({
                from: process.env.SMTP_FROM || 'noreply@tranzo.money',
                to: email,
                subject: 'Reset Your Tranzo Password',
                html: htmlContent,
            });
            return {
                success: true,
                message: 'Password reset email sent',
            };
        }
        catch (error) {
            console.error('Failed to send password reset email:', error);
            return {
                success: false,
                message: error.message || 'Failed to send password reset email',
            };
        }
    }
    /**
     * Send welcome email to new users
     */
    async sendWelcomeEmail(email, firstName) {
        try {
            const userName = firstName ? `${firstName},` : '';
            const htmlContent = `
<!DOCTYPE html>
<html>
<head>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Arial, sans-serif; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 40px 20px; background: white; }
        .header { text-align: center; padding-bottom: 30px; border-bottom: 1px solid #eee; }
        .logo { font-size: 24px; font-weight: bold; color: #050505; }
        .content { padding: 20px 0; }
        .footer { margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; font-size: 13px; color: #666; text-align: center; }
        .link { color: #4A9DFF; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="logo">Tranzo</h1>
        </div>
        <div class="content">
            <p>Welcome ${userName}</p>
            <p>Your Tranzo account has been created successfully!</p>
            <p style="margin-top: 20px;">You now have access to:</p>
            <ul style="color: #666;">
                <li>Secure crypto wallet with smart account abstraction</li>
                <li>Tranzo debit card for spending crypto</li>
                <li>Fast and low-cost transactions</li>
                <li>Complete financial control</li>
            </ul>
            <p style="margin-top: 20px;">Get started by setting up your wallet and exploring Tranzo.</p>
        </div>
        <div class="footer">
            <p><a href="https://tranzo.app" class="link">Visit Tranzo.app</a></p>
            <p>Questions? Email us at <a href="mailto:hi@tranzo.money" class="link">hi@tranzo.money</a></p>
            <p style="color: #999;">© 2024 Tranzo. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
            `;
            await this.transporter.sendMail({
                from: process.env.SMTP_FROM || 'noreply@tranzo.money',
                to: email,
                subject: 'Welcome to Tranzo! 🎉',
                html: htmlContent,
            });
            return {
                success: true,
                message: 'Welcome email sent',
            };
        }
        catch (error) {
            console.error('Failed to send welcome email:', error);
            return {
                success: false,
                message: error.message || 'Failed to send welcome email',
            };
        }
    }
    /**
     * Plain text fallback for OTP
     */
    getPlainTextOtp(otp, userName) {
        return `
Tranzo OTP Verification

${userName ? `Hi ${userName},` : 'Hi,'}

Your One-Time Password (OTP) for Tranzo is:

${otp}

This code expires in 10 minutes.

If you didn't request this OTP, please ignore this email.

---

Questions? Email us at hi@tranzo.money
Visit: https://tranzo.app

© 2024 Tranzo. All rights reserved.
        `;
    }
    /**
     * Fallback OTP template (plain HTML)
     */
    getFallbackOtpTemplate() {
        return `
<!DOCTYPE html>
<html>
<head>
    <style>
        body { font-family: system-ui, -apple-system, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 40px 20px; }
        .container { background: white; padding: 40px; border-radius: 8px; }
        .logo { font-size: 20px; font-weight: bold; margin-bottom: 30px; }
        .otp-box { background: #f5f5f5; padding: 30px; text-align: center; border-radius: 6px; margin: 30px 0; }
        .otp-code { font-size: 28px; font-weight: bold; letter-spacing: 2px; font-family: monospace; }
        .footer { margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; font-size: 12px; color: #666; text-align: center; }
        a { color: #4A9DFF; }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">Tranzo</div>
        <p>Your One-Time Password (OTP) for Tranzo:</p>
        <div class="otp-box">
            <div class="otp-code">{{OTP_CODE}}</div>
            <p style="font-size: 12px; color: #999; margin: 15px 0 0 0;">Expires in 10 minutes</p>
        </div>
        <p style="color: #666;">If you didn't request this, ignore this email.</p>
        <div class="footer">
            <p>Questions? Email <a href="mailto:hi@tranzo.money">hi@tranzo.money</a></p>
            <p>© 2024 Tranzo. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
        `;
    }
    /**
     * Verify email transporter connection
     */
    async verifyConnection() {
        try {
            await this.transporter.verify();
            console.log('Email service connected successfully');
            return true;
        }
        catch (error) {
            console.error('Email service connection failed:', error);
            return false;
        }
    }
}
// Export singleton instance
export const emailService = new EmailService();
export default EmailService;
//# sourceMappingURL=EmailService.js.map