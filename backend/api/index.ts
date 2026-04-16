import express from "express";
import cors from "cors";
import helmet from "helmet";
import { ENV } from "../src/config/env.js";
import { errorHandler } from "../src/utils/errors.js";
import { generalLimiter, authLimiter } from "../src/middleware/rateLimit.js";
import { requireAuth } from "../src/middleware/auth.js";
import authRoutes from "../src/routes/auth.routes.js";
import balanceRoutes from "../src/routes/balances.routes.js";
import transferRoutes from "../src/routes/transfers.routes.js";
import dripperRoutes from "../src/routes/dripper.routes.js";
import settingsRoutes from "../src/routes/settings.routes.js";
import cardRoutes from "../src/routes/card.routes.js";
import swapRoutes from "../src/routes/swap.routes.js";

const app = express();

app.use(helmet());
app.use(cors());
app.use(express.json());
app.use(generalLimiter);

app.get("/health", (_req, res) => {
  res.json({ status: "ok", version: "1.0.0" });
});

app.use("/auth", authLimiter, authRoutes);
app.use("/balances", requireAuth, balanceRoutes);
app.use("/transfers", requireAuth, transferRoutes);
app.use("/dripper", requireAuth, dripperRoutes);
app.use("/user", requireAuth, settingsRoutes);
app.use("/card", cardRoutes);
app.use("/swap", requireAuth, swapRoutes);

app.use(errorHandler);

export default app;
