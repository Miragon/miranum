# Stack

For local development you can use the following tools:

- **SSO**: [Keycloak](https://www.keycloak.org/)
- **DB**: [PostgreSQL](https://www.postgresql.org/)
- **Minio**: [Minio](https://min.io/)

IMPORTANT NOTE: You must have keycloak configured in your hosts-file to run the sack locally!
On Mac/Linux it is located in `/etc/hosts` on Win `C:\Windows\System32\drivers\etc\hosts`:
```bash
127.0.0.1 localhost keycloak
```

## Docker
Use docker compose to start the infrastructure components:

```bash
docker compose --profile engine up -d
```

### Smoke Test
The tasklist is the last container starting. As soon that's up and running, open `localhost:8081`. You will be redirected to keycloak, which needs you to authenticate with one of the users below. 

| User   | Password | Role    |
|--------|----------|---------|
| alex   | test     | admin   |
| oliver | test     | office  |
| olga   | test     | office  |

Now the tasklist should be open and you can see your login name and role in right upper corner.

### Error Handling
In some rare cases the init script doesn't work. You can see that in the stacktrace of the postgres container. When 
you're starting the docker compose the very first time and you see something like 'Skipping initialization', then the
initial DB creation for the other services failed. In this case, you can start postgres as single container:

```bash
docker compose up postgres -d
```

Then log into the containers bash:
```bash
docker exec -it postgres /bin/bash
```

Log into psql:
```bash
su - postgres
psql
```

Create the databases by hand:
```sql
CREATE USER engine;
CREATE DATABASE engine;
GRANT ALL PRIVILEGES ON DATABASE engine TO engine;

CREATE USER schemadb;
CREATE DATABASE schemadb;
GRANT ALL PRIVILEGES ON DATABASE schemadb TO schemadb;

CREATE USER keycloak;
CREATE DATABASE keycloak;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

CREATE USER s3db;
CREATE DATABASE s3db;
GRANT ALL PRIVILEGES ON DATABASE s3db TO s3db;
```

You can check, if the DBs have been created:
```bash
\l
```
If so, your postgres server is ready. You can now start the complete infrastructure (see command above). 