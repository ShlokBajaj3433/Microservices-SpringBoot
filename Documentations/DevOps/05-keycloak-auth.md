# Keycloak and Auth Infrastructure

This project uses Keycloak as identity provider for OAuth2 and JWT-based authorization.

## Keycloak Runtime Sources

Docker Compose reference:

- ../../SPRINGBOOT-Microservices/Gateway/docker-compose.yml

Kubernetes reference:

- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/keycloak.yml

Realm definition:

- ../../SPRINGBOOT-Microservices/Gateway/docker/keycloak/realms/spring-microservices-realm.json

## Realm and Issuer

Configured realm:

- spring-microservices-security-realm

Gateway issuer URI (local):

- http://localhost:8181/realms/spring-microservices-security-realm

Gateway file:

- ../../SPRINGBOOT-Microservices/Gateway/src/main/resources/application.properties

## Local Access Patterns

Docker compose exposed mapping:

- localhost:8181 -> keycloak:8080

Kubernetes port-forward example:

```bash
kubectl port-forward svc/keycloak 18181:8080 -n default
```

Discovery check:

- http://localhost:18181/realms/master/.well-known/openid-configuration

## Implementation Notes

- Gateway resource server validates JWT tokens using Keycloak issuer information.
- If startup fails with issuer resolution errors, verify Keycloak pod/service readiness and realm availability first.
- Realm mismatch between imported realm and gateway issuer causes authentication bootstrap failures.
