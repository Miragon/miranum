FROM amazoncorretto:17-alpine

EXPOSE 8080

RUN mkdir /app

COPY ./target/engine-tasklist-service-*.jar /app/spring-boot-application.jar

CMD ["java","-jar","/app/spring-boot-application.jar"]
