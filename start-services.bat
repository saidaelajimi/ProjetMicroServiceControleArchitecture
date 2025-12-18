@echo off
echo Starting Eureka Server...
start "Eureka Server" cmd /c "mvn -f eureka-server/pom.xml spring-boot:run"
timeout /t 10

echo Starting Compte Service...
start "Compte Service" cmd /c "mvn -f compte-service/pom.xml spring-boot:run"

echo Starting Transaction Service...
start "Transaction Service" cmd /c "mvn -f transaction-service/pom.xml spring-boot:run"

echo Starting Reporting Service...
start "Reporting Service" cmd /c "mvn -f reporting-service/pom.xml spring-boot:run"

echo Starting API Gateway...
start "API Gateway" cmd /c "mvn -f api-gateway/pom.xml spring-boot:run"


echo All services attempted to start. Please check the new windows for logs.
