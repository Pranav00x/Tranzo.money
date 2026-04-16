/** General API rate limit: 100 req/min per IP */
export declare const generalLimiter: import("express-rate-limit").RateLimitRequestHandler;
/** Auth endpoints rate limit: 10 req/min per IP */
export declare const authLimiter: import("express-rate-limit").RateLimitRequestHandler;
/** Sensitive operations: 5 req/min per IP */
export declare const sensitiveLimiter: import("express-rate-limit").RateLimitRequestHandler;
