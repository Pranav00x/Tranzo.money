# 🔧 .env File Setup - Email Configuration

## 📍 File Location

```
C:\Users\prana\Tranzo.money\backend\.env
```

---

## ✅ Step 1: Create `.env` File (If It Doesn't Exist)

Navigate to backend folder:
```bash
cd C:\Users\prana\Tranzo.money\backend
```

Copy example file:
```bash
# On Windows:
copy .env.example .env

# On Mac/Linux:
cp .env.example .env
```

---

## 📝 Step 2: Update Email Configuration in `.env`

Open the file with any text editor:
- VSCode: `code .env`
- Notepad: `notepad .env`
- Or any text editor

Find the section that says:
```
# ─── Email (SMTP for OTP) ─────────────────────────────
```

---

## 🎯 Option 1: Use Gmail (Easiest ⭐ Recommended)

### A. Setup Gmail App Password

1. Go to: https://myaccount.google.com/security
2. Scroll down to "Your devices"
3. Click on "App passwords" (or "2-Step Verification" if not set up)
4. Select "Mail" and "Windows Computer"
5. Google will generate a **16-character password**
6. Copy that password

### B. Update `.env` File

Replace this section:
```env
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=your-email@gmail.com
SMTP_PASSWORD=your-app-specific-password
SMTP_FROM=noreply@tranzo.money
```

With your actual values:
```env
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=pranav@flowstable.in
SMTP_PASSWORD=xxxx xxxx xxxx xxxx
SMTP_FROM=noreply@tranzo.money
```

**Example:**
```env
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=pranav@flowstable.in
SMTP_PASSWORD=abcd efgh ijkl mnop
SMTP_FROM=noreply@tranzo.money
```

---

## 🎯 Option 2: Use Resend (Alternative)

### A. Get Resend API Key

1. Go to: https://resend.com
2. Sign up / Login
3. Go to API Keys
4. Copy your API key (starts with `re_`)

### B. Update `.env` File

Replace or uncomment this section:
```env
# SMTP_HOST=smtp.resend.com
# SMTP_PORT=465
# SMTP_USER=resend
# SMTP_PASSWORD=re_xxxxxxxxxxxxx
# SMTP_FROM=onboarding@resend.dev
```

With your actual Resend API key:
```env
SMTP_HOST=smtp.resend.com
SMTP_PORT=465
SMTP_USER=resend
SMTP_PASSWORD=re_abcd1234efgh5678ijkl9012mnop
SMTP_FROM=onboarding@resend.dev
```

---

## 📋 Complete `.env` File Example

Here's what your complete `.env` file should look like:

```env
# ─── Server ───────────────────────────────────────────
PORT=3000
NODE_ENV=development

# ─── Database ─────────────────────────────────────────
DATABASE_URL=postgresql://postgres:postgres@localhost:5432/tranzo

# ─── Redis ────────────────────────────────────────────
REDIS_URL=redis://localhost:6379

# ─── JWT ──────────────────────────────────────────────
JWT_SECRET=your-jwt-secret-change-this
JWT_REFRESH_SECRET=your-refresh-secret-change-this

# ─── WebAuthn / Passkey ────────────────────────────────
RP_ID=tranzo.app
ORIGIN=https://tranzo.app

# ─── File Uploads ──────────────────────────────────────
MAX_UPLOAD_SIZE=5242880
UPLOAD_DIR=./uploads/avatars

# ─── Email (SMTP for OTP) ─────────────────────────────
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=pranav@flowstable.in
SMTP_PASSWORD=abcd efgh ijkl mnop
SMTP_FROM=noreply@tranzo.money

# ─── WebAuthn ──────────────────────────────────────────
RP_ID=tranzo.app
ORIGIN=https://tranzo.app

# For Local Development:
# RP_ID=localhost
# ORIGIN=http://localhost:3000

# ─── ZeroDev (Smart Accounts) ──────────────────────────
ZERODEV_PROJECT_ID=your-zerodev-project-id
ZERODEV_RPC_URL=https://rpc.zerodev.app/api/v1/rpc/{projectId}

# ─── Google OAuth ─────────────────────────────────────
GOOGLE_CLIENT_ID=...apps.googleusercontent.com

# ─── Chains ───────────────────────────────────────────
POLYGON_RPC_URL=https://polygon-mainnet.g.alchemy.com/v2/...
BASE_RPC_URL=https://base-mainnet.g.alchemy.com/v2/...
DEFAULT_CHAIN_ID=137
```

---

## 🔐 Security Notes

### ⚠️ Important

