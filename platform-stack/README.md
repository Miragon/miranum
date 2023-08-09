# Stack

For local development you can use the following tools:

- **SSO**: [Keycloak](https://www.keycloak.org/)
- **DB**: [PostgreSQL](https://www.postgresql.org/)
- **Minio**: [Minio](https://min.io/)

IMPORTANT NOTE: You must have keycloak configured in your hosts-file to run the sack locally!
On Mac/Linux it is located in `/etc/hosts` on Win `C:\Windows\System32\Drivers\etc\hosts`:
```bash
127.0.0.1 localhost keycloak
```

## Docker
Use docker compose to start the infrastructure components:

```bash
docker compose --profile engine up -d
```
