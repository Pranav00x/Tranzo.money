FROM node:20-alpine

WORKDIR /app

# Copy entire repo
COPY . .

# Install dependencies in backend
WORKDIR /app/backend
RUN npm ci --production=false
RUN npm run build

# Expose port (Railway will override with PORT env var)
EXPOSE 3000

# Start backend  
CMD ["node", "dist/index.js"]
