FROM openjdk:20
WORKDIR /app
COPY target/* .
EXPOSE 8080
ENTRYPOINT ["java","-jar","Library-0.0.1-SNAPSHOT.jar"]