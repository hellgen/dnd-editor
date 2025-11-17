FROM gradle:8.10-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:21-jdk
COPY --from=builder /app/build/libs/dnd-editor-0.0.1-SNAPSHOT.jar service.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/dnd_editor_db
ENV POSTGRES_USER=dnduser
ENV POSTGRES_PASSWORD=dnduser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "service.jar"]