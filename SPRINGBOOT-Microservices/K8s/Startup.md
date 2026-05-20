
What Kubernetes is doing here:
1. Runs messaging stack: Zookeeper, Kafka, Schema Registry, Kafka UI.
2. Runs security stack: Keycloak (with its own MySQL instance for Keycloak data).
3. Runs data services: MySQL and MongoDB.
4. Runs observability stack: Prometheus, Loki, Tempo, Grafana.
5. Provides in-cluster service discovery so containers talk via service names like kafka, schema-registry, mysql, mongodb.

You can see this in:
- 02-kubernetes-deployment.md
- kafka.yml
- keycloak.yml
- grafana.yml

How to run Kubernetes setup (Windows):
1. Start Docker Desktop.
2. Install and verify tools:
   1. kind --version
   2. kubectl version --client
3. From workspace root, create cluster:
```powershell
kind create cluster --config SPRINGBOOT-Microservices/K8s/kind/kind-config.yaml --name microservices-kind
```
4. Apply infrastructure manifests in order:
```powershell
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
5. Verify all pods/services:
```powershell
kubectl get pods -n default
kubectl get svc -n default
```
6. Open services via port-forward:
```powershell
kubectl port-forward svc/schema-registry 18081:8081 -n default
kubectl port-forward svc/kafka-ui 18080:8080 -n default
kubectl port-forward svc/keycloak 18181:8080 -n default
kubectl port-forward svc/grafana 13000:3000 -n default
kubectl port-forward svc/prometheus 19090:9090 -n default
```
7. Access:
   1. Kafka UI: http://localhost:18080
   2. Schema Registry: http://localhost:18081/subjects
   3. Keycloak: http://localhost:18181
   4. Grafana: http://localhost:13000
   5. Prometheus: http://localhost:19090

Important note for this repo:
- Kafka and Schema Registry manifests already include enableServiceLinks: false, which avoids a known CrashLoop issue in this setup.

Cleanup:
```powershell
kubectl delete -f SPRINGBOOT-Microservices/K8s/manifests/infrastructure/
kind delete cluster --name microservices-kind
```
  