# Car Rental Management System - Installer Creation Script

# Check for Inno Setup
$innoSetupPaths = @(
    "C:\Program Files (x86)\Inno Setup 6\ISCC.exe",
    "C:\Program Files\Inno Setup 6\ISCC.exe"
)

$innoSetupPath = $null
foreach ($path in $innoSetupPaths) {
    if (Test-Path $path) {
        $innoSetupPath = $path
        break
    }
}

if ($innoSetupPath -eq $null) {
    Write-Host "Inno Setup not found. Please install Inno Setup 6 from:" -ForegroundColor Red
    Write-Host "https://jrsoftware.org/isdl.php" -ForegroundColor Yellow
    Write-Host "Then run this script again." -ForegroundColor Yellow
    exit 1
}

# First build the JAR and EXE
Write-Host "Building JAR and EXE files..." -ForegroundColor Cyan
.\build_jar.ps1

if (-not (Test-Path ".\build\dist\CarRentalSystem.exe")) {
    Write-Host "EXE file not found. Cannot create installer." -ForegroundColor Red
    exit 1
}

# Create Inno Setup script
$innoScriptPath = ".\build\installer_script.iss"
$innoScriptContent = @"
#define MyAppName "Car Rental Management System"
#define MyAppVersion "1.0"
#define MyAppPublisher "SUT Team"
#define MyAppURL "https://github.com/Abram122/Car_Rental"
#define MyAppExeName "CarRentalSystem.exe"

[Setup]
AppId={{FCEF723A-2451-4453-8CE9-06C51F36E823}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={autopf}\{#MyAppName}
DefaultGroupName={#MyAppName}
DisableProgramGroupPage=yes
LicenseFile=..\README.md
OutputDir=.\build\installer
OutputBaseFilename=CarRentalSystem_Setup
Compression=lzma
SolidCompression=yes
WizardStyle=modern
SetupIconFile=.\src\assets\logo.ico

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: ".\build\dist\{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion
Source: ".\build\dist\CarRentalSystem.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: ".\build\dist\lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: ".\build\dist\src\*"; DestDir: "{app}\src"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: ".\README.md"; DestDir: "{app}"; Flags: ignoreversion
Source: ".\admin_passwords.txt"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{autodesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent
"@

# Create output directory
$installerDir = ".\build\installer"
if (-not (Test-Path $installerDir)) {
    New-Item -Path $installerDir -ItemType Directory | Out-Null
}

# Create the ico file from png if needed
$iconFile = ".\src\assets\logo.ico"
if (-not (Test-Path $iconFile)) {
    Write-Host "Creating icon file from PNG..." -ForegroundColor Cyan
    Write-Host "Note: For a proper icon, you should convert src\assets\logo.png to .ico using an image editor" -ForegroundColor Yellow
    
    # For now just copy the png as ico (this won't work properly, but prevents script failure)
    Copy-Item ".\src\assets\logo.png" $iconFile -Force
}

# Write the Inno Setup script
$innoScriptContent | Out-File -FilePath $innoScriptPath -Encoding utf8

# Run Inno Setup Compiler
Write-Host "Creating installer package..." -ForegroundColor Cyan
& "$innoSetupPath" "$innoScriptPath" /Q

if ($LASTEXITCODE -eq 0) {
    $installerPath = "$installerDir\CarRentalSystem_Setup.exe"
    Write-Host "Installer created successfully: $installerPath" -ForegroundColor Green
    Write-Host "You can distribute this installer to your users." -ForegroundColor Green
} else {
    Write-Host "Failed to create installer." -ForegroundColor Red
}
