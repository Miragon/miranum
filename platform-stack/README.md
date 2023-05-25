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

### Postgres

To use postgres as database, enable the postgres config in .env and start the following script:

```bash
chmod +x platform-stack/postgres/init.sh
docker compose --profile postgres up -d
```

### MSSQL

```bash
# with mssql
docker compose --profile mssql up -d
```