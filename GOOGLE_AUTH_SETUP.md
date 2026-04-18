# Google OAuth Setup Guide for Tranzo Android App

## Overview
The Tranzo Android app now supports Google Sign-In using the Credential Manager API (modern, secure approach). This guide walks through setting up Google OAuth credentials.

## Prerequisites
- Google Cloud Console access
- A Google Cloud Project (create one if needed)

## Step 1: Create OAuth 2.0 Credentials

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Select your project (or create a new one)
3. Navigate to **APIs & Services** > **Credentials**
4. Click **Create Credentials** > **OAuth Client ID**
5. Choose **Web application**
6. Add the following URIs:
   - **JavaScript origins**: 
     - `https://localhost`
   - **Authorized redirect URIs**: 
     - `https://localhost:3000/callback`
     - Your actual backend domain (e.g., `https://your-backend.com`)

7. Click **Create** and copy the **Client ID**

## Step 2: Configure Client ID in Android App

1. Open `android/app/src/main/java/com/tranzo/app/util/GoogleSignInHelper.kt`
2. Find the line:
   ```kotlin
   private const val GOOGLE_CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com"
   ```
3. Replace `YOUR_GOOGLE_CLIENT_ID` with your actual Web Client ID from Step 1

Example:
```kotlin
private const val GOOGLE_CLIENT_ID = "123456789-abc123xyz.apps.googleusercontent.com"
```

## Step 3: Enable Required APIs

1. In Google Cloud Console, go to **APIs & Services** > **Library**
2. Search for and enable:
   - **Google Identity** (for Sign-In)
   - **Google+ API** (if using older integration)

## Step 4: Configure Android Package Info (Optional - for enhanced security)

If using Google Play Services directly, add your app's SHA-1 fingerprint to OAuth credentials:

1. Get your app's SHA-1 fingerprint:
   ```bash
   ./gradlew signingReport
   ```

2. In Google Cloud Console > Credentials > OAuth 2.0 Client IDs
3. Add the SHA-1 fingerprint to Android package info (if needed)

## Step 5: Test Google Sign-In

1. Build and run the app:
   ```bash
   cd android
   ./gradlew installDebug
   ```

2. Navigate to the Welcome/Sign-up screen
3. Tap the **Google** auth method button
4. Complete the Google sign-in flow
5. You should be redirected to the profile setup screen

## How It Works

### Frontend Flow (Android)
1. User taps "Google" button on WelcomeScreen
2. `GoogleSignInHelper.signIn()` opens Credential Manager
3. User authenticates with Google
4. App receives Google ID token
5. ID token is sent to `AuthViewModel.loginWithGoogle(idToken)`

### Backend Flow
1. Backend receives `GoogleLoginRequest` with ID token
2. Backend verifies ID token with Google's servers
3. Backend creates or retrieves user account
4. Backend returns access & refresh tokens to app
5. Tokens are stored in SharedPreferences for future requests

## Security Notes

- **ID Tokens** are short-lived (~1 hour) and verified server-side
- **Access Tokens** from your backend are used for authenticated API calls
- Never expose your Google Client Secret in the app
- The Client ID in the app is safe to expose (it's public)

## Troubleshooting

### "One Tap Sign-Up" doesn't appear
- Ensure Credential Manager is updated
- Check that Client ID is correctly configured
- Verify Google Play Services is installed on device

### Sign-in succeeds but backend returns error
- Check backend logs for ID token verification errors
- Ensure backend can reach Google's token validation endpoint
- Verify the token hasn't expired

### Build error: "GoogleIdTokenCredential not found"
- Make sure the credential manager dependency is installed:
  ```bash
  cd android
  ./gradlew build
  ```

## Environment Configurations

### Development
- Use a development Google Cloud Project
- Test with test emails if available
- Use emulator or debug device

### Production
- Create a separate production Google Cloud Project
- Configure production OAuth credentials with actual domain
- Use signed Android build credentials

## Related Files
- `android/app/src/main/java/com/tranzo/app/util/GoogleSignInHelper.kt` - Sign-in implementation
- `android/app/src/main/java/com/tranzo/app/ui/auth/WelcomeScreen.kt` - UI integration
- `android/app/src/main/java/com/tranzo/app/ui/auth/AuthViewModel.kt` - API call handler
- `android/gradle/libs.versions.toml` - Dependencies

## References
- [Google Credential Manager Docs](https://developer.android.com/reference/androidx/credentials/CredentialManager)
- [Google Identity Docs](https://developers.google.com/identity)
- [Android OAuth Implementation](https://developer.android.com/training/sign-in)
