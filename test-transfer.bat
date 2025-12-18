@echo off
echo Testing Transfer Transaction...
echo Ensure services are running!

curl -v -X POST http://localhost:8080/transaction-service/api/transactions/transfert ^
-H "Content-Type: application/json" ^
-d "{\"compteSourceNumero\": \"FR7612345678901\", \"compteDestinationNumero\": \"FR7698765432109\", \"montant\": 100.0, \"description\": \"Test connection\"}"

echo.
echo.
pause
