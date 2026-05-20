# DevOps Documentation

This section documents the DevOps setup implemented in this project.

## Contents

1. [Docker Build and Image Publish](./01-docker-build-images.md)
2. [Kubernetes Deployment](./02-kubernetes-deployment.md)
3. [Observability Stack](./03-observability-stack.md)
4. [Kafka and Schema Registry](./04-kafka-schema-registry.md)
5. [Keycloak and Auth Infrastructure](./05-keycloak-auth.md)
6. [Troubleshooting Runbook](./06-troubleshooting.md)

## Platform Snapshot

- Runtime: Spring Boot microservices
- Container build: Spring Boot build-image via Maven Wrapper
- Local orchestration: Docker Compose
- Cluster orchestration: kind + Kubernetes manifests
- Messaging: Kafka + Zookeeper + Schema Registry + Kafka UI
- Security: Keycloak + OAuth2 resource server in Gateway
- Observability: Prometheus + Grafana + Loki + Tempo

## Quick Start Path

1. Build images using scripts from repository root.
2. Create kind cluster and deploy infrastructure manifests.
3. Port-forward required services for local access.
4. Verify logs, metrics, and traces in observability tools.
5. Use troubleshooting runbook for known issues.

## CI/CD Status

No active GitHub workflow files are currently present in this repository. CI/CD automation can be added later under a .github/workflows directory.
