import express from 'express';
import { z } from 'zod';
import { prisma } from '../db/prisma';
import { authenticate } from '../middleware/auth';
import { validateRequest } from '../middleware/validation';
import {
  generateRegistrationOptions,
  verifyRegistrationResponse,
  generateAuthenticationOptions,
  verifyAuthenticationResponse,
  type PublicKeyCredentialCreationOptionsJSON,
  type PublicKeyCredentialRequestOptionsJSON,
} from '@simplewebauthn/server';
import { isoUint8Array } from '@simplewebauthn/server/helpers/iso';

const router = express.Router();

// Get your site's domain from env
const rpID = process.env.RP_ID || 'tranzo.app';
const rpName = 'Tranzo';
const origin = process.env.ORIGIN || `https://${rpID}`;

// Session storage for registration/authentication challenges (use Redis in production)
const registrationChallenges = new Map<string, string>();
const authenticationChallenges = new Map<string, string>();

// ────────────────────────────────────────────────────────
// POST /auth/passkey/register/options
// Called by client to get registration challenge
// ────────────────────────────────────────────────────────

const registerOptionsSchema = z.object({
  email: z.string().email(),
  credentialName: z.string().min(1).max(100), // e.g., "iPhone 15"
});

router.post(
  '/register/options',
  validateRequest(registerOptionsSchema),
  async (req, res) => {
    try {
      const { email, credentialName } = req.body;

      // Check if user exists
      const user = await prisma.user.findUnique({
        where: { email },
        select: {
          id: true,
          firstName: true,
          lastName: true,
        },
      });

      if (!user) {
        return res.status(404).json({
          success: false,
          error: 'User not found',
        });
      }

      // Get existing credentials for this user (if any)
      const existingCredentials = await prisma.credential.findMany({
        where: { userId: user.id },
        select: { credentialId: true },
      });

      const excludeCredentials = existingCredentials.map((cred) => ({
        id: cred.credentialId,
        type: 'public-key' as const,
      }));

      // Generate registration options
      const options = generateRegistrationOptions({
        rpID,
        rpName,
        userID: user.id,
        userName: email,
        userDisplayName: `${user.firstName || ''} ${user.lastName || ''}`.trim() || email,
        attestationType: 'none',
        excludeCredentials,
      });

      // Store challenge in session (use Redis in production)
      registrationChallenges.set(user.id, options.challenge);

      res.json({
        success: true,
        options,
      });
    } catch (error: any) {
      console.error('Registration options error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to generate registration options',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// POST /auth/passkey/register/verify
// Called by client to verify registration and save credential
// ────────────────────────────────────────────────────────

const registerVerifySchema = z.object({
  email: z.string().email(),
  credentialName: z.string().min(1).max(100),
  credential: z.object({
    id: z.string(),
    rawId: z.string(),
    response: z.object({
      clientDataJSON: z.string(),
      attestationObject: z.string(),
      transports: z.array(z.string()).optional(),
    }),
    type: z.literal('public-key'),
  }),
});

router.post(
  '/register/verify',
  validateRequest(registerVerifySchema),
  async (req, res) => {
    try {
      const { email, credentialName, credential } = req.body;

      // Find user
      const user = await prisma.user.findUnique({
        where: { email },
        select: { id: true },
      });

      if (!user) {
        return res.status(404).json({
          success: false,
          error: 'User not found',
        });
      }

      // Get stored challenge
      const storedChallenge = registrationChallenges.get(user.id);
      if (!storedChallenge) {
        return res.status(400).json({
          success: false,
          error: 'No registration challenge found. Start registration again.',
        });
      }

      // Verify the registration response
      let verification;
      try {
        verification = await verifyRegistrationResponse({
          credential,
          expectedChallenge: storedChallenge,
          expectedOrigin: origin,
          expectedRPID: rpID,
        });
      } catch (error: any) {
        return res.status(400).json({
          success: false,
          error: `Registration verification failed: ${error.message}`,
        });
      }

      if (!verification.verified || !verification.registrationInfo) {
        return res.status(400).json({
          success: false,
          error: 'Registration verification failed',
        });
      }

      // Save credential to database
      const credentialData = verification.registrationInfo;
      await prisma.credential.create({
        data: {
          userId: user.id,
          credentialId: Buffer.from(credentialData.credentialID),
          credentialPublicKey: Buffer.from(credentialData.credentialPublicKey),
          credentialName,
          counter: credentialData.counter,
        },
      });

      // Clean up challenge
      registrationChallenges.delete(user.id);

      // Update biometricEnabled flag
      await prisma.user.update({
        where: { id: user.id },
        data: { biometricEnabled: true },
      });

      res.json({
        success: true,
        message: 'Passkey registered successfully',
        credentialName,
      });
    } catch (error: any) {
      console.error('Registration verification error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to verify registration',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// POST /auth/passkey/authenticate/options
// Called by client to get authentication challenge
// ────────────────────────────────────────────────────────

const authOptionsSchema = z.object({
  email: z.string().email(),
});

router.post(
  '/authenticate/options',
  validateRequest(authOptionsSchema),
  async (req, res) => {
    try {
      const { email } = req.body;

      // Check if user exists and has credentials
      const user = await prisma.user.findUnique({
        where: { email },
        select: {
          id: true,
          credentials: {
            select: { credentialId: true },
          },
        },
      });

      if (!user || user.credentials.length === 0) {
        return res.status(404).json({
          success: false,
          error: 'No passkeys registered for this user',
        });
      }

      // Generate authentication options
      const options = generateAuthenticationOptions({
        rpID,
        allowCredentials: user.credentials.map((cred) => ({
          id: cred.credentialId,
          type: 'public-key' as const,
        })),
      });

      // Store challenge in session
      authenticationChallenges.set(user.id, options.challenge);

      res.json({
        success: true,
        options,
      });
    } catch (error: any) {
      console.error('Authentication options error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to generate authentication options',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// POST /auth/passkey/authenticate/verify
// Called by client to verify authentication and issue tokens
// ────────────────────────────────────────────────────────

const authVerifySchema = z.object({
  email: z.string().email(),
  credential: z.object({
    id: z.string(),
    rawId: z.string(),
    response: z.object({
      clientDataJSON: z.string(),
      authenticatorData: z.string(),
      signature: z.string(),
      userHandle: z.string().optional(),
    }),
    type: z.literal('public-key'),
  }),
});

router.post(
  '/authenticate/verify',
  validateRequest(authVerifySchema),
  async (req, res) => {
    try {
      const { email, credential } = req.body;

      // Find user
      const user = await prisma.user.findUnique({
        where: { email },
        select: {
          id: true,
          credentials: true,
        },
      });

      if (!user) {
        return res.status(404).json({
          success: false,
          error: 'User not found',
        });
      }

      // Get stored challenge
      const storedChallenge = authenticationChallenges.get(user.id);
      if (!storedChallenge) {
        return res.status(400).json({
          success: false,
          error: 'No authentication challenge found. Start authentication again.',
        });
      }

      // Find the credential that was used
      const credentialIdBuffer = Buffer.from(credential.rawId, 'base64');
      const dbCredential = user.credentials.find(
        (cred) => cred.credentialId.equals(credentialIdBuffer)
      );

      if (!dbCredential) {
        return res.status(400).json({
          success: false,
          error: 'Credential not found',
        });
      }

      // Verify the authentication response
      let verification;
      try {
        verification = await verifyAuthenticationResponse({
          credential,
          expectedChallenge: storedChallenge,
          expectedOrigin: origin,
          expectedRPID: rpID,
          authenticator: {
            credentialID: dbCredential.credentialId,
            credentialPublicKey: dbCredential.credentialPublicKey,
            counter: dbCredential.counter,
            transports: [], // Add if you stored transports
          },
        });
      } catch (error: any) {
        return res.status(400).json({
          success: false,
          error: `Authentication verification failed: ${error.message}`,
        });
      }

      if (!verification.verified) {
        return res.status(400).json({
          success: false,
          error: 'Authentication verification failed',
        });
      }

      // Update counter to prevent cloning
      await prisma.credential.update({
        where: { id: dbCredential.id },
        data: {
          counter: verification.authenticationInfo.newCounter,
          lastUsedAt: new Date(),
        },
      });

      // Clean up challenge
      authenticationChallenges.delete(user.id);

      // Generate JWT tokens (use your existing token generation logic)
      const { accessToken, refreshToken } = generateJWT(user.id);

      // Save refresh token
      await prisma.user.update({
        where: { id: user.id },
        data: {
          accessToken,
          refreshToken,
        },
      });

      // Check if new user
      const isNewUser = !user.credentials[0]; // Simplification

      res.json({
        success: true,
        message: 'Authentication successful',
        accessToken,
        refreshToken,
        isNewUser,
        user: {
          id: user.id,
          email: user.email || '',
        },
      });
    } catch (error: any) {
      console.error('Authentication verification error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to verify authentication',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// GET /auth/passkey/credentials - List user's passkeys
// ────────────────────────────────────────────────────────

router.get(
  '/credentials',
  authenticate,
  async (req, res) => {
    try {
      const userId = (req as any).userId;

      const credentials = await prisma.credential.findMany({
        where: { userId },
        select: {
          id: true,
          credentialName: true,
          createdAt: true,
          lastUsedAt: true,
        },
      });

      res.json({
        success: true,
        credentials,
      });
    } catch (error: any) {
      console.error('List credentials error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to list credentials',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// DELETE /auth/passkey/credentials/:credentialId
// Remove a passkey
// ────────────────────────────────────────────────────────

router.delete(
  '/credentials/:credentialId',
  authenticate,
  async (req, res) => {
    try {
      const userId = (req as any).userId;
      const { credentialId } = req.params;

      // Verify ownership
      const credential = await prisma.credential.findFirst({
        where: {
          id: credentialId,
          userId,
        },
      });

      if (!credential) {
        return res.status(404).json({
          success: false,
          error: 'Credential not found',
        });
      }

      await prisma.credential.delete({
        where: { id: credentialId },
      });

      res.json({
        success: true,
        message: 'Passkey removed successfully',
      });
    } catch (error: any) {
      console.error('Delete credential error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to delete credential',
      });
    }
  }
);

// Helper function - use your existing JWT generation logic
function generateJWT(userId: string) {
  // Import and use your actual JWT generation logic
  // This is a placeholder
  return {
    accessToken: 'placeholder_access_token',
    refreshToken: 'placeholder_refresh_token',
  };
}

export default router;
