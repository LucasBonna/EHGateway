FROM maven:3.9.6-eclipse-temurin-22-jammy

WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Command to run the application with hot reload
CMD ["mvn", "spring-boot:run"]