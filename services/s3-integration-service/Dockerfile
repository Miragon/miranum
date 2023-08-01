FROM registry.access.redhat.com/ubi8/openjdk-11:latest

EXPOSE 8080

COPY target/*.jar /deployments/spring-boot-application.jar

CMD "java" ${JAVA_OPTIONS} "-jar" "/deployments/spring-boot-application.jar"
