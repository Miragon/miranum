# Stack

For local development you can use the following tools:

- **SSO**: Keycloak
- **SSO**: [Keycloak](https://www.keycloak.org/)
- **DB**: Postgresql database and mssql

## Postgres Setup
````
chmod +x platform-stack/postgres/init.sh
````

## Docker

Use docker compose to start the infrastructure components:

```bash
docker compose up -d

# with frontend
docker compose --profile tasklist-frontend up -d
```

