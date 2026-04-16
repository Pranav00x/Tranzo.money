FROM node:20-alpine

WORKDIR /app

# Copy backend files
COPY backend/package*.json ./
COPY backend/prisma ./prisma
COPY backend/dist ./dist

# Install dependencies and generate Prisma client
RUN npm ci --omit=dev && npx prisma generate

EXPOSE 3000

CMD ["node", "dist/index.js"]
