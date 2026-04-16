import rateLimit from "express-rate-limit";
import type { Request } from "express";

const getClientIp = (req: Request): string => {
  const forwarded = req.headers["x-forwarded-for"];
  if (typeof forwarded === "string") {
    return forwarded.split(",")[0].trim();
  }
  return req.ip || "unknown";
};

/** General API rate limit: 100 req/min per IP */
export const generalLimiter = rateLimit({
  windowMs: 60_000,
  max: 100,
  standardHeaders: true,
  legacyHeaders: false,
  message: { error: "Too many requests, please try again later" },
  keyGenerator: (req) => getClientIp(req),
  skip: (req) => {
    // Skip rate limiting for health checks
    return req.path === "/health";
  },
});

/** Auth endpoints rate limit: 10 req/min per IP */
export const authLimiter = rateLimit({
  windowMs: 60_000,
  max: 10,
  standardHeaders: true,
  legacyHeaders: false,
  message: { error: "Too many auth attempts, please try again later" },
  keyGenerator: (req) => getClientIp(req),
});

/** Sensitive operations: 5 req/min per IP */
export const sensitiveLimiter = rateLimit({
  windowMs: 60_000,
  max: 5,
  standardHeaders: true,
  legacyHeaders: false,
  message: { error: "Rate limit exceeded for this operation" },
  keyGenerator: (req) => getClientIp(req),
});
