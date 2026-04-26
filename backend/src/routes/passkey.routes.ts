import { Router } from "express";
import { PasskeyService } from "../services/passkey.service.js";
import { requireAuth } from "../middleware/auth.js";
import { AuthService } from "../services/auth.service.js";

const router = Router();

// Challenges store (In prod use Redis)
const registrationChallenges = new Map<string, string>();
const authenticationChallenges = new Map<string, string>();

/**
 * Register a new passkey - Step 1: Get Options
 */
router.post("/register/options", requireAuth, async (req: any, res) => {
  try {
    const options = await PasskeyService.getRegistrationOptions(req.user.sub, req.user.email);
    registrationChallenges.set(req.user.sub, options.challenge);
    res.json(options);
  } catch (err: any) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * Register a new passkey - Step 2: Verify
 */
router.post("/register/verify", requireAuth, async (req: any, res) => {
  try {
    const expectedChallenge = registrationChallenges.get(req.user.sub);
    if (!expectedChallenge) throw new Error("Challenge not found");

    await PasskeyService.verifyRegistration(req.user.sub, req.body, expectedChallenge);
    registrationChallenges.delete(req.user.sub);
    
    res.json({ success: true });
  } catch (err: any) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * Login with passkey - Step 1: Get Options
 */
router.post("/login/options", async (req, res) => {
  try {
    const { email } = req.body;
    // In a real flow, we might need a way to look up the user by email first
    // or use a non-resident key flow. We'll assume the email is provided.
    
    // For now, let's just generate generic options if email is not found
    // or handle it via a user lookup.
    res.status(501).json({ error: "Passkey login via email lookup coming soon" });
  } catch (err: any) {
    res.status(500).json({ error: err.message });
  }
});

export default router;
