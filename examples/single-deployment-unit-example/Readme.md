# Miranum Single Deployment Unit Example

> This examples shows how to use Miranum components to create a miranum-platform as a single spring boot application (single deployment unit).

![overview](../../../images/Miranum-Platform-Single-Deployment.png)

## Getting started

1. Run the infrastructure components (stack)
```bash
cd ../stack && docker compose up -d
```
2. Build the project
3. Run the application `MiranumPlatformSDU`

**Example Users**

| User   | Password | Role    |
|--------|----------|---------|
| alex   | test     | admin   |
| oliver | test     | office  |
| olga   | test     | office  |
