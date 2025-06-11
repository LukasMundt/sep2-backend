FROM gradle:8.13-jdk21-corretto AS builder

ARG APP_DOMAIN

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
COPY src ./src

RUN rm ./src/main/resources/application.properties
RUN mv ./src/main/resources/application.properties.deploy ./src/main/resources/application.properties

ENV APP_DOMAIN=${APP_DOMAIN}

RUN ./gradlew build -x test --no-daemon

RUN ./gradlew bootJar --no-daemon

FROM cloudron/base:5.0.0@sha256:04fd70dbd8ad6149c19de39e35718e024417c3e01dc9c6637eaf4a41ec4e596c AS runner

RUN apt-get update && \
    apt-get install -y openjdk-21-jre-headless && \
    rm -rf /var/cache/apt /var/lib/apt/lists

WORKDIR /app/code

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ADD start.sh /app/code/
# Ensure user can execute start.sh
RUN chmod +x /app/code/start.sh

CMD [ "/app/code/start.sh" ]