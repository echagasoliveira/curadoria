# Etapa de build (opcional se você já tem o .jar)
# FROM maven:3.8.5-openjdk-17 AS build
# COPY . /app
# WORKDIR /app
# RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:17
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
