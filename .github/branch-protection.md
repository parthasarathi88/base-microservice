# GitHub Actions Configuration Guide

## Docker Credentials Setup

### Step 1: Add Docker Hub Secrets to GitHub Repository

1. **Navigate to your GitHub repository**: https://github.com/parthasarathi88/base-microservice
2. **Go to Settings tab** (in the repository, not your profile)
3. **Click on "Secrets and variables"** in the left sidebar
4. **Click on "Actions"**
5. **Click "New repository secret"** button

### Step 2: Add DOCKER_USERNAME Secret

1. **Name**: `DOCKER_USERNAME`
2. **Value**: Your Docker Hub username 
3. **Click "Add secret"**

### Step 3: Add DOCKER_PASSWORD Secret

1. **Click "New repository secret"** again
2. **Name**: `DOCKER_PASSWORD`
3. **Value**: Your Docker Hub password or access token (recommended)
4. **Click "Add secret"**

### Step 4: Verify Secrets

After adding both secrets, you should see:
- ✅ DOCKER_USERNAME
- ✅ DOCKER_PASSWORD

## Creating Docker Hub Access Token (Recommended)

Instead of using your password, create an access token:

1. **Go to Docker Hub**: https://hub.docker.com/
2. **Sign in** to your account
3. **Click on your username** → **Account Settings**
4. **Go to "Security" tab**
5. **Click "New Access Token"**
6. **Enter description**: "GitHub Actions CI/CD"
7. **Select permissions**: Read, Write, Delete
8. **Click "Generate"**
9. **Copy the token** (you won't see it again)
10. **Use this token as DOCKER_PASSWORD** in GitHub secrets

## GitHub Actions Status Checks Configuration

### Required status checks that must pass before merging:
- Build / build
- CI/CD Pipeline / test  
- CI/CD Pipeline / security-scan

### Optional status checks:
- CI/CD Pipeline / build-docker (only on main branch)

## Branch Protection Setup

### To configure these in GitHub:
1. Go to Settings > Branches
2. Add branch protection rule for main/develop
3. Enable "Require status checks to pass before merging"
4. Select the required checks from above
