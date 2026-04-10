@echo off
REM Docker Build Script for Spring Boot Microservices
REM Usage: build-images.bat [password]

setlocal enabledelayedexpansion

set DOCKER_USERNAME=shlokbajaj3433
set DOCKER_PASSWORD=%1

if "%DOCKER_PASSWORD%"=="" (
    echo Usage: build-images.bat ^<docker_pat_token^>
    echo Example: build-images.bat YOUR_DOCKER_PAT_TOKEN
    exit /b 1
)

set PROJECT_ROOT=%~dp0SPRINGBOOT-Microservices
cd /d "%PROJECT_ROOT%"

echo.
echo ========================================
echo Docker Image Build for Microservices
echo ========================================
echo Username: %DOCKER_USERNAME%
echo.

REM Build Gateway
echo [1/5] Building Gateway...
cd /d "%PROJECT_ROOT%\Gateway"
call mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="%DOCKER_PASSWORD%"
if %errorlevel% neq 0 (
    echo Failed to build Gateway
    exit /b 1
)

REM Build Product-Service
echo.
echo [2/5] Building Product-Service...
cd /d "%PROJECT_ROOT%\Product-Service"
call mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="%DOCKER_PASSWORD%"
if %errorlevel% neq 0 (
    echo Failed to build Product-Service
    exit /b 1
)

REM Build order-service
echo.
echo [3/5] Building order-service...
cd /d "%PROJECT_ROOT%\order-service"
call mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="%DOCKER_PASSWORD%"
if %errorlevel% neq 0 (
    echo Failed to build order-service
    exit /b 1
)

REM Build inventory-service
echo.
echo [4/5] Building inventory-service...
cd /d "%PROJECT_ROOT%\inventory-service"
call mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="%DOCKER_PASSWORD%"
if %errorlevel% neq 0 (
    echo Failed to build inventory-service
    exit /b 1
)

REM Build notification-service
echo.
echo [5/5] Building notification-service...
cd /d "%PROJECT_ROOT%\notification-service"
call mvnw.cmd clean spring-boot:build-image -DskipTests -Ddocker.password="%DOCKER_PASSWORD%"
if %errorlevel% neq 0 (
    echo Failed to build notification-service
    exit /b 1
)

echo.
echo ========================================
echo All images built successfully!
echo ========================================
echo.
echo Images pushed to Docker Hub:
echo  - docker.io/%DOCKER_USERNAME%/gateway:latest
echo  - docker.io/%DOCKER_USERNAME%/product-service:latest
echo  - docker.io/%DOCKER_USERNAME%/order-service:latest
echo  - docker.io/%DOCKER_USERNAME%/inventory-service:latest
echo  - docker.io/%DOCKER_USERNAME%/notification-service:latest
echo.

cd /d "%PROJECT_ROOT%"

