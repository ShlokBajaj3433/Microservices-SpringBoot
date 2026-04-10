# Quick Build Reference

## ⚡ One-Command Build (All Services)

### Using Batch Script (Windows CMD)
```batch
cd c:\codes\SpringBoot\ Microservices
build-images.bat YOUR_DOCKER_PAT_TOKEN
```

### Using PowerShell
```powershell
cd c:\codes\SpringBoot\ Microservices
.\build-images.ps1 -DockerPassword "YOUR_DOCKER_PAT_TOKEN"
```

### Using Maven Directly
```bash
cd c:\codes\SpringBoot\ Microservices\SPRINGBOOT-Microservices
mvn clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

---

## 🔧 Individual Service Builds

### Gateway
```bash
cd SPRINGBOOT-Microservices\Gateway
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

### Product-Service
```bash
cd SPRINGBOOT-Microservices\Product-Service
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

### Order-Service
```bash
cd SPRINGBOOT-Microservices\order-service
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

### Inventory-Service
```bash
cd SPRINGBOOT-Microservices\inventory-service
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

### Notification-Service
```bash
cd SPRINGBOOT-Microservices\notification-service
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="YOUR_DOCKER_PAT_TOKEN"
```

---

## 📋 Docker Credentials
- **Username**: `shlokbajaj3433`
- **PAT Token**: `YOUR_DOCKER_PAT_TOKEN`
- **Registry**: `docker.io`

---

## ✅ What Gets Built

| Service | Image Name | Status |
|---------|-----------|--------|
| Gateway | `docker.io/shlokbajaj3433/gateway:latest` | ✓ Loki configured |
| Product-Service | `docker.io/shlokbajaj3433/product-service:latest` | ✓ Loki configured |
| Order-Service | `docker.io/shlokbajaj3433/order-service:latest` | ✓ Loki configured |
| Inventory-Service | `docker.io/shlokbajaj3433/inventory-service:latest` | ✓ Loki configured |
| Notification-Service | `docker.io/shlokbajaj3433/notification-service:latest` | ✓ Loki configured |

---

## 📊 Build Features

✓ **Loki Logging** - All services configured with Grafana Loki support  
✓ **Docker Auto-Push** - Images automatically pushed to Docker Hub  
✓ **Prometheus Metrics** - All services include Prometheus endpoints  
✓ **Distributed Tracing** - Zipkin tracing enabled  
✓ **Spring Cloud** - Gateway and service discovery configured  

---

## 🔐 POM Configuration

All services updated to use Maven property `${docker.password}`:
- Parent POM: `SPRINGBOOT-Microservices/pom.xml`
- Gateway: `SPRINGBOOT-Microservices/Gateway/pom.xml`
- Product-Service: `SPRINGBOOT-Microservices/Product-Service/pom.xml`
- Order-Service: `SPRINGBOOT-Microservices/order-service/pom.xml`
- Inventory-Service: `SPRINGBOOT-Microservices/inventory-service/pom.xml`
- Notification-Service: `SPRINGBOOT-Microservices/notification-service/pom.xml`

Changed from `${env.DOCKER_PASSWORD}` to `${docker.password}` for better flexibility.

---

## 🚀 Tips

- Use `-DskipTests` to skip unit tests and speed up build (already included)
- First build takes longer (downloading dependencies)
- Subsequent builds are faster (cached dependencies)
- Docker must be running for `spring-boot:build-image` to work
- Ensure 10+ GB disk space available for images

---

## 📝 Logs

Build logs are printed to console. For debugging, add:
```bash
-X  # Enable debug output
```

---

Last Updated: April 10, 2026

