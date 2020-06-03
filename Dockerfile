FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.host=mongo", "-jar","/app.jar"]