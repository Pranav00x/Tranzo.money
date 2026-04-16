FROM node:20-alpine

WORKDIR /app

# Copy entire repo
COPY . .

# Install dependencies and build backend
WORKDIR /app/backend
RUN npm install --production=false
RUN npm run build

# Start backend
CMD ["npm", "start"]
