FROM eclipse-temurin:17-jre-alpine

# Nome exato do JAR gerado pelo Maven
ARG JAR_FILE=target/feira-conectada-api-1.0.0.jar

# Criar o diretório /app
RUN mkdir -p /app

# Copiar o arquivo .jar para o contêiner
COPY ${JAR_FILE} /app/application.jar

# Definir permissões corretas no arquivo .jar
RUN chmod 755 /app/application.jar

# Configurar o diretório de trabalho
WORKDIR /app

# Expor a porta
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "application.jar"]
