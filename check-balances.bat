@echo off
echo ========================================================
echo CHECKING ACCOUNT BALANCES
echo ========================================================

echo.
echo [1/2] Checking Alice's Account (FR7612345678901)...
curl.exe -s -X GET "http://localhost:8080/api/comptes/search/byNumero?numeroCompte=FR7612345678901"
echo.

echo.
echo [2/2] Checking Bob's Account (FR7698765432109)...
curl.exe -s -X GET "http://localhost:8080/api/comptes/search/byNumero?numeroCompte=FR7698765432109"
echo.

echo.
echo ========================================================
echo If the transfer worked, Alice should have less, Bob more.
echo ========================================================
pause
