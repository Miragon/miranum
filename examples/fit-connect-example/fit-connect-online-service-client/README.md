# Fit-Connect online service client

*This online service client lets you create and submit submissions from a fit-connect online service client (sender) to a specified fit-connect destination.*

## Make it run

1. First of all you need to create a fit-connect sender client and  a destination (optionally). \
   For information on how to create the clients and the destination, see [the fit-connect video tutorial](https://docs.fitko.de/fit-connect/docs/receiving/overview) (German).

2. Add the following properties to your application. You can add those to a `credentials.properties` file, which is not tracked by git  and imported by your main `application.properties`.

```properties
spring.security.oauth2.client.registration.fitconnect.authorization-grant-type=client_credentials
spring.security.oauth2.client.registration.fitconnect.client-id=<client-id>
spring.security.oauth2.client.registration.fitconnect.client-secret=<client-secret>
spring.security.oauth2.client.provider.fitconnect.token-uri=<token-uri>

fitconnect.sender.destination-id=<fit-connect-destination-id>
```

Now you can just start the main `OnlineServiceApplication`.

The token uri for the testing environment is https://auth-testing.fit-connect.fitko.dev/token.

*For more information, please refer to the [fit-connect documentation](https://docs.fitko.de/fit-connect/docs).*