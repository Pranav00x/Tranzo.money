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
import cardRoutes from "./routes/card.routes.js";
import swapRoutes from "./routes/swap.routes.js";
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
const PORT = process.env.PORT || ENV.PORT;
app.listen(PORT, () => {
    console.log(`\n  🟢 Tranzo Backend v1.0.1 running on port ${PORT}`);
    console.log(`  📡 Chain: ${ENV.DEFAULT_CHAIN_ID}`);
    console.log(`  🌍 Environment: ${ENV.NODE_ENV}`);
    console.log(`  ✅ ZeroDev Integration: Kernel Accounts Active\n`);
});
//# sourceMappingURL=index.js.map