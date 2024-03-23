FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
ADD target/spring-jwt-authentication.jar spring-jwt-authentication.jar
ENTRYPOINT ["java", "-jar", "/spring-jwt-authentication.jar"]