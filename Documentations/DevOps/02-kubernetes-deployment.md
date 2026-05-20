# Kubernetes Deployment

This guide describes how to deploy project infrastructure in a local kind cluster.

## Prerequisites

- Docker Desktop running
- kind installed
- kubectl installed

## Cluster Configuration

kind config file:

- ../../SPRINGBOOT-Microservices/K8s/kind/kind-config.yaml

Create cluster:

```bash
kind create cluster --config SPRINGBOOT-Microservices/K8s/kind/kind-config.yaml --name microservices-kind
```

## Infrastructure Manifests

Manifests location:

- ../../SPRINGBOOT-Microservices/K8s/manifests/infrastructure/

Recommended apply order:

```bash
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/zookeeper.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/schema-registry.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka-mysql.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/keycloak.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/mysql.yaml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/mongodb.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/prometheus.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/loki.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/tempo.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/grafana.yml
kubectl apply -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/kafka-ui.yml
```

## Verify Deployment

```bash
kubectl get pods -n default
kubectl get svc -n default
```

Expected core services include: kafka, zookeeper, schema-registry, keycloak, mysql, mongodb, prometheus, grafana, loki, tempo, kafka-ui.

## Common Port Forwards

```bash
kubectl port-forward svc/schema-registry 18081:8081 -n default
kubectl port-forward svc/kafka-ui 18080:8080 -n default
kubectl port-forward svc/keycloak 18181:8080 -n default
kubectl port-forward svc/grafana 13000:3000 -n default
kubectl port-forward svc/prometheus 19090:9090 -n default
```

## Clean Up

```bash
kubectl delete -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/
kind delete cluster --name microservices-kind
```
