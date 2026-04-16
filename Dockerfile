FROM node:20-alpine

WORKDIR /app/backend

# Copy dist folder (pre-compiled code)
COPY backend/dist ./dist

# Copy Prisma schema (required for runtime)
COPY backend/prisma ./prisma

# Copy package files
COPY backend/package*.json ./

# Install only production dependencies + generate Prisma client
RUN npm ci --omit=dev

EXPOSE 3000

CMD ["node", "dist/index.js"]
