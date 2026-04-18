# Backend Implementation Checklist

## Phase 1: Database & Dependencies ✅ Ready

- [ ] **Prisma Schema Update**
  - [ ] Add `firstName`, `lastName`, `phone`, `preferredLanguage`, `avatarUrl`, `biometricEnabled` fields to User model
  - [ ] Create new Credential model for WebAuthn credentials
  - [ ] Run: `npx prisma migrate dev --name add_profile_fields_and_credentials`
  - [ ] Run: `npx prisma generate`
  - Files: `schema_updates.prisma`

- [ ] **Install Dependencies**
  ```bash
  npm install @simplewebauthn/server multer zod uuid
  npm install --save-dev @types/multer @types/express
  ```
  - [ ] Verify all packages installed: `npm list`

- [ ] **Environment Variables**
  - [ ] Add to `.env`:
    ```
    RP_ID=tranzo.app
    ORIGIN=https://tranzo.app
    MAX_UPLOAD_SIZE=5242880
    UPLOAD_DIR=./uploads/avatars
    ```

---

## Phase 2: Route Implementation ✅ Ready

- [ ] **User Profile Routes** (`src/routes/user.routes.ts`)
  - [ ] Copy file to your backend
  - [ ] Update import paths for your project structure
  - [ ] Verify `validateRequest` middleware exists or import from your utils
  - [ ] Create `uploads/avatars` directory or ensure it's writable
  - Endpoints:
    - [ ] `PUT /api/user/profile` - Update profile fields
    - [ ] `POST /api/user/avatar` - Upload avatar image
    - [ ] `GET /api/user/profile` - Get current user profile

- [ ] **Passkey/WebAuthn Routes** (`src/routes/passkey.routes.ts`)
  - [ ] Copy file to your backend
  - [ ] Update import paths for your project structure
  - [ ] **Important:** Replace `generateJWT` function with your actual JWT generation logic
  - [ ] Update `rpID` and origin handling to use env variables properly
  - [ ] Replace in-memory Maps with Redis for production (see guide)
  - Endpoints:
    - [ ] `POST /api/auth/passkey/register/options` - Get registration challenge
    - [ ] `POST /api/auth/passkey/register/verify` - Verify and save credential
    - [ ] `POST /api/auth/passkey/authenticate/options` - Get auth challenge
    - [ ] `POST /api/auth/passkey/authenticate/verify` - Verify auth & issue tokens
    - [ ] `GET /api/auth/passkey/credentials` - List user's passkeys
    - [ ] `DELETE /api/auth/passkey/credentials/:id` - Remove a passkey

---

## Phase 3: Express App Integration ✅ Ready

In your main app file (`src/index.ts` or `src/app.ts`):

- [ ] **Add Route Imports**
  ```typescript
  import userRoutes from './routes/user.routes';
  import passkeyRoutes from './routes/passkey.routes';
  ```

- [ ] **Mount Routes**
  ```typescript
  app.use('/api/auth/passkey', passkeyRoutes);
  app.use('/api/user', userRoutes);
  ```

- [ ] **Configure Static Files**
  ```typescript
  app.use('/uploads', express.static(path.join(process.cwd(), 'uploads')));
  ```

- [ ] **Test Routes**
  ```bash
  # Check if routes are mounted
  curl http://localhost:3000/api/user/profile
  # Should return 401 (Unauthorized) since no token - that's correct!
  ```

---

## Phase 4: Update Existing Auth Endpoints

Update response format for Email OTP and Google login:

- [ ] **Update `POST /auth/otp/verify`**
  - Include new fields in response:
    - `firstName`, `lastName`, `phone`, `preferredLanguage`, `avatarUrl`, `biometricEnabled`, `walletAddress`
  - Keep existing: `accessToken`, `refreshToken`, `isNewUser`

- [ ] **Update `POST /auth/google`**
  - Include same new fields in response

- [ ] **Test Updated Endpoints**
  ```bash
  curl -X POST http://localhost:3000/api/auth/otp/verify \
    -H "Content-Type: application/json" \
    -d '{"email":"test@example.com","otp":"000000"}'
  ```

---

## Phase 5: Error Handling & Validation

- [ ] **Request Validation**
  - [ ] Verify Zod schemas work with your validation middleware
  - [ ] Test invalid requests return 400 with proper error messages

- [ ] **Error Responses**
  - [ ] All endpoints return consistent error format:
    ```json
    {
      "success": false,
      "error": "Error message here"
    }
    ```

- [ ] **JWT Validation**
  - [ ] Protected routes verify `Authorization: Bearer <token>` header
  - [ ] Return 401 if token missing or invalid

---

## Phase 6: Testing & Verification

### Local Testing

- [ ] **Test Profile Update**
  ```bash
  curl -X PUT http://localhost:3000/api/user/profile \
    -H "Authorization: Bearer YOUR_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
      "firstName": "John",
      "lastName": "Doe",
      "phone": "+1234567890",
      "preferredLanguage": "en"
    }'
  ```

- [ ] **Test Avatar Upload**
  ```bash
  curl -X POST http://localhost:3000/api/user/avatar \
    -H "Authorization: Bearer YOUR_TOKEN" \
    -F "avatar=@test.jpg"
  ```

- [ ] **Test Passkey Registration Flow**
  - [ ] Call `/register/options` → Get challenge
  - [ ] Simulate WebAuthn registration locally
  - [ ] Call `/register/verify` → Save credential

