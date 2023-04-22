FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=target/banca-1.0.jar
ADD ${JAR_FILE} banca-1.0.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/banca-1.0.jar"]