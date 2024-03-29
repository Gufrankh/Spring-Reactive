FROM openjdk:8-jre-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar","/app/app.jar"]
EXPOSE 8081

