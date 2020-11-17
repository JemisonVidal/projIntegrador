FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/projeto-integrador-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT java -jar projeto-integrador-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
