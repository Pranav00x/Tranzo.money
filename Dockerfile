FROM node:20-alpine

WORKDIR /app

# Copy backend source structure
COPY backend/dist ./dist
COPY backend/prisma ./prisma
COPY backend/package*.json ./

# Install production dependencies only
RUN npm ci --omit=dev

EXPOSE 3000

CMD ["node", "dist/index.js"]
