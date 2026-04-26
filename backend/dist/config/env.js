import "dotenv/config";
import { z } from "zod";
const envSchema = z.object({
    PORT: z.coerce.number().default(3000),
    NODE_ENV: z.enum(["development", "production", "test"]).default("development"),
    // Database
    DATABASE_URL: z.string(),
    // Redis
    REDIS_URL: z.string().default("redis://localhost:6379"),
    // JWT
    JWT_SECRET: z.string().min(16),
    JWT_REFRESH_SECRET: z.string().min(16),
    // Openfort (deprecated - migration to ZeroDev in progress)
    OPENFORT_API_KEY: z.string().optional(),
    OPENFORT_POLICY_ID: z.string().optional(),
    OPENFORT_CONTRACT_ID: z.string().optional(),
    // Email
    SMTP_HOST: z.string().default("smtp.resend.com"),
    SMTP_PORT: z.coerce.number().default(465),
    SMTP_USER: z.string().default("resend"),
    SMTP_PASS: z.string().optional(),
    EMAIL_FROM: z.string().default("noreply@tranzo.money"),
    // Google OAuth
    GOOGLE_CLIENT_ID: z.string().optional(),
    // ZeroDev
    ZERODEV_PROJECT_ID: z.string().optional(),
    ZERODEV_RPC_URL: z.string().optional(),
    // Chains
    POLYGON_RPC_URL: z.string().optional(),
    BASE_RPC_URL: z.string().optional(),
    DEFAULT_CHAIN_ID: z.coerce.number().default(84532),
});
export const ENV = envSchema.parse(process.env);
//# sourceMappingURL=env.js.map