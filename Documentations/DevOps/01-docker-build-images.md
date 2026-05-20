# Docker Build and Image Publish

This project uses Spring Boot buildpacks through Maven Wrapper to build and publish container images.

## Source Scripts

- Root script (Windows CMD): ../../build-images.bat
- Root script (PowerShell): ../../build-images.ps1

Both scripts build these services:

- Gateway
- Product-Service
- order-service
- inventory-service
- notification-service

## Docker Hub Account Configuration

The scripts are configured with:

- Docker username: shlokbajaj3433
- Password input: Docker personal access token passed at runtime

## Usage

### CMD

```bat
build-images.bat YOUR_DOCKER_PAT_TOKEN
```

### PowerShell

```powershell
.\build-images.ps1 -DockerPassword "YOUR_DOCKER_PAT_TOKEN"
```

## Build Command Used Internally

For each service, the scripts run this pattern:

```bash
mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="<token>"
```

## Expected Published Images

- docker.io/shlokbajaj3433/gateway:latest
- docker.io/shlokbajaj3433/product-service:latest
- docker.io/shlokbajaj3433/order-service:latest
- docker.io/shlokbajaj3433/inventory-service:latest
- docker.io/shlokbajaj3433/notification-service:latest

## Verification

After running the script:

1. Ensure build exits with success for all five services.
2. Confirm push lines appear in Maven output.
3. Validate images exist in Docker Hub repository.

## Notes

- If image is built locally but not pushed, verify service pom plugin image and publish configuration.
- Use least-privilege Docker Hub token for CI or local scripts.
