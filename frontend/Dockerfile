# Step 1: Use a specific Node.js version (not "latest" to avoid breaking changes)
FROM node:18-alpine AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json first for better caching
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire project
COPY . .

# Set the environment variable at build time
ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=$REACT_APP_API_BASE_URL

# Print the value of REACT_APP_API_BASE_URL (for debugging)
RUN echo "REACT_APP_API_BASE_URL is set to: $REACT_APP_API_BASE_URL"


# Build the React app
RUN npm run build

# Step 2: Serve the React app using a lightweight HTTP server
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Install `serve` globally
RUN npm install -g serve

# Copy the built React app from the build stage
COPY --from=build /app/build ./build

# Expose an unprivileged port (default for `serve`)
EXPOSE 3000

# Run as a non-root user (required for OpenShift and other restricted environments)
RUN addgroup -g 1001 appgroup && \
    adduser -u 1001 -G appgroup -D appuser
USER appuser

# Start the React app
CMD ["serve", "-s", "build", "-l", "3000"]