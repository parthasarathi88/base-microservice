# GitHub Actions CI/CD Setup

This repository includes several GitHub Actions workflows for continuous integration and deployment.

## Workflows

### 1. **Build** (`.github/workflows/build.yml`)
- **Triggers**: Push/PR to any branch
- **Purpose**: Basic build and test validation
- **Steps**:
  - Checkout code
  - Set up JDK 17
  - Cache Maven dependencies
  - Run tests
  - Build JAR
  - Upload artifacts (main branch only)

### 2. **CI/CD Pipeline** (`.github/workflows/ci-cd.yml`)
- **Triggers**: Push/PR to main, develop, feature/* branches
- **Purpose**: Comprehensive testing, building, and Docker image creation
- **Jobs**:
  - **test**: Run tests and build
  - **build-docker**: Create Docker image (main branch only)
  - **security-scan**: Vulnerability scanning

### 3. **Release** (`.github/workflows/release.yml`)
- **Triggers**: Tag push (v*)
- **Purpose**: Create GitHub releases and tagged Docker images
- **Steps**:
  - Build application
  - Create GitHub release with JAR
  - Build and push tagged Docker image

### 4. **Validate Maven Wrapper** (`.github/workflows/validate-wrapper.yml`)
- **Triggers**: Every push/PR
- **Purpose**: Ensure Maven wrapper is properly configured

## Required Secrets

To enable Docker Hub integration, add these secrets in GitHub repository settings:

- `DOCKER_USERNAME`: Your Docker Hub username
- `DOCKER_PASSWORD`: Your Docker Hub password or access token

## Branch Protection

Recommended branch protection rules for `main` branch:
- Require status checks: "Build / build", "CI/CD Pipeline / test"
- Require branches to be up to date before merging
- Restrict pushes to main branch

## Usage

1. **Regular Development**: 
   - Push to feature branches triggers build and test
   - Create PR to main/develop for full CI/CD pipeline

2. **Docker Images**:
   - Push to main branch creates `latest` Docker image
   - Create tag (e.g., `v1.0.0`) creates versioned Docker image

3. **Releases**:
   - Create and push a tag: `git tag v1.0.0 && git push origin v1.0.0`
   - GitHub release with JAR artifact is created automatically

## Local Testing

Test the build locally before pushing:
```bash
./mvnw clean test
./mvnw clean package
```

## Docker Image

The workflows build Docker images using Jib plugin:
- Repository: `sahureb/k8s-base-microservice`
- Tags: `latest` (main branch), version tags (releases)
