export const validateRequest = (schema) => {
    return async (req, res, next) => {
        try {
            await schema.parseAsync(req.body);
            next();
        }
        catch (error) {
            return res.status(400).json({
                success: false,
                error: error.errors || error.message || 'Validation failed',
            });
        }
    };
};
//# sourceMappingURL=validation.js.map