# Use a lightweight Java 21 runtime image
FROM eclipse-temurin:21-jre-alpine

# Spring Boot uses /tmp to store embedded Tomcat data
VOLUME /tmp

# Expose the port the Order Service runs on
EXPOSE 9001

# Find the compiled JAR file and copy it into the container as app.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# The command to start the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]