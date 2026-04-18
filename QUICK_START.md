# 🚀 Tranzo Build & Deploy Quick Start

## Status: Ready to Build

✅ Android code updated
✅ Backend routes created  
✅ Env variables configured
✅ Permissions added
✅ Dependencies specified

---

## 🔨 Step 1: Build Android APK (5 min)

### Clean & Build
```bash
cd "C:\Users\prana\Tranzo.money\android"

# Clean cache
./gradlew clean

# Build debug APK (for testing)
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk

# OR build release APK (for production)
./gradlew assembleRelease  
# Output: app/build/outputs/apk/release/app-release.apk
```

### If Build Fails:
```bash
# Invalidate Android Studio cache first:
# File → Invalidate Caches → Invalidate and Restart

# Then clean again:
./gradlew clean --refresh-dependencies
./gradlew build -i  # -i shows detailed info
```

### Android Configuration Already Set:
- ✅ `WEBAUTHN_RP_ID=tranzo.app` (in build.gradle.kts)
- ✅ `WEBAUTHN_ORIGIN=https://tranzo.app` (in build.gradle.kts)
- ✅ All permissions added to AndroidManifest.xml
- ✅ All dependencies added to build.gradle.kts

**To change for local testing:**
Edit `app/build.gradle.kts` debug section:
```kotlin
debug {
    buildConfigField("String", "WEBAUTHN_RP_ID", "\"localhost\"")
    buildConfigField("String", "WEBAUTHN_ORIGIN", "\"http://192.168.1.100:3000\"")
}
```

---

## 🔧 Step 2: Setup Backend (.env) (3 min)

### Create Backend Environment File

```bash
cd "C:\Users\prana\Tranzo.money\backend"

# Copy example to actual .env
cp .env.example .env

# Or on Windows:
copy .env.example .env
```

### Edit `.env` with These Values:

```env
# Server
PORT=3000
NODE_ENV=development

# Database (keep existing or update)
DATABASE_URL=postgresql://postgres:postgres@localhost:5432/tranzo

# JWT (keep existing or generate new)
JWT_SECRET=your-jwt-secret-change-this
JWT_REFRESH_SECRET=your-refresh-secret-change-this

# ✨ NEW - WebAuthn Configuration
RP_ID=tranzo.app
ORIGIN=https://tranzo.app

# For local development, use:
# RP_ID=localhost
# ORIGIN=http://localhost:3000

# ✨ NEW - File Uploads
MAX_UPLOAD_SIZE=5242880
UPLOAD_DIR=./uploads/avatars

# Keep existing configs below...
```

### ⚠️ IMPORTANT: Don't commit .env!

Add to `.gitignore`:
```
.env
.env.local
.env.*.local
uploads/
```

---

## 📦 Step 3: Install Backend Dependencies (2 min)

```bash
cd "C:\Users\prana\Tranzo.money\backend"

npm install @simplewebauthn/server multer zod uuid
npm install --save-dev @types/multer
```

Verify installation:
```bash
npm list @simplewebauthn/server multer zod uuid
```

Should show versions for each package.

---

## 📂 Step 4: Copy Route Files to Backend (1 min)

Copy these files to your backend:

```bash
# From the delivery location to your backend src/routes/
cp user.routes.ts src/routes/
cp passkey.routes.ts src/routes/

# Create uploads directory
mkdir -p uploads/avatars
```

---

## 🔌 Step 5: Wire Routes into Express App (2 min)

Edit your `src/index.ts` or `src/app.ts`:

```typescript
import userRoutes from './routes/user.routes';
import passkeyRoutes from './routes/passkey.routes';

// ... your other middleware ...

// Add these lines:
app.use('/api/auth/passkey', passkeyRoutes);  // No auth required
app.use('/api/user', userRoutes);              // Auth required

// ... rest of your routes ...
```

---

## 🗄️ Step 6: Update Prisma Database (3 min)

Update `prisma/schema.prisma` with new User fields:

```prisma
model User {
  id                    String      @id @default(cuid())
  email                 String      @unique
  // ... existing fields ...

  // ADD THESE:
  firstName             String?
  lastName              String?
  phone                 String?
  preferredLanguage     String      @default("en")
  avatarUrl             String?
  biometricEnabled      Boolean     @default(false)
  
  credentials           Credential[]

  @@index([email])
  @@index([walletAddress])
}

// ADD NEW MODEL:
model Credential {
  id                    String      @id @default(cuid())
  userId                String
  user                  User        @relation(fields: [userId], references: [id], onDelete: Cascade)
  credentialId          Bytes       @unique
  credentialPublicKey   Bytes
  credentialName        String
  counter               Int         @default(0)
  createdAt             DateTime    @default(now())
  lastUsedAt            DateTime?
  
  @@index([userId])
}
```

Then run migration:
```bash
npx prisma migrate dev --name add_profile_and_credentials
npx prisma generate
```

---

## ✅ Step 7: Start Backend & Test (2 min)

```bash
cd "C:\Users\prana\Tranzo.money\backend"

npm run dev
# Should output: Server listening on http://localhost:3000
```

### Quick Test:
```bash
# Get profile (should fail with 401 - no token)
curl http://localhost:3000/api/user/profile

# Should return:
# {"success":false,"error":"No authorization header"}

# That's correct! It means the route is working.
```

