# Stage 1: Build the JAR
FROM maven:3.8.8-eclipse-temurin-8 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime image (Debian/Ubuntu-based Temurin with full ARM64 support)
FROM eclipse-temurin:8-jre
WORKDIR /app

# Create a non-root user (Debian/Ubuntu syntax)
RUN groupadd -r appgroup && useradd -r -g appgroup appuser
USER appuser

# Copy JAR from the builder stage
COPY --from=builder --chown=appuser:appgroup /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]