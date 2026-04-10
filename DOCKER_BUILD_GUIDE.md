# Docker Image Build Guide

## Overview
This guide explains how to build and push Docker images for all Spring Boot microservices in this project.

## Prerequisites
- Docker installed and running
- Maven 3.6+
- Docker Hub account
- Docker credentials (username and PAT token)

## Available Microservices
1. **Gateway** - API Gateway service
2. **Product-Service** - Product management service
3. **order-service** - Order processing service
4. **inventory-service** - Inventory management service
5. **notification-service** - Notification/Email service

## Build Configuration

### Parent POM Properties
The parent `pom.xml` defines Docker credentials via Maven properties:
- `docker.username` - Docker Hub username (default: `shlokbajaj3433`)
- `docker.password` - Docker Hub PAT token (passed via command line)

### Spring Boot Plugin Configuration
Each service uses Spring Boot Maven Plugin v4.0.2 with:
- `publish: true` - Automatically push to Docker Hub after build
- `publishRegistry` - Docker Hub credentials
- Custom image names: `docker.io/shlokbajaj3433/{service-name}`

## Build Commands

### Build All Services
```bash
cd c:\codes\SpringBoot\ Microservices\SPRINGBOOT-Microservices
mvn clean spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN
```

### Build Individual Service
```bash
# Example: Build only Product-Service
cd c:\codes\SpringBoot\ Microservices\SPRINGBOOT-Microservices\Product-Service
mvnw.cmd spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN
```

### For Each Service:
```bash
# Gateway
cd .\Gateway
mvnw.cmd spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN

# Product-Service
cd .\Product-Service
mvnw.cmd spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN

# order-service
cd .\order-service
mvnw.cmd spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN

# inventory-service
cd .\inventory-service
mvnw.cmd spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN

# notification-service
cd .\notification-service
mvnw.cmd spring-boot:build-image -DskipTests -Ddocker.password=YOUR_DOCKER_PAT_TOKEN
```

## Your Credentials
- **Username**: `shlokbajaj3433`
- **PAT Token**: `YOUR_DOCKER_PAT_TOKEN`

**Usage Example:**
```bash
mvn clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

## Expected Output
When builds complete successfully, you'll see:
- Compiled JAR for each service
- Docker image created locally
- Image pushed to Docker Hub
- Log message: `Successfully published image 'docker.io/shlokbajaj3433/{service-name}:latest'`

## Troubleshooting

### Build Fails with Password Error
- Ensure password is wrapped in quotes: `"-Ddocker.password=..."`
- Verify PAT token is correct and has appropriate scopes
- Check Docker daemon is running

### Image Push Fails
- Verify Docker credentials are correct
- Check Docker Hub connection: `docker login`
- Ensure repository exists on Docker Hub

### Compilation Errors
- Run `mvn clean` before building
- Verify Java 21 is installed: `java -version`
- Check all dependencies resolve: `mvn dependency:tree`

## Loki Logging Configuration
All services now support Grafana Loki logging. To enable:

### Activate Loki Profile
```bash
mvnw.cmd spring-boot:build-image -DskipTests -Dspring-boot.build-image.environment.SPRING_PROFILES_ACTIVE=loki -Ddocker.password=YOUR_DOCKER_PAT_TOKEN
```

### Configure Loki URL
Set `loki.url` in `application.properties` or via environment variable:
```properties
loki.url=http://localhost:3100/loki/api/v1/push
```

## Image Names
- `docker.io/shlokbajaj3433/gateway:latest`
- `docker.io/shlokbajaj3433/product-service:latest`
- `docker.io/shlokbajaj3433/order-service:latest`
- `docker.io/shlokbajaj3433/inventory-service:latest`
- `docker.io/shlokbajaj3433/notification-service:latest`

## Next Steps
1. Build images using commands above
2. Verify images on Docker Hub
3. Deploy using Kubernetes manifests in `K8s/manifests/`
4. Configure Loki for centralized logging


