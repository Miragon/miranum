# Fit-Connect C8 example

## Make it run

To make it run, make sure to add the following properties to your application.
Also make sure you have a running instance of the [Camunda 8 Platform engine](../../../stack/camunda-8/docker-compose.yml).

```properties
spring.security.oauth2.client.registration.fitconnect.authorization-grant-type=client_credentials
spring.security.oauth2.client.registration.fitconnect.client-id=<client-id>
spring.security.oauth2.client.registration.fitconnect.client-secret=<client-secret>
spring.security.oauth2.client.provider.fitconnect.token-uri=<token-uri>

fitconnect.subscriber.destination-id=<fit-connect-destination-id>
fitconnect.subscriber.privateKey-decryption-path=<path-to-private-decryption-key-json>
fitconnect.subscriber.privateKey-signing-path=<path-to-private-signing-key-json>
```
You can get the client credentials and the id of the destination from the [fit-connect self-service-portal testing environment](https://portal.auth-testing.fit-connect.fitko.dev/login). \
The token uri for the testing environment is https://auth-testing.fit-connect.fitko.dev/token.