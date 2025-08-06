@echo off
echo Running School Bus Service Tests...
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

echo Running unit tests...
mvn test

echo.
echo Test execution completed!
echo Check the results above for any failures.

pause