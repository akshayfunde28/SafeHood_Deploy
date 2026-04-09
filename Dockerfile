# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy all build files
COPY --from=build /app/target /app/target

# Dynamically pick jar
RUN cp /app/target/*.jar /app/app.jar

# Debug (IMPORTANT)
RUN ls -l /app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]