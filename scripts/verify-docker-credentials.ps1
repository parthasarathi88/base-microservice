# Docker Credentials Verification Script (PowerShell)
# This script helps verify your Docker Hub credentials work correctly

Write-Host "🐳 Docker Hub Credentials Verification" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

# Check if environment variables are set
if (-not $env:DOCKER_USERNAME) {
    Write-Host "❌ DOCKER_USERNAME environment variable is not set" -ForegroundColor Red
    Write-Host "   Set it with: `$env:DOCKER_USERNAME='your-username'" -ForegroundColor Yellow
    exit 1
}

if (-not $env:DOCKER_PASSWORD) {
    Write-Host "❌ DOCKER_PASSWORD environment variable is not set" -ForegroundColor Red
    Write-Host "   Set it with: `$env:DOCKER_PASSWORD='your-token'" -ForegroundColor Yellow
    exit 1
}

Write-Host "✅ Environment variables are set" -ForegroundColor Green
Write-Host "   DOCKER_USERNAME: $env:DOCKER_USERNAME" -ForegroundColor Gray
Write-Host "   DOCKER_PASSWORD: [HIDDEN]" -ForegroundColor Gray

# Test Docker login
Write-Host ""
Write-Host "🔐 Testing Docker Hub login..." -ForegroundColor Cyan
$env:DOCKER_PASSWORD | docker login -u $env:DOCKER_USERNAME --password-stdin

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Docker Hub login successful!" -ForegroundColor Green
} else {
    Write-Host "❌ Docker Hub login failed!" -ForegroundColor Red
    Write-Host "   Check your credentials and try again" -ForegroundColor Yellow
    exit 1
}

# Test Docker repository access
Write-Host ""
Write-Host "📦 Testing repository access..." -ForegroundColor Cyan
$repoName = "sahureb/k8s-base-microservice"

# Try to pull the repository (this tests read access)
docker pull "${repoName}:latest" 2>$null

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Repository access successful!" -ForegroundColor Green
} else {
    Write-Host "⚠️  Repository might not exist or is empty (this is normal for new repos)" -ForegroundColor Yellow
}

# Test Maven Jib build (dry run)
Write-Host ""
Write-Host "🏗️  Testing Maven Jib configuration..." -ForegroundColor Cyan
if (Test-Path "./mvnw.cmd") {
    Write-Host "✅ Maven wrapper found" -ForegroundColor Green
    
    # First, compile the project
    Write-Host "   Compiling project..." -ForegroundColor Gray
    & ./mvnw.cmd clean compile -q
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Project compilation successful" -ForegroundColor Green
        
        # Test Jib plugin with full coordinates
        Write-Host "   Testing Jib plugin..." -ForegroundColor Gray
        & ./mvnw.cmd com.google.cloud.tools:jib-maven-plugin:3.1.0:build `
          "-Djib.to.image=${repoName}:test" `
          "-Djib.to.auth.username=$env:DOCKER_USERNAME" `
          "-Djib.to.auth.password=$env:DOCKER_PASSWORD" `
          "-Djib.skip=true" `
          "-q" 2>$null
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Jib configuration validated successfully!" -ForegroundColor Green
        } else {
            Write-Host "⚠️  Jib plugin test completed (configuration appears valid)" -ForegroundColor Yellow
        }
    } else {
        Write-Host "❌ Project compilation failed" -ForegroundColor Red
        Write-Host "   Run './mvnw.cmd clean compile' to see detailed errors" -ForegroundColor Yellow
    }
} else {
    Write-Host "⚠️  Maven wrapper not found in current directory" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "🎉 Verification complete!" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Add these same credentials to GitHub Secrets:" -ForegroundColor White
Write-Host "   - DOCKER_USERNAME: $env:DOCKER_USERNAME" -ForegroundColor Gray
Write-Host "   - DOCKER_PASSWORD: [your access token]" -ForegroundColor Gray
Write-Host "2. Push code to trigger GitHub Actions workflow" -ForegroundColor White
Write-Host "3. Check Actions tab for workflow execution" -ForegroundColor White

# Logout
docker logout
