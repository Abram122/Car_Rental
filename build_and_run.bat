@echo off
echo Building Car Rental Management System...

:: Run the PowerShell script to build JAR and EXE
powershell -ExecutionPolicy Bypass -File .\build_jar.ps1

:: Check if EXE was created successfully
if exist ".\build\dist\CarRentalSystem.exe" (
    echo.
    echo Starting the application...
    echo.
    start "" ".\build\dist\CarRentalSystem.exe"
) else (
    echo.
    echo EXE file not found. Trying to run the JAR file...
    echo.
    if exist ".\build\dist\CarRentalSystem.jar" (
        java -jar ".\build\dist\CarRentalSystem.jar"
    ) else (
        echo Failed to find either EXE or JAR file.
        echo Build process may have failed.
    )
)

pause
