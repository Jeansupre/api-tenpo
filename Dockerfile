FROM eclipse-temurin:21-jdk
LABEL authors="jcarl"
WORKDIR /app
COPY target/api.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api.jar"]
