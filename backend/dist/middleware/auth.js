import jwt from "jsonwebtoken";
import { ENV } from "../config/env.js";
/**
 * JWT authentication middleware.
 * Extracts and validates the Bearer token from the Authorization header.
 */
export function requireAuth(req, res, next) {
    const header = req.headers.authorization;
    if (!header?.startsWith("Bearer ")) {
        res.status(401).json({ error: "Missing authorization token" });
        return;
    }
    try {
        const token = header.slice(7);
        const payload = jwt.verify(token, ENV.JWT_SECRET);
        req.user = payload;
        next();
    }
    catch {
        res.status(401).json({ error: "Invalid or expired token" });
    }
}
/**
 * Optional auth — attaches user if token is present, but doesn't block.
 */
export function optionalAuth(req, _res, next) {
    const header = req.headers.authorization;
    if (header?.startsWith("Bearer ")) {
        try {
            const token = header.slice(7);
            req.user = jwt.verify(token, ENV.JWT_SECRET);
        }
        catch {
            // Ignore invalid tokens in optional mode
        }
    }
    next();
}
//# sourceMappingURL=auth.js.map