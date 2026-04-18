# Tranzo Backend Implementation Guide

## New Endpoints for Production Auth Flow

This guide covers implementing the backend changes needed to support the production-ready auth flow with profile management, avatar uploads, and passkey/WebAuthn authentication.

---

## 1. Database Schema Updates

### Step 1: Update User Model in Prisma

Add these fields to your `prisma/schema.prisma`:

```prisma
model User {
  id                    String      @id @default(cuid())
  email                 String      @unique
  accessToken           String?
  refreshToken          String?
  walletAddress         String?     @unique

  // NEW: Profile fields
  firstName             String?
  lastName              String?
  phone                 String?
  preferredLanguage     String      @default("en")  // en, es, fr, pt
  avatarUrl             String?
  biometricEnabled      Boolean     @default(false)

  // Passkey/WebAuthn credentials
  credentials           Credential[]

  // Existing fields
  createdAt             DateTime    @default(now())
  updatedAt             DateTime    @updatedAt

  @@index([email])
  @@index([walletAddress])
}

// NEW: WebAuthn credential storage
model Credential {
  id                    String      @id @default(cuid())
  userId                String
  user                  User        @relation(fields: [userId], references: [id], onDelete: Cascade)

  // WebAuthn credential data
  credentialId          Bytes       @unique
  credentialPublicKey   Bytes
  credentialName        String      // Name user gave this credential (e.g., "iPhone 15")
  counter               Int         @default(0)  // For cloning detection

  // Metadata
  createdAt             DateTime    @default(now())
  lastUsedAt            DateTime?

  @@index([userId])
}
```

### Step 2: Create and Run Migration

```bash
npx prisma migrate dev --name add_profile_fields_and_credentials
npx prisma generate
```

---

## 2. Install Required Dependencies

Add these packages to `package.json`:

```bash
npm install @simplewebauthn/server multer dotenv zod uuid
npm install --save-dev @types/multer @types/express
```

**Why each package:**
- `@simplewebauthn/server` - WebAuthn registration and authentication verification
- `multer` - Handle multipart file uploads for avatars
- `zod` - Request validation (you may already have this)
- `uuid` - Generate unique IDs
- `dotenv` - Environment variable management

---

## 3. Set Environment Variables

Add to your `.env` file:

```env
# WebAuthn Configuration
RP_ID=tranzo.app
ORIGIN=https://tranzo.app

# For development/testing, you might use:
# RP_ID=localhost
# ORIGIN=http://localhost:3000

# File Upload Configuration
MAX_UPLOAD_SIZE=5242880  # 5MB in bytes
UPLOAD_DIR=./uploads/avatars
```

---

## 4. Integrate Routes into Express App

### In your main `src/index.ts` or `src/app.ts`:

```typescript
import express from 'express';
import userRoutes from './routes/user.routes';
import passkeyRoutes from './routes/passkey.routes';
import { authenticate } from './middleware/auth';

const app = express();

// Existing middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Serve uploaded files statically
app.use('/uploads', express.static(path.join(process.cwd(), 'uploads')));

// Routes
app.use('/api/auth/passkey', passkeyRoutes);  // Passkey routes (no auth required for registration/login)
app.use('/api/user', userRoutes);              // User profile routes (require auth)

// ... rest of your routes
```

---

## 5. New Endpoints Reference

### Profile Management

#### `PUT /api/user/profile`
**Authentication:** Required (JWT token)

**Request:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1 (555) 123-4567",
  "preferredLanguage": "en",
  "biometricEnabled": true
}
```

**Response:**
```json
{
  "success": true,
  "message": "Profile updated successfully",
  "user": {
    "id": "user123",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1 (555) 123-4567",
    "preferredLanguage": "en",
    "avatarUrl": null,
    "biometricEnabled": true
  }
}
```

#### `POST /api/user/avatar`
**Authentication:** Required (JWT token)
**Content-Type:** multipart/form-data

**Request:**
```
Form Data:
  avatar: <file> (image/jpeg, image/png, image/webp, max 5MB)
