import { Router, Request, Response } from "express";
import { z } from "zod";
import { requireAuth } from "../middleware/auth.js";
import { StreamService } from "../services/stream.service.js";

const router = Router();

// ─── Create Stream ─────────────────────────────────────────────

const createStreamSchema = z.object({
  employeeAddress: z.string().startsWith("0x"),
  tokenAddress: z.string().startsWith("0x"),
  amountPerSecond: z.string(),
  startTime: z.string().datetime(),
  endTime: z.string().datetime(),
});

router.post("/", requireAuth, async (req: Request, res: Response) => {
  try {
    const params = createStreamSchema.parse(req.body);
    const result = await StreamService.createStream({
      employerId: req.user!.sub,
      employeeAddress: params.employeeAddress,
      tokenAddress: params.tokenAddress,
      amountPerSecond: params.amountPerSecond,
      startTime: new Date(params.startTime),
      endTime: new Date(params.endTime),
    });

    res.json(result);
  } catch (err: any) {
    res.status(400).json({ error: err.message });
  }
});

// ─── List Streams ──────────────────────────────────────────────

router.get("/", requireAuth, async (req: Request, res: Response) => {
  try {
    const role = (req.query.role as string) === "employer" ? "employer" : "employee";
    const streams = await StreamService.listStreams(req.user!.sub, role);
    res.json({ streams });
  } catch (err: any) {
    res.status(500).json({ error: err.message });
  }
});

// ─── Get Stream Detail ─────────────────────────────────────────

router.get("/:id", requireAuth, async (req: Request, res: Response) => {
  try {
    const stream = await StreamService.getStream(req.params.id as string);
    res.json(stream);
  } catch (err: any) {
    res.status(404).json({ error: err.message });
  }
});

// ─── Withdraw ──────────────────────────────────────────────────

router.post("/:id/withdraw", requireAuth, async (req: Request, res: Response) => {
  try {
    const result = await StreamService.withdrawFromStream(
      req.params.id as string,
      req.user!.sub
    );
    res.json(result);
  } catch (err: any) {
    res.status(400).json({ error: err.message });
  }
});

// ─── Cancel Stream ─────────────────────────────────────────────

router.post("/:id/cancel", requireAuth, async (req: Request, res: Response) => {
  try {
    const result = await StreamService.cancelStream(
      req.params.id as string,
      req.user!.sub
    );
    res.json(result);
  } catch (err: any) {
    res.status(400).json({ error: err.message });
  }
});

export default router;
