# Stack

For local development you can use the following tools:

- **SSO**: Keycloak
- **S3 Storage**: [Minio](https://min.io/docs/minio/linux/index.html)
- **Email Server**: [Mailhog](https://github.com/mailhog/MailHog)
- **SSO**: [Keycloak](https://www.keycloak.org/)
- **DB**: Postgresql database 

## Docker

Use docker compose to start the infrastructure components:

```bash
docker compose up -d

# with frontend
docker compose --profile tasklist-frontend up -d
```

