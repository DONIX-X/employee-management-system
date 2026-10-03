$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $root

function Find-Jdk {
    $candidates = @(
        "$env:ProgramFiles\ojdkbuild\java-17-openjdk*",
        "$env:ProgramFiles\Java\jdk*",
        "$env:ProgramFiles\Microsoft\jdk*",
        "$env:ProgramFiles\BellSoft\LibericaJDK*",
        "$env:ProgramFiles\Zulu\zulu-*"
    )

    foreach ($pattern in $candidates) {
        $matches = Get-ChildItem -Path $pattern -ErrorAction SilentlyContinue | Sort-Object LastWriteTime -Descending
        if ($null -ne $matches) {
            foreach ($dir in $matches) {
                $javac = Join-Path $dir.FullName 'bin\javac.exe'
                if (Test-Path $javac) {
                    return @($dir.FullName, $javac)
                }
            }
        }
    }

    return $null
}

$jdkInfo = Find-Jdk
if (-not $jdkInfo) {
    throw "JDK 17+ was not found. Install a Java Development Kit and run this script again."
}

$JAVA_HOME = $jdkInfo[0]
$JAVAC = $jdkInfo[1]
$env:JAVA_HOME = $JAVA_HOME
$env:Path = "$JAVA_HOME\bin;$env:Path"

if (-not $env:EMS_DB_HOST) { $env:EMS_DB_HOST = 'localhost' }
if (-not $env:EMS_DB_PORT) { $env:EMS_DB_PORT = '3306' }
if (-not $env:EMS_DB_NAME) { $env:EMS_DB_NAME = 'employee_management' }
if (-not $env:EMS_DB_USER) { $env:EMS_DB_USER = 'root' }
if (-not $env:EMS_DB_PASSWORD) {
    Write-Host "No MySQL password configured. Starting in offline demo mode."
    $env:EMS_DB_PASSWORD = ''
}

$jar = Join-Path $root 'lib\mysql-connector-j-26.7.0.jar'
if (-not (Test-Path $jar)) {
    throw "MySQL Connector/J was not found at $jar. Add the JAR to the lib folder."
}

$outDir = Join-Path $root 'out'
New-Item -ItemType Directory -Path $outDir -Force | Out-Null

$sources = Get-ChildItem $root -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
& $JAVAC -cp $jar -d $outDir $sources
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

$mainJarClassPath = "out;$jar"
& (Join-Path $JAVA_HOME 'bin\java.exe') -cp $mainJarClassPath Main