```

**Response:**
```json
{
  "success": true,
  "message": "Avatar uploaded successfully",
  "avatarUrl": "/uploads/avatars/user123-1713427200000.png",
  "user": {
    "id": "user123",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "avatarUrl": "/uploads/avatars/user123-1713427200000.png"
  }
}
```

#### `GET /api/user/profile`
**Authentication:** Required (JWT token)

**Response:**
```json
{
  "success": true,
  "user": {
    "id": "user123",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1 (555) 123-4567",
    "preferredLanguage": "en",
    "avatarUrl": "/uploads/avatars/user123-1713427200000.png",
    "biometricEnabled": true,
    "walletAddress": "0x1234...",
    "createdAt": "2024-04-18T12:00:00Z"
  }
}
```

### Passkey/WebAuthn Authentication

#### Step 1: `POST /api/auth/passkey/register/options`
**Authentication:** Not required
**Purpose:** Get registration challenge for WebAuthn

**Request:**
```json
{
  "email": "john@example.com",
  "credentialName": "iPhone 15 Pro"
}
```

**Response:**
```json
{
  "success": true,
  "options": {
    "challenge": "base64_encoded_challenge",
    "rp": {
      "name": "Tranzo",
      "id": "tranzo.app"
    },
    "user": {
      "id": "base64_encoded_user_id",
      "name": "john@example.com",
      "displayName": "John Doe"
    },
    "pubKeyCredParams": [...],
    "timeout": 60000,
    "attestation": "none",
    "excludeCredentials": [...]
  }
}
```

#### Step 2: `POST /api/auth/passkey/register/verify`
**Authentication:** Not required
**Purpose:** Verify WebAuthn credential registration

**Request:**
```json
{
  "email": "john@example.com",
  "credentialName": "iPhone 15 Pro",
  "credential": {
    "id": "base64_credential_id",
    "rawId": "base64_credential_id",
    "response": {
      "clientDataJSON": "base64_data",
      "attestationObject": "base64_data",
      "transports": ["internal", "hybrid"]
    },
    "type": "public-key"
  }
}
```

**Response:**
```json
{
  "success": true,
  "message": "Passkey registered successfully",
  "credentialName": "iPhone 15 Pro"
}
```

#### Step 3: `POST /api/auth/passkey/authenticate/options`
**Authentication:** Not required
**Purpose:** Get authentication challenge for WebAuthn login

**Request:**
```json
{
  "email": "john@example.com"
}
```

**Response:**
```json
{
  "success": true,
  "options": {
    "challenge": "base64_encoded_challenge",
    "timeout": 60000,
    "rpId": "tranzo.app",
    "allowCredentials": [
      {
        "type": "public-key",
        "id": "base64_credential_id",
        "transports": ["internal", "hybrid"]
      }
    ],
    "userVerification": "preferred"
  }
}
```

#### Step 4: `POST /api/auth/passkey/authenticate/verify`
**Authentication:** Not required
**Purpose:** Verify WebAuthn authentication and issue JWT tokens

**Request:**
```json
{
  "email": "john@example.com",
  "credential": {
    "id": "base64_credential_id",
    "rawId": "base64_credential_id",
    "response": {
      "clientDataJSON": "base64_data",
      "authenticatorData": "base64_data",
      "signature": "base64_data",
      "userHandle": "base64_user_id"
    },
    "type": "public-key"
  }
}
```

**Response:**
```json
{
  "success": true,
  "message": "Authentication successful",
  "accessToken": "jwt_access_token",
  "refreshToken": "jwt_refresh_token",
  "isNewUser": false,
  "user": {
    "id": "user123",
    "email": "john@example.com"
  }
}
```

### Passkey Management

#### `GET /api/auth/passkey/credentials`
**Authentication:** Required (JWT token)
**Purpose:** List all user's registered passkeys

**Response:**
```json
{
  "success": true,
  "credentials": [
    {
      "id": "cred1",
      "credentialName": "iPhone 15 Pro",
      "createdAt": "2024-04-18T10:00:00Z",
      "lastUsedAt": "2024-04-18T12:30:00Z"
    }
  ]
}
```

#### `DELETE /api/auth/passkey/credentials/:credentialId`
**Authentication:** Required (JWT token)
**Purpose:** Remove a registered passkey

**Response:**
```json
{
  "success": true,
  "message": "Passkey removed successfully"
}
```

---

## 6. Update Existing Endpoints

### Modify `POST /auth/login` (Email OTP)

Update the response to include the new profile fields:

```typescript
// After successful OTP verification
res.json({
  success: true,
  accessToken,
  refreshToken,
  isNewUser,
  user: {
    id: user.id,
    email: user.email,
    firstName: user.firstName,
    lastName: user.lastName,
    phone: user.phone,
    preferredLanguage: user.preferredLanguage,
    avatarUrl: user.avatarUrl,
    biometricEnabled: user.biometricEnabled,
    walletAddress: user.walletAddress,
  }
});
```

### Modify `POST /auth/google` (Google Login)

Similarly update the response:

```typescript
// After successful Google login
res.json({
  success: true,
  accessToken,
  refreshToken,
  isNewUser,
  user: {
    id: user.id,
    email: user.email,
    firstName: user.firstName,
    lastName: user.lastName,
    phone: user.phone,
    preferredLanguage: user.preferredLanguage,
    avatarUrl: user.avatarUrl,
    biometricEnabled: user.biometricEnabled,
    walletAddress: user.walletAddress,
  }
});
```

---

## 7. Important Notes for Production

### Session Management for WebAuthn Challenges

**Current Implementation:** Uses in-memory Maps
```typescript
const registrationChallenges = new Map<string, string>();
const authenticationChallenges = new Map<string, string>();
```

**For Production:** Replace with Redis or database storage
```typescript
// Example with Redis
import redis from 'redis';

