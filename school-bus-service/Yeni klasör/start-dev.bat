@echo off
echo Starting School Bus Service in Development Mode...
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/download.cgi
    echo and add it to your PATH environment variable
    pause
    exit /b 1
)

REM Check if PostgreSQL is running
echo Checking if PostgreSQL is running...
netstat -an | findstr :5432 >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo WARNING: PostgreSQL might not be running on port 5432
    echo Please make sure PostgreSQL is installed and running
    echo.
)

REM Set development profile
set SPRING_PROFILES_ACTIVE=dev

echo Starting the application with development profile...
echo You can access:
echo - Application: http://localhost:8080/api
echo - Swagger UI: http://localhost:8080/api/swagger-ui.html
echo - API Docs: http://localhost:8080/api/api-docs
echo.

mvn spring-boot:run

pause