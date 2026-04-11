# Kubernetes Deployment Guide

This guide explains how to run the microservices stack on the local Kubernetes cluster.

## Prerequisites

- Docker Desktop running
- kind installed
- kubectl installed

## Create Cluster

```bash
kind create cluster --config K8s/kind/kind-config.yaml --name microservices-kind
```

## Deploy Infrastructure

Apply core infrastructure manifests first:

```bash
kubectl apply -f K8s/manifests/infrastructure/zookeeper.yml
kubectl apply -f K8s/manifests/infrastructure/kafka.yml
kubectl apply -f K8s/manifests/infrastructure/schema-registry.yml
kubectl apply -f K8s/manifests/infrastructure/mysql.yaml
kubectl apply -f K8s/manifests/infrastructure/mongodb.yml
kubectl apply -f K8s/manifests/infrastructure/keycloak.yml
kubectl apply -f K8s/manifests/infrastructure/prometheus.yml
kubectl apply -f K8s/manifests/infrastructure/loki.yml
kubectl apply -f K8s/manifests/infrastructure/tempo.yml
kubectl apply -f K8s/manifests/infrastructure/grafana.yml
kubectl apply -f K8s/manifests/infrastructure/kafka-ui.yml
```

## Verify Health

```bash
kubectl get pods -n default
kubectl get svc -n default
```

Wait until all required pods are in `Running` state.

## Useful Port Forwards

```bash
kubectl port-forward svc/schema-registry 18081:8081 -n default
kubectl port-forward svc/kafka-ui 18080:8080 -n default
kubectl port-forward svc/keycloak 18181:8080 -n default
kubectl port-forward svc/grafana 13000:3000 -n default
kubectl port-forward svc/prometheus 19090:9090 -n default
```

## Troubleshooting

- If `kafka` or `schema-registry` is restarting, check logs:

```bash
kubectl logs deploy/kafka -n default --tail=100
kubectl logs deploy/schema-registry -n default --tail=100
```

- If you see immediate exit with only deprecation logs, ensure `enableServiceLinks: false` is present in Kafka and Schema Registry deployments.

## Clean Up

```bash
kubectl delete -f K8s/manifests/infrastructure/
kind delete cluster --name microservices-kind
```
