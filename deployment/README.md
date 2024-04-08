# Miranum Deployment

Checkout our docs at [miranum.io](https://www.miranum.io/docs/components/miranum-ide/miranum-deployment) for more information.

[Miranum Deployment](https://github.com/Miragon/miranum-ide/tree/main/spring-boot-apps/miranum-deployment) consists of two parts. The Miranum Deployment Server and the Miranum Deployment Receiver.
The Miranum Deployment Server is a Spring Boot application that provides a REST API to deploy process artifacts to
Spring applications that use the Miranum Deployment Receiver library.

## Module Structure

- **core**: Contains the core implementation for Miranum-Deployment.
- **rest**: Contains the REST adapter implementation for Miranum-Deployment.
- **embedded**: Contains the embedded adapter implementation for Miranum-Deployment.
- **examples**: Contains example implementations using different adapters of Miranum-Deployment-Server and Miranum-Deployment-Receiver.
