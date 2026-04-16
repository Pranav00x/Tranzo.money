FROM node:20-alpine

WORKDIR /app/backend

# Copy only dist and node_modules requirements
COPY backend/dist ./dist
COPY backend/package*.json ./

# Install only production dependencies
RUN npm ci --omit=dev

EXPOSE 3000

CMD ["node", "dist/index.js"]
