/**
 * Example Express App Setup
 *
 * This shows how to integrate the new user routes and passkey routes
 * into your existing Express application.
 *
 * Update this based on your actual project structure.
 */

import express, { Express, Request, Response, NextFunction } from 'express';
import path from 'path';
import dotenv from 'dotenv';

// Load environment variables
dotenv.config();

// Import middleware
import { authenticate } from './middleware/auth'; // Your existing auth middleware
import { validateRequest } from './middleware/validation'; // Your existing validation

// Import new route modules
import userRoutes from './routes/user.routes';
import passkeyRoutes from './routes/passkey.routes';

// Import existing route modules (example names - adjust to yours)
import authRoutes from './routes/auth.routes'; // Your existing auth routes

const app: Express = express();
const PORT = process.env.PORT || 3000;

// ─────────────────────────────────────────────────────────────────
// MIDDLEWARE SETUP
// ─────────────────────────────────────────────────────────────────

// Body parsing middleware
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ limit: '10mb', extended: true }));

// CORS middleware (if needed)
import cors from 'cors';
app.use(cors({
  origin: process.env.ORIGIN || 'https://tranzo.app',
  credentials: true,
}));

// Security headers
import helmet from 'helmet';
app.use(helmet());

// Request logging (example)
app.use((req: Request, res: Response, next: NextFunction) => {
  console.log(`[${new Date().toISOString()}] ${req.method} ${req.path}`);
  next();
});

// ─────────────────────────────────────────────────────────────────
// STATIC FILE SERVING
// ─────────────────────────────────────────────────────────────────

// Serve uploaded files (avatars, etc.)
app.use('/uploads', express.static(path.join(process.cwd(), 'uploads')));

// ─────────────────────────────────────────────────────────────────
// ROUTES SETUP
// ─────────────────────────────────────────────────────────────────

// Health check
app.get('/health', (req: Request, res: Response) => {
  res.json({ status: 'ok', timestamp: new Date().toISOString() });
});

// ─────────────────────────────────────────────────────────────────
// AUTHENTICATION ROUTES (existing - keep these)
// ─────────────────────────────────────────────────────────────────

app.use('/api/auth', authRoutes);

// ─────────────────────────────────────────────────────────────────
// NEW PASSKEY/WEBAUTHN ROUTES (add these)
// ─────────────────────────────────────────────────────────────────

// Note: Passkey routes are NOT protected (no auth required)
// Users can register/login without existing tokens
app.use('/api/auth/passkey', passkeyRoutes);

// ─────────────────────────────────────────────────────────────────
// NEW USER PROFILE ROUTES (add these)
// ─────────────────────────────────────────────────────────────────

// Note: User profile routes ARE protected (auth required)
// Users must be authenticated to view/update their profile
app.use('/api/user', userRoutes);

// ─────────────────────────────────────────────────────────────────
// ERROR HANDLING
// ─────────────────────────────────────────────────────────────────

// 404 handler
app.use((req: Request, res: Response) => {
  res.status(404).json({
    success: false,
    error: `Route not found: ${req.method} ${req.path}`,
  });
});

// Global error handler
app.use((err: any, req: Request, res: Response, next: NextFunction) => {
  console.error('Error:', err);

  // Handle multer errors specifically
  if (err.code === 'LIMIT_FILE_SIZE') {
    return res.status(413).json({
      success: false,
      error: 'File too large',
    });
  }

  if (err.code === 'LIMIT_FILE_COUNT') {
    return res.status(413).json({
      success: false,
      error: 'Too many files',
    });
  }

  if (err.message?.includes('Invalid file type')) {
    return res.status(400).json({
      success: false,
      error: err.message,
    });
  }

  // Handle Zod validation errors
  if (err.errors && Array.isArray(err.errors)) {
    return res.status(400).json({
      success: false,
      error: 'Validation error',
      details: err.errors,
    });
  }

  // Generic error response
  res.status(err.status || 500).json({
    success: false,
    error: err.message || 'Internal server error',
  });
});

// ─────────────────────────────────────────────────────────────────
// SERVER STARTUP
// ─────────────────────────────────────────────────────────────────

app.listen(PORT, () => {
  console.log(`
╔════════════════════════════════════════╗
║    Tranzo Backend Server Started       ║
├════════════════════════════════════════┤
║ Environment: ${process.env.NODE_ENV || 'development'}
║ Port: ${PORT}
║ Origin: ${process.env.ORIGIN || 'https://tranzo.app'}
╚════════════════════════════════════════╝

Available Routes:
  - GET  /health
  - POST /api/auth/otp/send
  - POST /api/auth/otp/verify
  - POST /api/auth/google

  NEW - Passkey Routes:
  - POST /api/auth/passkey/register/options
  - POST /api/auth/passkey/register/verify
  - POST /api/auth/passkey/authenticate/options
  - POST /api/auth/passkey/authenticate/verify
  - GET  /api/auth/passkey/credentials (protected)
  - DELETE /api/auth/passkey/credentials/:id (protected)

  NEW - User Profile Routes:
  - PUT  /api/user/profile (protected)
  - POST /api/user/avatar (protected)
  - GET  /api/user/profile (protected)

WebAuthn Configuration:
  - RP_ID: ${process.env.RP_ID || 'tranzo.app'}
  - Origin: ${process.env.ORIGIN || 'https://tranzo.app'}

Database:
  - Prisma Studio: npx prisma studio
  `);
});

export default app;

// ─────────────────────────────────────────────────────────────────
// INTEGRATION NOTES
// ─────────────────────────────────────────────────────────────────

/**
 * WHAT'S NEW:
 *
 * 1. Passkey Routes (No Auth Required)
 *    - Users register new passkeys
 *    - Users authenticate with existing passkeys
 *    - No JWT token needed to call these endpoints
 *
 * 2. User Profile Routes (Auth Required)
 *    - Users update their profile (name, phone, language)
 *    - Users upload avatars
 *    - Users view their complete profile
 *    - All require valid JWT token in Authorization header
 *
 * IMPORTANT UPDATES NEEDED:
 *
 * 1. Update existing auth endpoints to return new profile fields:
 *    POST /api/auth/otp/verify
 *    POST /api/auth/google
 *    Response should include:
 *    - firstName, lastName, phone, preferredLanguage, avatarUrl, biometricEnabled
 *
 * 2. Replace in-memory challenge storage with Redis:
 *    // In passkey.routes.ts, replace:
 *    const registrationChallenges = new Map<string, string>();
 *    // With Redis:
 *    const redis = require('redis').createClient();
 *    await redis.setex(`reg_challenge:${userId}`, 600, challenge);
 *
 * 3. Update .env variables:
 *    RP_ID=tranzo.app
 *    ORIGIN=https://tranzo.app
 *
 * OPTIONAL ENHANCEMENTS:
 *
 * 1. Add rate limiting to auth endpoints
 * 2. Move avatar uploads to cloud storage (S3, Cloudinary)
 * 3. Add virus scanning for uploaded files
 * 4. Implement webhook logging for security events
 * 5. Add monitoring/alerting for failed auth attempts
 */
