import { Router, Request, Response } from "express";
import { z } from "zod";
import { requireAuth } from "../middleware/auth.js";
import prisma from "../services/prisma.service.js";

const router = Router();

// ─── Get Profile ───────────────────────────────────────────────

router.get("/profile", requireAuth, async (req: Request, res: Response) => {
  try {
    const user = await prisma.user.findUniqueOrThrow({
      where: { id: req.user!.sub },
      select: {
        id: true,
        email: true,
        phone: true,
        displayName: true,
        avatarUrl: true,
        smartAccount: true,
        kycStatus: true,
        emailVerified: true,
        createdAt: true,
      },
    });
    res.json(user);
  } catch {
    res.status(404).json({ error: "User not found" });
  }
});

// ─── Update Profile ────────────────────────────────────────────

const updateProfileSchema = z.object({
  displayName: z.string().min(1).max(50).optional(),
  avatarUrl: z.string().url().optional(),
});

router.put("/profile", requireAuth, async (req: Request, res: Response) => {
  try {
    const data = updateProfileSchema.parse(req.body);
    const user = await prisma.user.update({
      where: { id: req.user!.sub },
      data,
      select: {
        id: true,
        displayName: true,
        avatarUrl: true,
      },
    });
    res.json(user);
  } catch (err: any) {
    res.status(400).json({ error: err.message });
  }
});

// ─── Get Account Info ──────────────────────────────────────────

router.get("/account", requireAuth, async (req: Request, res: Response) => {
  try {
    const user = await prisma.user.findUniqueOrThrow({
      where: { id: req.user!.sub },
      select: {
        smartAccount: true,
        openfortPlayer: true,
        kycStatus: true,
        createdAt: true,
      },
    });
    res.json(user);
  } catch {
    res.status(404).json({ error: "Account not found" });
  }
});

export default router;
