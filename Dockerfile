# Stage 1: Build the application using Gradle
FROM gradle:7.5.0-jdk17 AS builder

# Set the working directory inside the builder container
WORKDIR /app

# Copy all files into the builder container
COPY . .

# Build the application, excluding tests for faster build time
RUN gradle build -x test

FROM gradle:7.5.0-jdk17

WORKDIR /opt/app

COPY ./build/libs/budget-manager-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar budget-manager-0.0.1-SNAPSHOT.jar"]