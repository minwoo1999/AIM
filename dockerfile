# Dockerfile
FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE=dev
ENTRYPOINT ["java", "-jar", "/app.jar"]
