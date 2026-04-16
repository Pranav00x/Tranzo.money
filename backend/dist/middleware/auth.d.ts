import { Request, Response, NextFunction } from "express";
export interface AuthPayload {
    sub: string;
    smartAccount: string;
    chainId: number;
}
declare global {
    namespace Express {
        interface Request {
            user?: AuthPayload;
        }
    }
}
/**
 * JWT authentication middleware.
 * Extracts and validates the Bearer token from the Authorization header.
 */
export declare function requireAuth(req: Request, res: Response, next: NextFunction): void;
/**
 * Optional auth — attaches user if token is present, but doesn't block.
 */
export declare function optionalAuth(req: Request, _res: Response, next: NextFunction): void;
