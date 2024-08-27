FROM eclipse-temurin:22-jdk-jammy

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copia o pom.xml e instala as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Comando para executar a aplicação com hot reload
CMD ["mvn", "spring-boot:run"]