# 🚀 Spring Boot Microservices - Complete Startup Guide

## Prerequisites
Before starting, ensure you have:
- Docker and Docker Compose installed
- Java 25 or Java 21 installed
- Maven installed
- Ports 3307, 27017, 9092, 8181, 8080-8084, 9000 are available

## Step 1: Start Infrastructure Dependencies

### Start Gateway Infrastructure (Keycloak, Loki, Prometheus, Grafana, Tempo)
```bash
cd Gateway
docker-compose up -d
```
Wait for all containers to be healthy (2-3 minutes).

### Start Order Service Infrastructure (MySQL, Kafka, Schema Registry)
```bash
cd order-service
docker-compose up -d
```
Wait for MySQL and Kafka to be ready (verify with `docker ps`).

### Start Product Service Infrastructure (MongoDB)
```bash
cd Product-Service
docker-compose up -d
```

Wait 30 seconds for MongoDB to initialize.

### Verify Infrastructure
```bash
docker ps
# Should show containers: mysql, keycloak-mysql, keycloak, mongodb, zookeeper, broker, schema-registry, loki, prometheus, tempo, grafana
```

## Step 2: Wait for Services to Be Ready

### Check MySQL Connection
```bash
# Test MySQL on port 3307
mysql -h localhost -P 3307 -u root -pShlok@sql -e "SHOW DATABASES;"
```

### Check MongoDB
```bash
# Test MongoDB on port 27017
mongosh --host localhost:27017 -u root -p example --authenticationDatabase admin --eval "db.adminCommand('ping')"
```

### Check Kafka
```bash
# Kafka should be accessible on localhost:9092
# Check with: kafka-broker-api-versions.sh --bootstrap-server localhost:9092
```

## Step 3: Build All Services

```bash
# Order Service
cd order-service
mvn clean install -DskipTests

# Product Service
cd ../Product-Service
mvn clean install -DskipTests

# Inventory Service
cd ../inventory-service
mvn clean install -DskipTests

# Notification Service
cd ../notification-service
mvn clean install -DskipTests

# Gateway
cd ../Gateway
mvn clean install -DskipTests
```

## Step 4: Start Microservices (in this order)

### Terminal 1: Start Order Service
```bash
cd order-service
mvn spring-boot:run
# Wait for: "Started OrderServiceApplication in X seconds"
```

### Terminal 2: Start Inventory Service
```bash
cd inventory-service
mvn spring-boot:run
# Wait for: "Started InventoryServiceApplication in X seconds"
```

### Terminal 3: Start Product Service
```bash
cd Product-Service
mvn spring-boot:run
# Wait for: "Started ProductServiceApplication in X seconds"
```

### Terminal 4: Start Notification Service
```bash
cd notification-service
mvn spring-boot:run
# Wait for: "Started NotificationServiceApplication in X seconds"
```

### Terminal 5: Start Gateway (Last!)
```bash
cd Gateway
mvn spring-boot:run
# Wait for: "Started GatewayApplication in X seconds"
```

## Step 5: Verify All Services Are Running

```bash
# Check Gateway is accessible
curl http://localhost:9000/actuator/health

# Check Order Service
curl http://localhost:8081/actuator/health

# Check Product Service
curl http://localhost:8083/actuator/health

# Check Inventory Service
curl http://localhost:8082/actuator/health

# Check Notification Service
curl http://localhost:8084/actuator/health
```

Expected response for each:
```json
{"status":"UP"}
```

## Step 6: Test Order Placement

### Via API
```bash
curl -X POST http://localhost:9000/api/order \
  -H "Content-Type: application/json" \
  -d '{
    "skuCode": "IPHONE_13",
    "price": 1250,
    "quantity": 1,
    "userDetails": {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com"
    }
  }'
```

### Via Frontend
1. Open http://localhost:4200
2. Click on a product
3. Click "Place Order"
4. Should see "Order placed successfully"

## Troubleshooting

### If you get 503 Service Unavailable

**Check if services are running:**
```bash
# Check all services are UP
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
curl http://localhost:8084/actuator/health
```

If any returns DOWN or error, check the service logs for:
- Database connection errors
- Kafka connection errors
- Port conflicts

**Check service logs:**
```bash
# For example, if Order Service won't start:
cd order-service
mvn spring-boot:run
# Look for error messages in the output
```

### If you get database connection errors

**For MySQL Services:**
```bash
# Check if MySQL is running
docker ps | grep mysql

# Check MySQL logs
docker logs mysql

# Restart MySQL if needed
docker restart mysql
```

**For MongoDB:**
```bash
# Check if MongoDB is running
docker ps | grep mongodb

# Check MongoDB logs
docker logs mongodb

# Restart MongoDB if needed
docker restart mongodb
```

### If you get Kafka connection errors

```bash
# Check if Kafka is running
docker ps | grep broker

# Check Kafka logs
docker logs broker
```

### If Gateway routes to 503

Make sure ALL backend services are UP before testing through Gateway:
```bash
# Verify all 4 services are running
curl http://localhost:8081/actuator/health  # Order Service
curl http://localhost:8082/actuator/health  # Inventory Service
curl http://localhost:8083/actuator/health  # Product Service
curl http://localhost:8084/actuator/health  # Notification Service
```

## Key Service Ports

| Service | Port | Type |
|---------|------|------|
| Gateway | 9000 | API Gateway |
| Order Service | 8081 | REST API |
| Inventory Service | 8082 | REST API |
| Product Service | 8083 | REST API |
| Notification Service | 8084 | REST API |
| MySQL (Order/Inventory) | 3307 | Database |
| MongoDB (Product) | 27017 | Database |
| Kafka | 9092 | Message Broker |
| Keycloak | 8181 | Auth Server |
| Grafana | 3000 | Monitoring Dashboard |
| Prometheus | 9090 | Metrics Database |
| Zipkin | 9411 | Tracing UI |
| Loki | 3100 | Log Aggregation |

## Quick Cleanup

To stop all containers:
```bash
docker-compose down
# Run this in Gateway, order-service, and Product-Service directories
```

To remove all data:
```bash
docker-compose down -v
# Run this in Gateway, order-service, and Product-Service directories
```
