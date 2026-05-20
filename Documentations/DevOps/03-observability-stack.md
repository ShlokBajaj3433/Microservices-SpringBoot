# Observability Stack

This project includes logs, metrics, and traces infrastructure.

## Components

- Prometheus: metrics scrape and query
- Grafana: dashboards and visualization
- Loki: centralized logs backend
- Tempo: distributed tracing backend

## Local Docker Compose Reference

Observability containers are defined in:

- ../../SPRINGBOOT-Microservices/Gateway/docker-compose.yml

Supporting configs are under:

- ../../SPRINGBOOT-Microservices/Gateway/docker/prometheus/
- ../../SPRINGBOOT-Microservices/Gateway/docker/grafana/
- ../../SPRINGBOOT-Microservices/Gateway/docker/tempo/

## Kubernetes Manifests

- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/prometheus.yml
- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/grafana.yml
- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/loki.yml
- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/tempo.yml

## Gateway Telemetry Settings

Gateway application properties include tracing and metrics fields, such as:

- management.tracing.enabled=true
- management.tracing.sampling.probability=1.0
- management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
- management.metrics.distribution.percentiles-histogram.http.server.requests=true

File reference:

- ../../SPRINGBOOT-Microservices/Gateway/src/main/resources/application.properties

## Access During Local Testing

Use port-forward in Kubernetes:

```bash
kubectl port-forward svc/grafana 13000:3000 -n default
kubectl port-forward svc/prometheus 19090:9090 -n default
kubectl port-forward svc/tempo 19411:9411 -n default
```

Then open:

- http://localhost:13000
- http://localhost:19090
- http://localhost:19411

## Notes

- Gateway currently uses a webflux-style observability key in properties. If gateway mode is MVC, switch to the MVC-specific key to ensure expected instrumentation behavior.
- Tempo mount errors in compose are usually resolved by recreating only the tempo service container.
