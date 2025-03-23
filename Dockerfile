FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/watahel-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]