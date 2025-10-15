# Etapa 1: Compilar o projeto
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Rodar o .jar gerado
FROM eclipse-temurin:17
WORKDIR /app
ls /app/target
COPY --from=builder /app/target/curadoria-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]