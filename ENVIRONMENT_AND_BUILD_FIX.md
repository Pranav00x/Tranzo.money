# Android APK Build Fix & Environment Variables Setup

## Part 1: Fix APK Build Failures

### What Was Updated:
- ✅ Updated `AndroidManifest.xml` with biometric permissions
- ✅ Updated `build.gradle.kts` with WebAuthn configuration
- ✅ Added FIDO2/Passkey dependency

### Step 1: Clean Build Cache

```bash
cd C:\Users\prana\Tranzo.money\android

# Clean all build artifacts
./gradlew clean

# Or on Windows:
gradlew.bat clean
```

### Step 2: Invalidate Gradle Cache (Android Studio)

1. **File → Invalidate Caches → Invalidate and Restart**
2. Wait for Android Studio to restart
3. It will rebuild the gradle cache

### Step 3: Rebuild APK

```bash
# Debug APK (for testing)
./gradlew assembleDebug

# Release APK (for production)
./gradlew assembleRelease
```

If using Android Studio, just click **Build → Make Project**

### Common Build Errors & Fixes:

#### Error: "Cannot resolve symbol 'BiometricHelper'"
**Solution:** 
- Check that `BiometricHelper.kt` exists at `com/tranzo/app/util/BiometricHelper.kt`
- If missing, ask for the file or create a stub version

#### Error: "Cannot resolve symbol 'GoogleSignInHelper'"
**Solution:**
- Check that `GoogleSignInHelper.kt` exists at `com/tranzo/app/util/GoogleSignInHelper.kt`
- If missing, create the file with proper Google Sign-In implementation

#### Error: "Duplicate class or Manifest merger failed"
**Solution:**
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

#### Error: "com.google.android.gms:play-services-fido not found"
**Solution:**
- This is expected if you're on a fresh build
- Run: `./gradlew build --refresh-dependencies`
- Or check internet connection and try again

---

## Part 2: Environment Variables Setup

### Android (Build Time Configuration)

Environment variables for Android are now configured in **`app/build.gradle.kts`** as `buildConfigField`.

**Already Added:**
```kotlin
buildConfigField("String", "BASE_URL", "\"https://tranzomoney-production.up.railway.app\"")
buildConfigField("String", "WEBAUTHN_RP_ID", "\"tranzo.app\"")
buildConfigField("String", "WEBAUTHN_ORIGIN", "\"https://tranzo.app\"")
```

### Using Config in Android Code:

```kotlin
import com.tranzo.app.BuildConfig

// Access in your code:
val baseUrl = BuildConfig.BASE_URL           // "https://tranzomoney-production.up.railway.app"
val rpId = BuildConfig.WEBAUTHN_RP_ID        // "tranzo.app"
val origin = BuildConfig.WEBAUTHN_ORIGIN     // "https://tranzo.app"
```

### Change Configurations for Different Environments:

#### 1. For Development (Debug):

Edit `app/build.gradle.kts` debug block:

```kotlin
buildTypes {
    debug {
        buildConfigField("String", "BASE_URL", "\"http://localhost:3000\"")
        buildConfigField("String", "WEBAUTHN_RP_ID", "\"localhost\"")
        buildConfigField("String", "WEBAUTHN_ORIGIN", "\"http://localhost:3000\"")
    }
}
```

#### 2. For Production (Release):

Edit `app/build.gradle.kts` release block:

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        
        buildConfigField("String", "BASE_URL", "\"https://api.tranzo.app\"")
        buildConfigField("String", "WEBAUTHN_RP_ID", "\"tranzo.app\"")
        buildConfigField("String", "WEBAUTHN_ORIGIN", "\"https://tranzo.app\"")
    }
}
```

---

## Part 3: Backend Environment Variables

### Where to Add (Backend .env file):

Create or update `.env` file in backend root:

```bash
# C:\Users\prana\Tranzo.money\backend\.env

# ──────────────────────────────
# WebAuthn Configuration
# ──────────────────────────────
RP_ID=tranzo.app
ORIGIN=https://tranzo.app

# For local development:
# RP_ID=localhost
# ORIGIN=http://localhost:3000

# ──────────────────────────────
# File Upload Configuration
# ──────────────────────────────
MAX_UPLOAD_SIZE=5242880          # 5MB in bytes
UPLOAD_DIR=./uploads/avatars

# ──────────────────────────────
# JWT Configuration
# ──────────────────────────────
JWT_SECRET=your_secret_key_here
JWT_ACCESS_TOKEN_EXPIRY=15m      # 15 minutes
JWT_REFRESH_TOKEN_EXPIRY=30d     # 30 days

# ──────────────────────────────
# Database Configuration
# ──────────────────────────────
DATABASE_URL=postgresql://user:password@localhost:5432/tranzo

# ──────────────────────────────
# Server Configuration
# ──────────────────────────────
NODE_ENV=production              # or 'development'
PORT=3000
```

### Load Variables in Backend Code:

**In `src/index.ts` or `src/app.ts`:**

```typescript
import dotenv from 'dotenv';

// Load environment variables from .env file
dotenv.config();

const rpID = process.env.RP_ID || 'tranzo.app';
const origin = process.env.ORIGIN || 'https://tranzo.app';
const port = process.env.PORT || 3000;

