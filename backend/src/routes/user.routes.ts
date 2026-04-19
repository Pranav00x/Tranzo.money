import express from 'express';
import { z } from 'zod';
import multer from 'multer';
import path from 'path';
import fs from 'fs';
import prisma from '../services/prisma.service.js';
import { requireAuth } from '../middleware/auth.js';
import { validateRequest } from '../middleware/validation.js';

const router = express.Router();

// Configure multer for avatar uploads
const uploadDir = path.join(process.cwd(), 'uploads/avatars');
if (!fs.existsSync(uploadDir)) {
  fs.mkdirSync(uploadDir, { recursive: true });
}

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, uploadDir);
  },
  filename: (req, file, cb) => {
    const userId = (req as any).userId;
    const ext = path.extname(file.originalname);
    cb(null, `${userId}-${Date.now()}${ext}`);
  },
});

const upload = multer({
  storage,
  limits: { fileSize: 5 * 1024 * 1024 }, // 5MB limit
  fileFilter: (req, file, cb) => {
    const allowedMimes = ['image/jpeg', 'image/png', 'image/webp'];
    if (allowedMimes.includes(file.mimetype)) {
      cb(null, true);
    } else {
      cb(new Error('Invalid file type. Only JPEG, PNG, and WebP allowed.'));
    }
  },
});

// ────────────────────────────────────────────────────────
// PUT /user/profile - Update user profile information
// ────────────────────────────────────────────────────────

const updateProfileSchema = z.object({
  displayName: z.string().min(2).max(50).optional(),
  phone: z.string().regex(/^[\d\s+\-()]+$/).min(10).optional().or(z.literal('')),
});

router.put(
  '/profile',
  requireAuth,
  validateRequest(updateProfileSchema),
  async (req, res) => {
    try {
      const userId = (req as any).userId;
      const { displayName, phone } = req.body;

      const updatedUser = await prisma.user.update({
        where: { id: userId },
        data: {
          ...(displayName && { displayName }),
          ...(phone !== undefined && { phone: phone || null }),
        },
        select: {
          id: true,
          email: true,
          phone: true,
          displayName: true,
          avatarUrl: true,
        },
      });

      res.json({
        success: true,
        message: 'Profile updated successfully',
        user: updatedUser,
      });
    } catch (error: any) {
      console.error('Profile update error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to update profile',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// POST /user/avatar - Upload user avatar
// ────────────────────────────────────────────────────────

router.post(
  '/avatar',
  requireAuth,
  upload.single('avatar'),
  async (req, res) => {
    try {
      if (!(req as any).file) {
        return res.status(400).json({
          success: false,
          error: 'No file uploaded',
        });
      }

      const userId = (req as any).userId;
      const avatarUrl = `/uploads/avatars/${(req as any).file.filename}`;

      // Delete old avatar if exists
      const user = await prisma.user.findUnique({
        where: { id: userId },
        select: { avatarUrl: true },
      });

      if (user?.avatarUrl) {
        const oldPath = path.join(process.cwd(), 'public', user.avatarUrl);
        if (fs.existsSync(oldPath)) {
          fs.unlinkSync(oldPath);
        }
      }

      const updatedUser = await prisma.user.update({
        where: { id: userId },
        data: { avatarUrl },
        select: {
          id: true,
          email: true,
          displayName: true,
          avatarUrl: true,
        },
      });

      res.json({
        success: true,
        message: 'Avatar uploaded successfully',
        avatarUrl,
        user: updatedUser,
      });
    } catch (error: any) {
      console.error('Avatar upload error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to upload avatar',
      });
    }
  }
);

// ────────────────────────────────────────────────────────
// GET /user/profile - Get current user profile
// ────────────────────────────────────────────────────────

router.get(
  '/profile',
  requireAuth,
  async (req, res) => {
    try {
      const userId = (req as any).userId;

      const user = await prisma.user.findUnique({
        where: { id: userId },
        select: {
          id: true,
          email: true,
          displayName: true,
          phone: true,
          avatarUrl: true,
          smartAccount: true,
          createdAt: true,
        },
      });

      if (!user) {
        return res.status(404).json({
          success: false,
          error: 'User not found',
        });
      }

      res.json({
        success: true,
        user,
      });
    } catch (error: any) {
      console.error('Get profile error:', error);
      res.status(400).json({
        success: false,
        error: error.message || 'Failed to get profile',
      });
    }
  }
);

export default router;
