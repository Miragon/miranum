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

On Mac/Linux:
```bash
docker compose --profile engine up -d
```

On Windows:
```bash
docker-compose --profile engine up -d
```

### Smoke Test
The tasklist is the last container starting. As soon that's up and running, open `localhost:8081`. You will be redirected to keycloak, which needs you to authenticate with one of the users below. 

alex / test (role admin)
oliver / test (role office)
olga / test (role office)

Now the tasklist should be open and you can see your login name and role in right upper corner.