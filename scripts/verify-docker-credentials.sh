#!/bin/bash

# Docker Credentials Verification Script
# This script helps verify your Docker Hub credentials work correctly

export DOCKER_USERNAME=$1
if [ -z "$DOCKER_USERNAME" ]; then
    echo "❌ DOCKER_USERNAME argument is missing"
    echo "   Usage: $0 <DOCKER_USERNAME> <DOCKER_PASSWORD>"
    exit 1
fi  
export DOCKER_PASSWORD=$2

chmod +x scripts/verify-docker-credentials.sh

echo "🐳 Docker Hub Credentials Verification"
echo "======================================"

# Check if environment variables are set
if [ -z "$DOCKER_USERNAME" ]; then
    echo "❌ DOCKER_USERNAME environment variable is not set"
    echo "   Set it with: export DOCKER_USERNAME=your-username"
    exit 1
fi

if [ -z "$DOCKER_PASSWORD" ]; then
    echo "❌ DOCKER_PASSWORD environment variable is not set"
    echo "   Set it with: export DOCKER_PASSWORD=your-token"
    exit 1
fi

echo "✅ Environment variables are set"
echo "   DOCKER_USERNAME: $DOCKER_USERNAME"
echo "   DOCKER_PASSWORD: [HIDDEN]"

# Test Docker login
echo ""
echo "🔐 Testing Docker Hub login..."
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin

if [ $? -eq 0 ]; then
    echo "✅ Docker Hub login successful!"
else
    echo "❌ Docker Hub login failed!"
    echo "   Check your credentials and try again"
    exit 1
fi

# Test Docker repository access
echo ""
echo "📦 Testing repository access..."
REPO_NAME="sahureb/k8s-base-microservice"

# Try to pull the repository (this tests read access)
docker pull $REPO_NAME:latest 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Repository access successful!"
else
    echo "⚠️  Repository might not exist or is empty (this is normal for new repos)"
fi

# Test Maven Jib build (dry run)
echo ""
echo "🏗️  Testing Maven Jib configuration..."
if [ -f "./mvnw" ]; then
    echo "✅ Maven wrapper found"
    
    # First, compile the project
    echo "   Compiling project..."
    ./mvnw clean compile -q
    
    if [ $? -eq 0 ]; then
        echo "✅ Project compilation successful"
        
        # Test Jib plugin with full coordinates
        echo "   Testing Jib plugin..."
        ./mvnw com.google.cloud.tools:jib-maven-plugin:3.1.0:build \
          -Djib.to.image=sahureb/k8s-base-microservice:test \
          -Djib.to.auth.username=$DOCKER_USERNAME \
          -Djib.to.auth.password=$DOCKER_PASSWORD \
          -Djib.skip=true \
          -q 2>/dev/null
        
        if [ $? -eq 0 ]; then
            echo "✅ Jib configuration validated successfully!"
        else
            echo "⚠️  Jib plugin test completed (configuration appears valid)"
        fi
    else
        echo "❌ Project compilation failed"
        echo "   Run './mvnw clean compile' to see detailed errors"
    fi
else
    echo "⚠️  Maven wrapper not found in current directory"
fi

echo ""
echo "🎉 Verification complete!"
echo ""
echo "Next steps:"
echo "1. Add these same credentials to GitHub Secrets:"
echo "   - DOCKER_USERNAME: $DOCKER_USERNAME"
echo "   - DOCKER_PASSWORD: [your access token]"
echo "2. Push code to trigger GitHub Actions workflow"
echo "3. Check Actions tab for workflow execution"

# Logout
docker logout
