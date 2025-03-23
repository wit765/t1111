@echo off
chcp 65001 >nul
echo Checking Java version...
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java not found. Please install JDK 17 or higher.
    exit /b 1
)

:: Check if lib directory exists
if not exist lib (
    echo Error: lib directory not found. Please run download_dependencies.bat first.
    exit /b 1
)

:: Check if dependencies exist
if not exist lib\gson-2.10.1.jar (
    echo Error: Gson dependency not found. Please run download_dependencies.bat first.
    exit /b 1
)

if not exist lib\postgresql-42.6.0.jar (
    echo Error: PostgreSQL JDBC Driver not found. Please run download_dependencies.bat first.
    exit /b 1
)

:: Get absolute path of current directory
set "CURRENT_DIR=%~dp0"
set "GSON_PATH=%CURRENT_DIR%lib\gson-2.10.1.jar"
set "POSTGRES_PATH=%CURRENT_DIR%lib\postgresql-42.6.0.jar"

:: Delete all .class files
echo Cleaning old class files...
del /Q *.class 2>nul
if errorlevel 1 (
    echo No old class files found.
) else (
    echo Old class files deleted.
)

:: Compile Java files
echo Compiling Java files...
javac -encoding UTF-8 -cp "%GSON_PATH%;%POSTGRES_PATH%" *.java
if errorlevel 1 (
    echo Compilation failed!
    exit /b 1
) else (
    echo Compilation successful!
)

:: Run the application
echo Starting server...
java -cp ".;%GSON_PATH%;%POSTGRES_PATH%" UserApiServer
if errorlevel 1 (
    echo Server startup failed!
    exit /b 1
) 