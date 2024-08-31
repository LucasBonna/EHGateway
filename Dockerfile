FROM maven:3.9.6-eclipse-temurin-22-jammy
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
CMD ["java", "-jar", "/app/target/gateway-0.0.1-SNAPSHOT.jar"]