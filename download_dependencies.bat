@echo off
chcp 65001 >nul
echo Creating lib directory...
if not exist lib mkdir lib

echo Downloading Gson...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar' -OutFile 'lib\gson-2.10.1.jar'"

echo Downloading PostgreSQL JDBC Driver...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/postgresql/postgresql/42.6.0/postgresql-42.6.0.jar' -OutFile 'lib\postgresql-42.6.0.jar'"

if exist lib\gson-2.10.1.jar (
    echo Gson downloaded successfully!
) else (
    echo Failed to download Gson!
    exit /b 1
)

if exist lib\postgresql-42.6.0.jar (
    echo PostgreSQL JDBC Driver downloaded successfully!
) else (
    echo Failed to download PostgreSQL JDBC Driver!
    exit /b 1
)

echo All dependencies downloaded successfully! 