FROM eclipse-temurin:24-jdk-alpine

WORKDIR /app

COPY target/andersen-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]