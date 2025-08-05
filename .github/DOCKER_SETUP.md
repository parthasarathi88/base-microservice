# Docker Hub Credentials Setup for GitHub Actions

## Quick Setup Checklist

- [ ] Create Docker Hub Access Token
- [ ] Add DOCKER_USERNAME to GitHub Secrets
- [ ] Add DOCKER_PASSWORD to GitHub Secrets
- [ ] Test the workflow

## Detailed Instructions

### 1. Create Docker Hub Access Token (Recommended)

**Why use access tokens?**
- More secure than passwords
- Can be revoked without changing your password
- Can have limited permissions
- Can track usage

**Steps:**
1. Go to [Docker Hub](https://hub.docker.com/)
2. Sign in with your credentials
3. Click on your username (top right) → **Account Settings**
4. Navigate to **Security** tab
5. Click **New Access Token**
6. Fill in the details:
   - **Access Token Description**: `GitHub Actions CI/CD`
   - **Permissions**: Select **Read, Write, Delete**
7. Click **Generate**
8. **IMPORTANT**: Copy the token immediately (you won't see it again)

### 2. Add Secrets to GitHub Repository

**Navigate to your repository:**
```
https://github.com/parthasarathi88/base-microservice
```

**Add secrets:**
1. Go to **Settings** tab (repository settings, not profile)
2. In left sidebar: **Secrets and variables** → **Actions**
3. Click **New repository secret**

**Add DOCKER_USERNAME:**
- Name: `DOCKER_USERNAME`
- Value: `sahureb` (your Docker Hub username)
- Click **Add secret**

**Add DOCKER_PASSWORD:**
- Name: `DOCKER_PASSWORD`
- Value: `[paste your access token here]`
- Click **Add secret**

### 3. Verify Setup

After adding secrets, you should see:
```
✅ DOCKER_USERNAME
✅ DOCKER_PASSWORD
```

### 4. Test the Workflow

**Option A: Push to main branch**
```bash
git push origin main
```
This will trigger the CI/CD pipeline with Docker build.

**Option B: Create a test tag**
```bash
git tag v0.1.0
git push origin v0.1.0
```
This will trigger the release workflow.

## Security Best Practices

### ✅ Do's
- Use Docker Hub access tokens instead of passwords
- Set appropriate permissions on access tokens
- Regularly rotate access tokens
- Monitor token usage in Docker Hub

### ❌ Don'ts
- Never commit credentials to version control
- Don't share access tokens
- Don't use overly broad permissions
- Don't use the same token for multiple purposes

## Troubleshooting

### Common Issues

**1. "Authentication failed" error:**
- Verify username and token are correct
- Check if token has expired
- Ensure token has push permissions

**2. "Repository not found" error:**
- Verify Docker Hub repository exists
- Check repository name in pom.xml matches Docker Hub
- Ensure you have push permissions to the repository

**3. Workflow not triggered:**
- Check if secrets are properly named (case-sensitive)
- Verify workflow file syntax
- Check branch protection rules

### Debug Steps

1. **Check secrets are set:**
   - Go to repository Settings → Secrets and variables → Actions
   - Verify both secrets exist

2. **Check workflow logs:**
   - Go to Actions tab in GitHub
   - Click on failed workflow
   - Expand the Docker login step to see error details

3. **Test Docker Hub connection:**
   ```bash
   docker login -u your-username -p your-token
   ```

## Docker Hub Repository Setup

Make sure your Docker Hub repository exists:
1. Go to [Docker Hub](https://hub.docker.com/)
2. Navigate to **Repositories**
3. Verify `k8s-base-microservice` repository exists
4. If not, create it: **Create Repository** → **k8s-base-microservice**

## Environment Variables Reference

The workflows use these environment variables:
```yaml
env:
  DOCKER_USERNAME: ${{ secrets.DOCKER_USERNAME }}
  DOCKER_PASSWORD: ${{ secrets.DOCKER_PASSWORD }}
```

These are passed to:
- Docker login action
- Jib Maven plugin for authentication
