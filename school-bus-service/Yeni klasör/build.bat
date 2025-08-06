@echo off
echo Building School Bus Service...
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

echo Cleaning and compiling the project...
mvn clean compile

if %ERRORLEVEL% equ 0 (
    echo.
    echo Build successful!
    echo Running tests...
    mvn test
    
    if %ERRORLEVEL% equ 0 (
        echo.
        echo All tests passed!
        echo Creating the final JAR...
        mvn package -DskipTests
        
        if %ERRORLEVEL% equ 0 (
            echo.
            echo ================================================
            echo BUILD SUCCESSFUL!
            echo ================================================
            echo JAR file created: target\school-bus-service-1.0.0.jar
            echo.
            echo To run the application:
            echo java -jar target\school-bus-service-1.0.0.jar
            echo.
        ) else (
            echo Package creation failed!
        )
    ) else (
        echo Tests failed! Please fix the failing tests before packaging.
    )
) else (
    echo Compilation failed! Please fix the compilation errors.
)

pause