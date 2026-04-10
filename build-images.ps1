# Docker Build Script for Spring Boot Microservices
# Usage: .\build-images.ps1 -DockerPassword "YOUR_PAT_TOKEN"

param(
    [Parameter(Mandatory=$true)]
    [string]$DockerPassword
)

$ErrorActionPreference = "Stop"

$DOCKER_USERNAME = "shlokbajaj3433"
$PROJECT_ROOT = "$PSScriptRoot\SPRINGBOOT-Microservices"

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Docker Image Build for Microservices" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Username: $DOCKER_USERNAME" -ForegroundColor Yellow
Write-Host ""

$services = @(
    @{ name = "Gateway"; path = "$PROJECT_ROOT\Gateway" },
    @{ name = "Product-Service"; path = "$PROJECT_ROOT\Product-Service" },
    @{ name = "order-service"; path = "$PROJECT_ROOT\order-service" },
    @{ name = "inventory-service"; path = "$PROJECT_ROOT\inventory-service" },
    @{ name = "notification-service"; path = "$PROJECT_ROOT\notification-service" }
)

$count = 1
foreach ($service in $services) {
    Write-Host "[$count/$($services.Count)] Building $($service.name)..." -ForegroundColor Green

    Push-Location $service.path
    try {
        & cmd /c "mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password=`"$DockerPassword`""
        if ($LASTEXITCODE -ne 0) {
            Write-Host "Failed to build $($service.name)" -ForegroundColor Red
            exit 1
        }
    } finally {
        Pop-Location
    }

    Write-Host ""
    $count++
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "All images built successfully!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Images pushed to Docker Hub:" -ForegroundColor Yellow
Write-Host "  - docker.io/$DOCKER_USERNAME/gateway:latest"
Write-Host "  - docker.io/$DOCKER_USERNAME/product-service:latest"
Write-Host "  - docker.io/$DOCKER_USERNAME/order-service:latest"
Write-Host "  - docker.io/$DOCKER_USERNAME/inventory-service:latest"
Write-Host "  - docker.io/$DOCKER_USERNAME/notification-service:latest"
Write-Host ""

