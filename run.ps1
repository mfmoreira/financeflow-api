# run.ps1 - Script to run FinanceFlow API via Docker

param (
    [ValidateSet("code","config")]
    [string]$type = "code"
)

Write-Host "===================================================="
Write-Host "FinanceFlow - Docker Automation"
Write-Host "Detected change type:" $type
Write-Host "===================================================="

# Set default environment profile
$env:SPRING_PROFILES_ACTIVE="dev"
Write-Host "Profile set: $env:SPRING_PROFILES_ACTIVE"

# Choose the correct Docker command
if ($type -eq "code") {
    Write-Host "Detected Kotlin/Gradle code changes. Running full rebuild..."
    docker-compose up --build
} else {
    Write-Host "Detected config/migration changes only. Starting containers without rebuild..."
    docker-compose up
}

Write-Host "===================================================="
Write-Host "Post-run tips:"
Write-Host "  - To stop containers: docker-compose down -v"
Write-Host "  - To access API container: docker exec -it financeflow_api /bin/sh"
Write-Host "  - To access Postgres container: docker exec -it financeflow_db psql -U postgres -d financeflow"
Write-Host "===================================================="