- [ ] **Test Passkey Authentication Flow**
  - [ ] Call `/authenticate/options` → Get challenge
  - [ ] Simulate WebAuthn auth locally
  - [ ] Call `/authenticate/verify` → Get tokens

### Database Verification

- [ ] **Check Schema Applied**
  ```bash
  npx prisma db push
  npx prisma studio  # Visual database browser
  ```

- [ ] **Verify User Record**
  ```bash
  # In Prisma Studio, check:
  # - New fields on User model
  # - Credential model exists and relationships work
  ```

- [ ] **Test Credential Save**
  - After passkey registration, verify `Credential` record exists
  - Check `credentialId` and `credentialPublicKey` are stored as Bytes

---

## Phase 7: Production Hardening

- [ ] **Session Management**
  - [ ] Replace in-memory Maps with Redis for challenges
  - [ ] Implement 10-minute TTL for challenges
  - [ ] Handle challenge expiry gracefully

- [ ] **File Upload Security**
  - [ ] Validate MIME types ✅ (done)
  - [ ] Enforce 5MB file size limit ✅ (done)
  - [ ] Use cloud storage (S3, Cloudinary) instead of local file system
  - [ ] Add virus scanning for uploaded files

- [ ] **Rate Limiting**
  - [ ] Add rate limiter to auth endpoints
  - [ ] Implement: 10 req/min for auth, 100 req/min for general
  - [ ] Return 429 (Too Many Requests) when exceeded

- [ ] **Logging**
  - [ ] Add structured logging for all endpoints
  - [ ] Log failures with enough context for debugging
  - [ ] Never log sensitive data (passwords, tokens)

- [ ] **HTTPS & Security Headers**
  - [ ] WebAuthn requires HTTPS (secure context)
  - [ ] Add security headers (HSTS, CSP, etc.)
  - [ ] Ensure `ORIGIN` environment variable matches deployment domain

- [ ] **Database Indexes**
  - [ ] Verify indexes on:
    - [ ] `User.email` ✅ (defined in schema)
    - [ ] `User.walletAddress` ✅ (defined in schema)
    - [ ] `Credential.userId` ✅ (defined in schema)
    - [ ] `Credential.credentialId` ✅ (defined in schema)

---

## Phase 8: Android Integration Testing

After backend is live, test with Android app:

- [ ] **New User Flow**
  - [ ] Send OTP → Verify OTP
  - [ ] Verify response includes `isNewUser: true`
  - [ ] Android navigates to ProfileSetupScreen
  - [ ] User enters firstName, lastName, phone, language
  - [ ] Android calls `PUT /api/user/profile`
  - [ ] Verify data saved in database
  - [ ] Android navigates to WalletCreation
  - [ ] Verify profile displays in Home screen

- [ ] **Returning User Flow**
  - [ ] Send OTP → Verify OTP
  - [ ] Verify response includes `isNewUser: false`
  - [ ] Android skips ProfileSetupScreen
  - [ ] Android navigates to WalletCreation
  - [ ] Verify previously saved profile displays

- [ ] **Avatar Upload**
  - [ ] Upload avatar from Android
  - [ ] Verify file saved in `uploads/avatars`
  - [ ] Verify `avatarUrl` returned and stored
  - [ ] Verify URL accessible and image displays

- [ ] **Passkey Registration**
  - [ ] Android calls `/register/options`
  - [ ] WebAuthn credential registration completes
  - [ ] Android calls `/register/verify`
  - [ ] Verify `Credential` record created
  - [ ] Verify `biometricEnabled: true` set on user

- [ ] **Passkey Login**
  - [ ] Android calls `/authenticate/options`
  - [ ] WebAuthn credential authentication completes
  - [ ] Android calls `/authenticate/verify`
  - [ ] Verify `accessToken` and `refreshToken` issued
  - [ ] Verify user logged in without email/password

---

## Quick Start Commands

```bash
# Install everything
npm install @simplewebauthn/server multer zod uuid
npm install --save-dev @types/multer @types/express

# Setup database
npx prisma migrate dev --name add_profile_fields_and_credentials
npx prisma generate

# Start server
npm run dev

# View database (visual browser)
npx prisma studio
```

---

## Files Checklist

| File | Location | Status |
|------|----------|--------|
| `schema_updates.prisma` | Reference only | ✅ Ready |
| `user.routes.ts` | `src/routes/` | ✅ Ready |
| `passkey.routes.ts` | `src/routes/` | ✅ Ready |
| `BACKEND_IMPLEMENTATION_GUIDE.md` | Project root | ✅ Ready |
| Updated Prisma schema | `prisma/schema.prisma` | Needs your action |
| Updated main app file | `src/index.ts` or `src/app.ts` | Needs your action |

---

## Critical Notes

⚠️ **Before Production:**
1. Replace in-memory challenges with Redis
2. Move to cloud storage for avatars
3. Enable HTTPS/TLS
4. Set proper `ORIGIN` and `RP_ID` environment variables
5. Implement rate limiting
6. Add monitoring and alerting

⚠️ **JWT Generation:**
Look for your existing JWT utility and update the `generateJWT` function in `passkey.routes.ts` to use your actual implementation.

⚠️ **Android Coordination:**
Ensure Android app is updated before deploying these endpoints to production. Coordinate the release!

---

## Success Criteria

✅ All tests in Phase 6 pass
✅ Android can complete new user → profile setup → wallet flow
✅ Android can passkey register and authenticate
✅ Profile data persists and displays correctly
✅ Avatar uploads and displays
✅ No 500 errors in production logs
✅ Load testing shows no performance degradation
