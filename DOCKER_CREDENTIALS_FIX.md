# 🔧 Docker Credentials Configuration - FIXED

## Issue Description
The build was failing with:
```
[ERROR] Invalid Docker publish registry configuration, either token or username/password must be provided
```

## Root Cause
**Inconsistency in Docker credential references across pom.xml files:**
- **Gateway, Product-Service, Order-Service, Notification-Service**: Using `${env.DOCKER_PASSWORD}` (environment variable)
- **Inventory-Service**: Using `${docker.password}` (Maven property)

The build scripts pass credentials as **Maven properties** with `-Ddocker.password=...`, NOT as environment variables.

## Solution Applied

### ✅ Fixed All POM.xml Files
Updated all services to use consistent `${docker.password}` Maven property:

1. **Gateway** - `SPRINGBOOT-Microservices/Gateway/pom.xml`
   - Changed: `${env.DOCKER_PASSWORD}` → `${docker.password}`

2. **Product-Service** - `SPRINGBOOT-Microservices/Product-Service/pom.xml`
   - Changed: `${env.DOCKER_PASSWORD}` → `${docker.password}`

3. **Order-Service** - `SPRINGBOOT-Microservices/order-service/pom.xml`
   - Changed: `${env.DOCKER_PASSWORD}` → `${docker.password}`

4. **Notification-Service** - `SPRINGBOOT-Microservices/notification-service/pom.xml`
   - Changed: `${env.DOCKER_PASSWORD}` → `${docker.password}`

5. **Inventory-Service** - Already correctly using `${docker.password}`

### ✅ Verified Loki Configuration
All services have proper logback-spring.xml with Loki appender configured:
- **Gateway** ✓
- **Product-Service** ✓
- **Order-Service** ✓
- **Inventory-Service** ✓
- **Notification-Service** ✓

## How to Build Now

### Using PowerShell
```powershell
cd "C:\codes\SpringBoot Microservices"
.\build-images.ps1 -DockerPassword "YOUR_DOCKER_PAT_TOKEN"
```

### Using Batch Script
```batch
cd C:\codes\SpringBoot Microservices
build-images.bat YOUR_DOCKER_PAT_TOKEN
```

### Using Maven Directly
```bash
cd "C:\codes\SpringBoot Microservices\SPRINGBOOT-Microservices"
mvn clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

### Build Individual Service (e.g., Inventory)
```bash
cd "C:\codes\SpringBoot Microservices\SPRINGBOOT-Microservices\inventory-service"
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

## Key Points

✓ **Maven Properties vs Environment Variables**: 
- Maven properties: `-Ddocker.password=...` (command line)
- Environment variables: `-Denv.VARIABLE=...` (system env)
- Our scripts use Maven properties, so all pom.xml must reference `${docker.password}`

✓ **Loki Configuration Ready**:
- All services have Loki appender configured
- Activate with Spring profile: `-Dspring.profiles.active=loki`
- Default Loki URL: `http://localhost:3100/loki/api/v1/push`
- Can be overridden with: `-Dloki.url=...`

✓ **Docker Hub Credentials**:
- Username: `shlokbajaj3433`
- PAT Token: `YOUR_DOCKER_PAT_TOKEN`

## Testing the Fix

After the fix, the build should succeed with output:
```
[INFO] --- spring-boot:4.0.2:build-image (default-cli) @ inventory-service ---
[INFO] Building image: docker.io/shlokbajaj3433/inventory-service:latest
...
[INFO] Successfully published image 'docker.io/shlokbajaj3433/inventory-service:latest'
[INFO] BUILD SUCCESS
```

## Files Modified

| File | Change |
|------|--------|
| `Gateway/pom.xml` | Line 147: `${env.DOCKER_PASSWORD}` → `${docker.password}` |
| `Product-Service/pom.xml` | Line 194: `${env.DOCKER_PASSWORD}` → `${docker.password}` |
| `order-service/pom.xml` | Line 204: `${env.DOCKER_PASSWORD}` → `${docker.password}` |
| `notification-service/pom.xml` | Line 148: `${env.DOCKER_PASSWORD}` → `${docker.password}` |
| `inventory-service/pom.xml` | No changes needed (already correct) |

---

**Status**: ✅ FIXED - Ready to build Docker images
**Last Updated**: April 10, 2026

