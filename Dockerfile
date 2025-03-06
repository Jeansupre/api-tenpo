FROM eclipse-temurin:21-jdk
LABEL authors="Jean Carlo Rodriguez Sanchez"
RUN mkdir -p /app
WORKDIR /app
COPY target/prueba-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/api.jar"]

