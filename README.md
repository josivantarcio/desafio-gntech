# Weather API - Desafio GNTECH
## API REST em Java 21/Spring Boot para consulta, armazenamento e listagem de dados climáticos a partir da Meteomatics Weather API. Projeto pronto para rodar localmente ou em container Docker.

## Tecnologias Utilizadas
Java 21
Spring Boot 3.5.7
Spring Data JPA
MySQL 8
Lombok
Meteomatics Weather API
Docker
Maven

## Instalação e Uso Rápido
1. Clone o repositório
bash
git clone https://github.com/josivantarcio/desafio-gntech.git
cd desafio-gntech
2. Configure o MySQL
Crie o banco de dados.

text
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/weather_db?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=sua_senha_mysql
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL8Dialect

METEOMATICS_API_URL=https://api.meteomatics.com
METEOMATICS_API_USER=SEU_USUARIO_METEOMATICS
METEOMATICS_API_PASSWORD=SUA_SENHA_METEOMATICS
Troque os valores conforme sua configuração!

4. Rodando com Docker Compose
Já incluso arquivo docker-compose.yml para rodar tudo com um só comando:

text
version: '3.8'
services:
  mysql:
    image: mysql:8
    environment:
      - MYSQL_ROOT_PASSWORD=sua_senha_mysql
      - MYSQL_DATABASE=weather_db
    ports:
      - "3306:3306"
    networks:
      - backend
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 10s
      retries: 5

  weather-api:
    build: .
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/weather_db?useSSL=false&serverTimezone=UTC
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=sua_senha_mysql
      - METEOMATICS_API_URL=https://api.meteomatics.com
      - METEOMATICS_API_USER=SEU_USUARIO_METEOMATICS
      - METEOMATICS_API_PASSWORD=SUA_SENHA_METEOMATICS
    ports:
      - "8080:8080"
    depends_on:
      mysql:
        condition: service_healthy
    networks:
      - backend

networks:
  backend:
Build and run:

bash
docker-compose up --build

## Acesse http://localhost:8080
5. Rodando LOCALMENTE (sem Docker)
Configure o banco e application.properties normalmente (veja acima)

## Compile com:
bash
mvn clean package
docker build -t weather-api .
docker run -p 8080:8080 --env-file .env weather-api
java -jar target/weather-api-*.jar

### Principais Endpoints
Salvar clima por cidade
text
POST /api/weather/fetch?dateTime=2025-11-06T12:40:00.000-03:00&lat=-3.7304512&lon=-38.5217989&cityName=Fortaleza
Exemplo com curl:

bash
curl -X POST "http://localhost:8080/api/weather/fetch?dateTime=2025-11-06T12:40:00.000-03:00&lat=-3.7304512&lon=-38.5217989&cityName=Fortaleza"
Listar todos os registros
text
GET /api/weather/all
Buscar registro por ID
text
GET /api/weather/{id}
Estrutura da Tabela weather_data
id	city_name	temperature	humidity	weather_description	observation_time
BIGINT	VARCHAR	DOUBLE	INTEGER (nullable)	VARCHAR	DATETIME
Dockerfile
Já incluso no repositório. Use junto do docker-compose, ou execute isolado:

text
## Dockerfile para Weather API Spring Boot
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/weather-api-*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

### API REST usando Spring Boot

 Consome Meteomatics Weather API (com autenticação)
