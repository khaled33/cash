FROM openjdk:17.0.2-slim-buster
EXPOSE 8088
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
VOLUME /tmp
ENTRYPOINT ["java","-jar","/app.jar"]
