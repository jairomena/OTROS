FROM eclipse-temurin:17.0.5_8-jre-alpine

RUN mkdir /app
WORKDIR /app

COPY build/quarkus-app/ .

CMD ["java", "-jar", "quarkus-run.jar"]