1. **NEVER commit `.env` to git**
   - Add to `.gitignore`:
   ```
   .env
   .env.local
   .env.*.local
   ```

2. **Keep passwords secret**
   - Don't share `.env` file with anyone
   - Don't post it online
   - Don't commit it to GitHub

3. **Use app-specific passwords**
   - Gmail: Never use your main password
   - Always use "App Password" feature
   - Can revoke anytime from Google Account

---

## ✅ Verification Checklist

After updating `.env`:

- [ ] File location: `C:\Users\prana\Tranzo.money\backend\.env`
- [ ] `SMTP_HOST` is set (smtp.gmail.com or smtp.resend.com)
- [ ] `SMTP_PORT` is set (587 for Gmail, 465 for Resend)
- [ ] `SMTP_USER` is set (your email or API user)
- [ ] `SMTP_PASSWORD` is set (app password or API key)
- [ ] `SMTP_FROM` is set (noreply@tranzo.money)
- [ ] No extra spaces or quotes around values
- [ ] File is saved

---

## 🧪 Test Email Setup

After updating `.env`, test it:

```bash
cd C:\Users\prana\Tranzo.money\backend

# Start backend
npm run dev

# In another terminal, test email:
npm test -- --testNamePattern="email"
```

Or test manually by calling OTP endpoint:

```bash
curl -X POST http://localhost:3000/api/auth/otp/send \
  -H "Content-Type: application/json" \
  -d '{"email":"test@gmail.com"}'
```

Check your email inbox - you should receive OTP email! 📧

---

## 🐛 Troubleshooting

### "Email not sending"

**Check 1:** Are all env variables set?
```bash
echo $SMTP_HOST
echo $SMTP_USER
echo $SMTP_PASSWORD
```

**Check 2:** Is Gmail app password correct?
- Go back to Google Account
- Generate NEW app password
- Update `.env`
- Restart backend

**Check 3:** Is SMTP port correct?
- Gmail: `587` (TLS)
- Resend: `465` (SSL)
- Other: Check with provider

### "Authentication failed"

**Solution:** Password is wrong
1. Delete old app password in Google Account
2. Generate new one
3. Copy exactly (spaces matter: `xxxx xxxx xxxx xxxx`)
4. Update `.env`
5. Restart backend

### "Connection timeout"

**Solution:** Wrong SMTP host or port
- Gmail: `smtp.gmail.com:587`
- Resend: `smtp.resend.com:465`
- Check spelling!

### "Email goes to spam"

**Normal for new domains**
- This is expected
- Mark as "Not Spam" in Gmail
- Later setup DKIM/SPF records (ask DevOps)

---

## 📍 Quick Reference

### Gmail Setup
```
SMTP_HOST = smtp.gmail.com
SMTP_PORT = 587
SMTP_USER = your-email@gmail.com
SMTP_PASSWORD = [16-char app password from Google]
```

### Resend Setup
```
SMTP_HOST = smtp.resend.com
SMTP_PORT = 465
SMTP_USER = resend
SMTP_PASSWORD = re_[your API key]
```

### Support Email
```
SMTP_FROM = noreply@tranzo.money
```

---

## 🎯 What Gets Sent When Everything Works

When user requests OTP:
1. Backend generates 6-digit code: `123456`
2. Backend calls `emailService.sendOtpEmail(email, otp)`
3. EmailService uses SMTP config from `.env`
4. Email sent with minimal template
5. User receives email in inbox with:
   - Large OTP code
   - 10-minute expiry notice
   - Support email: `hi@tranzo.money`
   - Website: `tranzo.app`

---

## 📂 File Path Reference

```
C:\Users\prana\Tranzo.money\
└── backend\
    ├── .env                    ← YOU EDIT THIS FILE
    ├── .env.example            ← Template (don't edit)
    ├── src\
    │   ├── services\
    │   │   └── EmailService.ts ← Email sending logic
    │   ├── templates\
    │   │   └── otp-email.html  ← Email template
    │   └── routes\
    │       └── auth.routes.ts   ← OTP endpoint (update this)
    └── package.json            ← Has nodemailer
```

---

## 🚀 Final Steps

1. ✅ Copy `.env.example` to `.env`
2. ✅ Update email variables in `.env`
3. ✅ Copy `EmailService.ts` to `src/services/`
4. ✅ Copy `otp-email.html` to `src/templates/`
5. ✅ Update `auth.routes.ts` to use EmailService
6. ✅ Test: `npm run dev` then call OTP endpoint
7. ✅ Check email inbox for OTP

---

**Status:** ✅ Ready to send emails!

When you restart the backend and call the OTP endpoint, users will receive clean, minimal OTP emails. 🎉
