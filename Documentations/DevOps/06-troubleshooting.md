# Troubleshooting Runbook

This runbook lists known DevOps issues found in this repository and practical fixes.

## 1. Kafka or Schema Registry CrashLoopBackOff

Symptoms:

- pod restarts continuously
- logs show only deprecation text around port settings

Diagnosis:

```bash
kubectl get pods -n default
kubectl describe pod <pod-name> -n default
kubectl logs <pod-name> -n default --previous
```

Fix:

- Ensure both deployments include enableServiceLinks: false
- File refs:
  - ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka.yml
  - ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/schema-registry.yml

Apply:

```bash
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/schema-registry.yml
kubectl rollout status deployment/kafka -n default
kubectl rollout status deployment/schema-registry -n default
```

## 2. Gateway JWT Issuer Resolution Failure

Error pattern:

- Unable to resolve the Configuration with the provided Issuer

Checks:

```bash
curl http://localhost:8181/realms/spring-microservices-security-realm/.well-known/openid-configuration
kubectl get pods -n default | findstr keycloak
```

Fix direction:

- Ensure Keycloak is reachable and realm exists.
- Ensure gateway issuer URI matches the imported realm name.
- Validate Gateway security config and JWT decoder settings.

## 3. Tempo Compose Mount Failure

Error pattern:

- mount path reports not a directory for tempo.yaml

Fix:

```bash
cd SPRINGBOOT-Microservices/Gateway
docker compose rm -sf tempo
docker compose up -d --build tempo
```

## 4. Image Build Succeeds but Push Missing

Symptoms:

- local image built, no Docker Hub push events

Checks:

- verify per-service spring-boot-maven-plugin image name and publish configuration
- verify token passed as -Ddocker.password in script

Fix:

- correct plugin publish config and rerun build scripts from repository root.

## 5. Basic Health Commands

```bash
kubectl get pods -n default
kubectl get svc -n default
kubectl logs deploy/kafka -n default --tail=100
kubectl logs deploy/schema-registry -n default --tail=100
kubectl logs deploy/keycloak -n default --tail=100
```
