# Dockerfile

# builder stage
FROM gradle:8.10-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean assemble -x test

# runtime stage
FROM eclipse-temurin:21-jdk
WORKDIR /app

# КОПИРУЕМ JAR БЕЗ ARG — напрямую
COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app/app.jar

ARG INTERNAL_REPO_LOGIN
ARG INTERNAL_REPO_PASSWORD
ENV INTERNAL_REPO_LOGIN=$INTERNAL_REPO_LOGIN
ENV INTERNAL_REPO_PASSWORD=$INTERNAL_REPO_PASSWORD

EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