const redisClient = redis.createClient();

// Store challenge
await redisClient.setex(`reg_challenge:${userId}`, 600, challenge);

// Retrieve challenge
const challenge = await redisClient.get(`reg_challenge:${userId}`);

// Delete challenge after verification
await redisClient.del(`reg_challenge:${userId}`);
```

### File Upload Security

1. **Validate file types** - Already implemented (JPEG, PNG, WebP only)
2. **Limit file size** - Already implemented (5MB max)
3. **Store files safely** - Use cloud storage (AWS S3, Cloudinary, etc.) in production
4. **Clean old avatars** - Already implemented (deletes old files)

### WebAuthn Best Practices

1. **Challenge expiry** - Set short TTL (10 minutes recommended)
2. **Counter checking** - Prevents cloning detection (already implemented)
3. **Attestation validation** - Currently set to 'none' for simplicity, can enhance
4. **HTTPS requirement** - WebAuthn requires secure context (HTTPS)

### Rate Limiting

Add rate limiting to prevent brute force attacks:

```typescript
import rateLimit from 'express-rate-limit';

const authLimiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 10, // Limit each IP to 10 requests per windowMs
  message: 'Too many authentication attempts, please try again later',
});

router.post('/register/options', authLimiter, ...);
router.post('/authenticate/options', authLimiter, ...);
```

---

## 8. Testing the Implementation

### Test with curl or Postman:

```bash
# 1. Register passkey - get options
curl -X POST http://localhost:3000/api/auth/passkey/register/options \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","credentialName":"Test Device"}'

# 2. Update profile
curl -X PUT http://localhost:3000/api/user/profile \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "firstName":"John",
    "lastName":"Doe",
    "phone":"+1234567890",
    "preferredLanguage":"en"
  }'

# 3. Upload avatar
curl -X POST http://localhost:3000/api/user/avatar \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "avatar=@/path/to/image.jpg"

# 4. Get profile
curl -X GET http://localhost:3000/api/user/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 9. Android Integration Checklist

- [x] Android ProfileSetupScreen collects firstName, lastName, phone, preferredLanguage
- [x] Android sends profile data to new PUT `/user/profile` endpoint
- [x] Android implements WebAuthn registration using Credential Manager API
- [x] Android sends attestation to new POST `/auth/passkey/register/verify` endpoint
- [x] Android implements WebAuthn authentication using Credential Manager API
- [x] Android sends assertion to new POST `/auth/passkey/authenticate/verify` endpoint
- [ ] Backend implements all new endpoints ← **YOU ARE HERE**
- [ ] End-to-end testing: Email OTP → Profile Setup → Wallet → Home
- [ ] End-to-end testing: Passkey → Home (returning user)
- [ ] Production deployment

---

## 10. Next Steps

1. **Copy the route files** to your backend project:
   - `src/routes/user.routes.ts`
   - `src/routes/passkey.routes.ts`

2. **Update Prisma schema** with new User and Credential models

3. **Run migration**: `npx prisma migrate dev`

4. **Install dependencies**: `npm install @simplewebauthn/server multer`

5. **Add environment variables** to `.env`

6. **Wire routes into Express app** in your main app file

7. **Test all endpoints** with curl/Postman before Android integration

8. **Update Android client** to use new endpoints (already implemented in code)

---

## Questions & Troubleshooting

### Q: WebAuthn origin mismatch error
**A:** Ensure `ORIGIN` env var matches your deployment domain exactly (protocol + domain)

### Q: File upload returns 413 (Payload Too Large)
**A:** Increase Express `bodyParser` limits:
```typescript
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ limit: '10mb', extended: true }));
```

### Q: Credential not found during auth
**A:** Ensure credentialId is properly encoded/decoded. Check that Bytes field is being compared correctly.

### Q: Tests failing on local (RP_ID issue)
**A:** Set `RP_ID=localhost` and `ORIGIN=http://localhost:3000` for local testing
