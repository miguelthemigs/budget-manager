# Stage 1: Build the application using Gradle
FROM gradle:7.5.0-jdk17 AS builder

# Set the working directory inside the builder container
WORKDIR /app

# Copy only necessary files to leverage Docker caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Build the application, skipping tests for faster builds
RUN ./gradlew build -x test

# Stage 2: Create a minimal image for the application
FROM openjdk:17-jdk

WORKDIR /opt/app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/budget-manager-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the application port
EXPOSE 8080

# Define the entrypoint command
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