console.log(`Server running at ${origin}:${port}`);
```

**In `src/routes/passkey.routes.ts`:**

```typescript
const rpID = process.env.RP_ID || 'tranzo.app';
const rpName = 'Tranzo';
const origin = process.env.ORIGIN || `https://${rpID}`;

// Use these in WebAuthn configuration...
```

---

## Part 4: Environment-Specific Setup

### Local Development Setup

#### Android (Debug Build):

1. Update `app/build.gradle.kts` for localhost:
```kotlin
debug {
    buildConfigField("String", "BASE_URL", "\"http://192.168.1.100:3000\"")  // Use your machine IP
    buildConfigField("String", "WEBAUTHN_RP_ID", "\"localhost\"")
    buildConfigField("String", "WEBAUTHN_ORIGIN", "\"http://192.168.1.100:3000\"")
}
```

2. Build debug APK:
```bash
./gradlew assembleDebug
```

#### Backend (Local):

1. Create `.env.local`:
```env
NODE_ENV=development
PORT=3000
RP_ID=localhost
ORIGIN=http://localhost:3000
DATABASE_URL=postgresql://localhost:5432/tranzo_dev
```

2. Start backend:
```bash
npm run dev
```

### Production Setup

#### Android (Release Build):

1. Update `app/build.gradle.kts`:
```kotlin
release {
    isMinifyEnabled = true
    isShrinkResources = true
    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    
    buildConfigField("String", "BASE_URL", "\"https://api.tranzo.app\"")
    buildConfigField("String", "WEBAUTHN_RP_ID", "\"tranzo.app\"")
    buildConfigField("String", "WEBAUTHN_ORIGIN", "\"https://tranzo.app\"")
}
```

2. Build release APK:
```bash
./gradlew assembleRelease
```

#### Backend (Production):

1. Create `.env.production`:
```env
NODE_ENV=production
PORT=3000
RP_ID=api.tranzo.app
ORIGIN=https://api.tranzo.app
DATABASE_URL=postgresql://<prod-user>:<prod-password>@<prod-host>:5432/tranzo
JWT_SECRET=<long-random-secret-key>
MAX_UPLOAD_SIZE=5242880
UPLOAD_DIR=/var/uploads/avatars
```

2. Deploy with environment:
```bash
NODE_ENV=production npm start
```

---

## Part 5: Quick Reference

### Android Config Locations:

| Config | Location | Type |
|--------|----------|------|
| BASE_URL | `app/build.gradle.kts` | buildConfigField |
| WEBAUTHN_RP_ID | `app/build.gradle.kts` | buildConfigField |
| WEBAUTHN_ORIGIN | `app/build.gradle.kts` | buildConfigField |
| Access in code | `BuildConfig.BASE_URL` | String constant |

### Backend Config Locations:

| Config | Location | Type |
|--------|----------|------|
| RP_ID | `.env` file | environment variable |
| ORIGIN | `.env` file | environment variable |
| JWT_SECRET | `.env` file | environment variable |
| DATABASE_URL | `.env` file | environment variable |
| Access in code | `process.env.RP_ID` | process variable |

---

## Part 6: Build Troubleshooting Checklist

- [ ] Run `./gradlew clean` before building
- [ ] Ensure you're using Java 17 or higher: `java -version`
- [ ] Invalidate Android Studio cache: **File → Invalidate Caches → Restart**
- [ ] Check that all required files exist:
  - [ ] `app/build.gradle.kts`
  - [ ] `gradle.properties`
  - [ ] `AndroidManifest.xml`
  - [ ] `BuildConfig` generation enabled in `build.gradle.kts`
- [ ] Verify `buildConfigField` entries are correctly formatted:
  ```kotlin
  buildConfigField("String", "KEY", "\"value\"")  // Note the escaped quotes
  ```
- [ ] Run full rebuild: `./gradlew build`
- [ ] If still failing, check logcat for actual error: `./gradlew build -i`

---

## Part 7: Verifying Config Values

### To Check Android Configs:

1. Build APK: `./gradlew assembleDebug`
2. Extract and check: `unzip -l app/build/outputs/apk/debug/app-debug.apk | grep BuildConfig`
3. Or add debug log:
```kotlin
import com.tranzo.app.BuildConfig

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("CONFIG", "BASE_URL: ${BuildConfig.BASE_URL}")
    Log.d("CONFIG", "WEBAUTHN_RP_ID: ${BuildConfig.WEBAUTHN_RP_ID}")
    Log.d("CONFIG", "WEBAUTHN_ORIGIN: ${BuildConfig.WEBAUTHN_ORIGIN}")
}
```

### To Check Backend Configs:

1. Start server with debug logging:
```bash
NODE_DEBUG=* npm run dev
```

2. Check env vars are loaded:
```typescript
console.log('RP_ID:', process.env.RP_ID);
console.log('ORIGIN:', process.env.ORIGIN);
```

---

## Summary

✅ **Android**: Config values are hardcoded in `build.gradle.kts` as `buildConfigField`
✅ **Backend**: Config values are loaded from `.env` file using `dotenv`
✅ **Changed Files**: 
  - `app/build.gradle.kts` - Added buildConfigFields
  - `AndroidManifest.xml` - Added FIDO2 permissions
  - Ready for `.env` setup on backend

**Next Steps:**
1. Run `./gradlew clean && ./gradlew build` to verify APK builds
2. Create `.env` file in backend root with proper values
3. Test backend endpoints locally
4. Deploy to production with prod `.env`