---

## 📱 Step 8: Update Existing Auth Endpoints

Edit your OTP and Google auth endpoints to return new fields:

**In `POST /auth/otp/verify`:**
```typescript
res.json({
  success: true,
  accessToken,
  refreshToken,
  isNewUser,
  user: {
    id: user.id,
    email: user.email,
    firstName: user.firstName,        // NEW
    lastName: user.lastName,          // NEW
    phone: user.phone,                // NEW
    preferredLanguage: user.preferredLanguage,  // NEW
    avatarUrl: user.avatarUrl,        // NEW
    biometricEnabled: user.biometricEnabled,    // NEW
  }
});
```

---

## 🔑 Step 9: Generate JWT Secret (For Production)

```bash
# Generate strong secret for production
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"

# Output something like:
# a1b2c3d4e5f6... (64 characters)

# Use this in production .env:
JWT_SECRET=a1b2c3d4e5f6...
```

---

## 🧪 Step 10: Test Full Flow (5 min)

### Local Testing (All on localhost):

**Terminal 1 - Start Backend:**
```bash
cd backend
npm run dev
# Listens on http://localhost:3000
```

**Terminal 2 - Test OTP Endpoint:**
```bash
curl -X POST http://localhost:3000/api/auth/otp/send \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.in"}'

# Response:
# {"success":true,"message":"OTP sent"}
```

**Terminal 3 - Test Profile Update:**
```bash
# First get a token from OTP verify
# Then:
curl -X PUT http://localhost:3000/api/user/profile \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName":"John",
    "lastName":"Doe",
    "phone":"+1234567890",
    "preferredLanguage":"en"
  }'

# Response:
# {"success":true,"message":"Profile updated successfully","user":{...}}
```

**Terminal 3 - Test Avatar Upload:**
```bash
curl -X POST http://localhost:3000/api/user/avatar \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "avatar=@/path/to/image.jpg"

# Response:
# {"success":true,"message":"Avatar uploaded successfully","avatarUrl":"..."}
```

---

## 📋 Deployment Checklist

### Before Going to Production:

- [ ] Android APK built successfully
- [ ] Backend `.env` file created (not committed)
- [ ] All dependencies installed (`npm list`)
- [ ] Database migration completed (`npx prisma migrate status`)
- [ ] Test endpoints working locally
- [ ] JWT_SECRET changed to strong value
- [ ] RP_ID and ORIGIN set to correct domain
- [ ] HTTPS enabled on backend
- [ ] Rate limiting configured
- [ ] Logging configured
- [ ] Backups enabled

### Deployment:

```bash
# 1. Backend - Update environment
export NODE_ENV=production
export RP_ID=api.tranzo.app
export ORIGIN=https://api.tranzo.app

# 2. Start backend
npm run build
npm start

# 3. Deploy Android APK to app stores
# Build release APK
./gradlew assembleRelease

# 4. Upload to Google Play Store
# Use Android Studio's Build → Generate Signed APK
```

---

## 🐛 Troubleshooting

### Android Build Fails
```bash
./gradlew clean
./gradlew build -i  # Shows detailed errors
# Check: Java 17 installed, Android SDK updated
```

### Backend Won't Start
```bash
# Check .env file exists
# Check DATABASE_URL is correct
# Check PORT is not in use
# Check Node version: node -v (v16+ required)
```

### Prisma Migration Fails
```bash
npx prisma migrate reset  # ⚠️ Deletes all data!
npx prisma migrate dev --name add_profile_and_credentials
```

### WebAuthn RP_ID Error
```
Error: RP_ID mismatch
Solution: Ensure RP_ID in .env matches ORIGIN domain
# For localhost: RP_ID=localhost, ORIGIN=http://localhost:3000
# For production: RP_ID=tranzo.app, ORIGIN=https://tranzo.app
```

---

## 📞 Support

| Issue | Solution |
|-------|----------|
| APK won't build | Run `./gradlew clean` and check Java version |
| Backend won't start | Check `.env` file and DATABASE_URL |
| Tests fail | Check RP_ID and ORIGIN match in .env |
| Avatar upload fails | Check `uploads/avatars` directory exists |
| Auth endpoints fail | Check JWT_SECRET is set correctly |

---

## 🎉 What's Next?

✅ Android APK ready to install/publish
✅ Backend endpoints ready to deploy
✅ Database schema updated
✅ Environment variables configured

### To Go Live:
1. Deploy backend to production server
2. Update production `.env` with real domain
3. Build and publish Android APK to Play Store
4. Test end-to-end flow in production

---

## 📚 Files Reference

| File | Purpose | Location |
|------|---------|----------|
| `user.routes.ts` | Profile & avatar endpoints | `backend/src/routes/` |
| `passkey.routes.ts` | WebAuthn endpoints | `backend/src/routes/` |
| `.env` | Environment variables | `backend/` |
| `build.gradle.kts` | Android build config | `android/app/` |
| `AndroidManifest.xml` | Android permissions | `android/app/src/main/` |
| `schema.prisma` | Database schema | `backend/prisma/` |

---

**Total Setup Time: ~20 minutes**

🚀 Ready to deploy!
