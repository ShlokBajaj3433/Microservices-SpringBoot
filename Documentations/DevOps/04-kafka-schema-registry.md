# Kafka and Schema Registry

This project uses Kafka infrastructure for eventing and Schema Registry for schema governance.

## Components

- Zookeeper
- Kafka broker
- Schema Registry
- Kafka UI

Kubernetes manifests:

- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/zookeeper.yml
- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka.yml
- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/schema-registry.yml
- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka-ui.yml

## Service Endpoints (Kubernetes Service)

- kafka: ports 29092 and 9092
- schema-registry: port 8081
- kafka-ui: port 8080

## Important Runtime Configuration

Kafka deployment uses listeners:

- PLAINTEXT://kafka:29092 for in-cluster clients
- PLAINTEXT_HOST://localhost:9092 for local access style settings

Schema Registry uses:

- SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://kafka:29092

## Access for Local Validation

```bash
kubectl port-forward svc/schema-registry 18081:8081 -n default
kubectl port-forward svc/kafka-ui 18080:8080 -n default
```

Examples:

- Schema Registry subjects: http://localhost:18081/subjects
- Kafka UI: http://localhost:18080

## Stability Note

In this repository, both kafka and schema-registry manifests include:

- enableServiceLinks: false

This avoids environment-variable collisions from Kubernetes service links that can crash Confluent containers.
