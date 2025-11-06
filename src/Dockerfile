
FROM eclipse-temurin:17
WORKDIR /app
COPY target/weather-api-*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
