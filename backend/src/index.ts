import express from "express";
import cors from "cors";
import helmet from "helmet";
import { ENV } from "./config/env.js";
import { errorHandler } from "./utils/errors.js";
import { generalLimiter, authLimiter } from "./middleware/rateLimit.js";
import { requireAuth } from "./middleware/auth.js";
import authRoutes from "./routes/auth.routes.js";
import balanceRoutes from "./routes/balances.routes.js";
import transferRoutes from "./routes/transfers.routes.js";
import dripperRoutes from "./routes/dripper.routes.js";
import settingsRoutes from "./routes/settings.routes.js";

const app = express();

// ─── Global Middleware ──────────────────────────────────────────

app.use(helmet());
app.use(cors());
app.use(express.json());
app.use(generalLimiter);

// ─── Health Check ───────────────────────────────────────────────

app.get("/health", (_req, res) => {
  res.json({ status: "ok", version: "1.0.0" });
});

// ─── Auth Routes (rate limited) ─────────────────────────────────

app.use("/auth", authLimiter, authRoutes);

// ─── Protected Routes ───────────────────────────────────────────

app.use("/balances", requireAuth, balanceRoutes);
app.use("/transfers", requireAuth, transferRoutes);
app.use("/dripper", requireAuth, dripperRoutes);
app.use("/user", requireAuth, settingsRoutes);

// ─── Error Handler ──────────────────────────────────────────────

app.use(errorHandler);

// ─── Start Server ───────────────────────────────────────────────

app.listen(ENV.PORT, () => {
  console.log(`\n  🟢 Tranzo Backend running on port ${ENV.PORT}`);
  console.log(`  📡 Chain: ${ENV.DEFAULT_CHAIN_ID}`);
  console.log(`  🌍 Environment: ${ENV.NODE_ENV}\n`);
});
