/**
 * HOW TO UPDATE YOUR AUTH ROUTES
 *
 * This file shows exactly how to integrate the new EmailService
 * into your existing auth endpoints.
 */
// ════════════════════════════════════════════════════════════════
// STEP 1: Add EmailService import at the top of your auth.routes.ts
// ════════════════════════════════════════════════════════════════
import { emailService } from '../services/EmailService';
// ════════════════════════════════════════════════════════════════
// STEP 2: Update your OTP sending endpoint
// ════════════════════════════════════════════════════════════════
// Find your existing POST /auth/otp/send endpoint and update it like this:
router.post('/otp/send', validateRequest(sendOtpSchema), async (req, res) => {
    try {
        const { email } = req.body;
        // Generate OTP (6 digits)
        const otp = Math.floor(100000 + Math.random() * 900000).toString();
        // Store OTP in database or cache with 10-minute expiry
        // Example: await redisClient.setex(`otp:${email}`, 600, otp);
        // OR: await db.otp.create({ email, code: otp, expiresAt: new Date(Date.now() + 10 * 60 * 1000) });
        // ✨ NEW: Send email with minimal, clean template
        const emailResult = await emailService.sendOtpEmail(email, otp);
        if (!emailResult.success) {
            return res.status(500).json({
                success: false,
                error: 'Failed to send OTP email. Please try again.',
            });
        }
        res.json({
            success: true,
            message: 'OTP sent to your email address',
            // Don't expose OTP in response (security)
        });
    }
    catch (error) {
        console.error('OTP send error:', error);
        res.status(500).json({
            success: false,
            error: error.message || 'Failed to send OTP',
        });
    }
});
// ════════════════════════════════════════════════════════════════
// STEP 3: (Optional) Add welcome email after account creation
// ════════════════════════════════════════════════════════════════
// After successful OTP verification and account creation, send welcome email:
const userData = { email, firstName, lastName };
// ... create user in database ...
// Send welcome email to new user
await emailService.sendWelcomeEmail(email, firstName);
// ════════════════════════════════════════════════════════════════
// STEP 4: Update your environment variables (.env)
// ════════════════════════════════════════════════════════════════
/**
 * Add these to your backend/.env file:
 *
 * SMTP_HOST=smtp.gmail.com
 * SMTP_PORT=587
 * SMTP_USER=your-email@gmail.com
 * SMTP_PASSWORD=your-app-specific-password
 * SMTP_FROM=noreply@tranzo.money
 */
// ════════════════════════════════════════════════════════════════
// WHAT THE EMAIL LOOKS LIKE
// ════════════════════════════════════════════════════════════════
/**
 * To: user@example.com
 * Subject: Your Tranzo OTP: 123***
 *
 * ┌─────────────────────────────┐
 * │       Tranzo                │  ← Logo/Brand
 * │                             │
 * │ Hi,                         │
 * │                             │
 * │ Your One-Time Password      │
 * │ for Tranzo is:              │
 * │                             │
 * │  ┌──────────────────┐      │
 * │  │    123456        │      │  ← OTP Code (Large, clear)
 * │  │                  │      │
 * │  │ Expires in       │      │
 * │  │ 10 minutes       │      │
 * │  └──────────────────┘      │
 * │                             │
 * │ If you didn't request       │
 * │ this, ignore this email.    │
 * │                             │
 * ├─────────────────────────────┤
 * │                             │
 * │ Tranzo — Secure Crypto      │
 * │ Card & Smart Wallet         │
 * │                             │
 * │ Visit tranzo.app            │
 * │ hi@tranzo.money             │
 * │                             │
 * │ © 2024 Tranzo              │
 * └─────────────────────────────┘
 */
// ════════════════════════════════════════════════════════════════
// THAT'S IT!
// ════════════════════════════════════════════════════════════════
/**
 * Summary:
 * 1. Import EmailService at top of auth.routes.ts
 * 2. In OTP send endpoint, call: emailService.sendOtpEmail(email, otp)
 * 3. Update .env with SMTP credentials
 * 4. Optional: send welcome email after signup
 *
 * The email is:
 * ✅ Minimal - Clean text only
 * ✅ Professional - No logos/images
 * ✅ Clear - OTP code is big and obvious
 * ✅ Safe - Shows support email (hi@tranzo.money)
 * ✅ Branded - Website link (tranzo.app)
 *
 * Done!
 */
//# sourceMappingURL=auth.routes.updated.js.map