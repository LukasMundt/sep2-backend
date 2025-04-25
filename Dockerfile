FROM gradle:8.13-jdk21-corretto AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew build -x test --no-daemon

RUN ./gradlew bootJar --no-daemon

FROM amazoncorretto:21-alpine3.21-jdk AS runner

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]