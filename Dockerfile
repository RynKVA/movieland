FROM eclipse-temurin:22-jdk-alpine AS builder

# Build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src
RUN chmod +x gradlew
RUN ./gradlew test bootJar

# Runtime
FROM eclipse-temurin:22-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
