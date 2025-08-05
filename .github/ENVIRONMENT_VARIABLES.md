# Environment Variables Configuration

This document explains how to configure environment variables for Docker operations in different environments.

## GitHub Actions (Current Setup)

The workflows are configured to use GitHub Secrets that are exposed as environment variables:

```yaml
env:
  DOCKER_USERNAME: ${{ secrets.DOCKER_USERNAME }}
  DOCKER_PASSWORD: ${{ secrets.DOCKER_PASSWORD }}
```

## Local Development

For local development, you can set these environment variables:

### Windows (PowerShell)
```powershell
$env:DOCKER_USERNAME="your-docker-username"
$env:DOCKER_PASSWORD="your-docker-password"
./mvnw jib:build
```

### Linux/macOS (Bash)
```bash
export DOCKER_USERNAME=your-docker-username
export DOCKER_PASSWORD=your-docker-password
./mvnw jib:build
```

### Using .env file (recommended for local development)
Create a `.env` file in the project root:
```bash
DOCKER_USERNAME=your-docker-username
DOCKER_PASSWORD=your-docker-password
```

Then source it:
```bash
# Linux/macOS
source .env
./mvnw jib:build

# Windows PowerShell
Get-Content .env | ForEach-Object { 
  $name, $value = $_.split('=', 2)
  Set-Item -Path "env:$name" -Value $value
}
./mvnw jib:build
```

## Docker Login Alternative

You can also login to Docker Hub first, then run the build:
```bash
docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
./mvnw jib:build
```

## Maven Configuration

The Jib plugin is configured to use environment variables:
```bash
./mvnw jib:build \
  -Djib.to.auth.username=$DOCKER_USERNAME \
  -Djib.to.auth.password=$DOCKER_PASSWORD
```

## Security Best Practices

1. **Never commit credentials** to version control
2. **Use GitHub Secrets** for CI/CD pipelines
3. **Use .env files** for local development (add to .gitignore)
4. **Use Docker credential helpers** when possible
5. **Rotate passwords** regularly

## Troubleshooting

If you get authentication errors:
1. Verify environment variables are set: `echo $DOCKER_USERNAME`
2. Check Docker Hub credentials are correct
3. Ensure the Docker repository exists and you have push permissions
4. Try manual Docker login: `docker login`
