# Car Rental Management System - Build Script
# This script creates an executable JAR file

# Create build directories
$buildDir = ".\build"
$classesDir = "$buildDir\classes"
$distDir = "$buildDir\dist"

# Create directories if they don't exist
if (-not (Test-Path $buildDir)) { New-Item -Path $buildDir -ItemType Directory | Out-Null }
if (-not (Test-Path $classesDir)) { New-Item -Path $classesDir -ItemType Directory | Out-Null }
if (-not (Test-Path $distDir)) { New-Item -Path $distDir -ItemType Directory | Out-Null }

# Create manifest file
$manifestContent = @"
Manifest-Version: 1.0
Main-Class: car_rental.Main
Class-Path: lib/flatlaf-3.6.jar lib/flatlaf-extras-3.6.jar lib/fontbox-3.0.5.jar lib/jakarta.activation-1.2.2.jar lib/javax.mail.jar lib/jbcrypt-0.4.jar lib/jdatepicker-1.3.4.jar lib/miglayout-core-4.3-20121116.151612-3.jar lib/miglayout-swing-4.3-20121116.151626-3.jar lib/mysql-connector-j-9.1.0.jar lib/pdfbox-3.0.5.jar lib/pdfbox-app-3.0.5.jar

"@
$manifestContent | Out-File -FilePath "$buildDir\MANIFEST.MF" -Encoding ASCII

# Compile Java files
Write-Host "Compiling Java files..."
$libPath = ".\lib\*"
$srcPath = ".\src"

# Get all Java files recursively
$javaFiles = Get-ChildItem -Path $srcPath -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }

# Prepare javac command
$javacCmd = "javac -d `"$classesDir`" -cp `"$libPath`" $($javaFiles -join ' ')"
Write-Host "Executing: $javacCmd"

# Execute javac
Invoke-Expression $javacCmd

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Compilation successful!" -ForegroundColor Green

# Create JAR file
Write-Host "Creating JAR file..."
$jarFile = "$distDir\CarRentalSystem.jar"
jar cfm "$jarFile" "$buildDir\MANIFEST.MF" -C "$classesDir" .

if ($LASTEXITCODE -ne 0) {
    Write-Host "JAR creation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "JAR file created successfully: $jarFile" -ForegroundColor Green

# Copy resources
Write-Host "Copying resources..."
$resourceDirs = @("assets", "resources", "fonts")
foreach ($dir in $resourceDirs) {
    $sourceDir = ".\src\$dir"
    $targetDir = "$distDir\src\$dir"
    
    if (Test-Path $sourceDir) {
        if (-not (Test-Path $targetDir)) { New-Item -Path $targetDir -ItemType Directory -Force | Out-Null }
        Copy-Item -Path "$sourceDir\*" -Destination $targetDir -Recurse -Force
    }
}

# Copy libraries
Write-Host "Copying libraries..."
$libDir = "$distDir\lib"
if (-not (Test-Path $libDir)) { New-Item -Path $libDir -ItemType Directory | Out-Null }
Copy-Item -Path ".\lib\*" -Destination $libDir -Recurse -Force

# Check if Launch4j exists and create EXE file
Write-Host "Converting JAR to EXE using Launch4j..."

$launch4jPaths = @(
    "C:\Program Files\Launch4j\launch4jc.exe",
    "C:\Program Files (x86)\Launch4j\launch4jc.exe",
    "$env:LOCALAPPDATA\Launch4j\launch4jc.exe"
)

$launch4jPath = $null
foreach ($path in $launch4jPaths) {
    if (Test-Path $path) {
        $launch4jPath = $path
        break
    }
}

if ($launch4jPath -ne $null) {
    # Run Launch4j to create EXE file
    & "$launch4jPath" ".\launch4j_config.xml"
    
    if ($LASTEXITCODE -eq 0) {
        $exeFile = "$distDir\CarRentalSystem.exe"
        Write-Host "EXE file created successfully: $exeFile" -ForegroundColor Green
        Write-Host "You can now double-click the EXE file to run the application."
    } else {
        Write-Host "Failed to create EXE file." -ForegroundColor Red
        Write-Host "You can still run the JAR file with: java -jar `"$jarFile`""
    }
} else {
    Write-Host "Launch4j not found. Cannot create EXE file." -ForegroundColor Yellow
    Write-Host "Please install Launch4j from: https://sourceforge.net/projects/launch4j/"
    Write-Host "Then run: launch4jc.exe .\launch4j_config.xml"
    Write-Host "Meanwhile, you can run the JAR file with: java -jar `"$jarFile`""
}

Write-Host "Build completed successfully!" -ForegroundColor Green
