/**
 * HOW TO UPDATE YOUR AUTH ROUTES
 *
 * This file shows exactly how to integrate the new EmailService
 * into your existing auth endpoints.
 */
export {};
/**
 * Add these to your backend/.env file:
 *
 * SMTP_HOST=smtp.gmail.com
 * SMTP_PORT=587
 * SMTP_USER=your-email@gmail.com
 * SMTP_PASSWORD=your-app-specific-password
 * SMTP_FROM=noreply@tranzo.money
 */
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
