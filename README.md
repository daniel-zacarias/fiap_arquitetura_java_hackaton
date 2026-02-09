# FIAP Hackaton - Higia

Backend API com Java (Spring Boot), banco PostgreSQL, Keycloak para autenticacao e um job Python para carga de dados de exemplo.

## Estrutura

- `higia/` - backend Java (domain, application, infrastructure)
- `python_carga/` - script de carga de dados
- `database/ddl.sql` - schema inicial do banco
- `docker-compose.yml` - orquestracao local

## Requisitos

- Docker e Docker Compose

## Subir o ambiente

```bash
docker compose up --build
```

Servicos e portas:

- API: http://localhost:8080/api
- Swagger: http://localhost:8080/api/swagger-ui/index.html
- Keycloak: http://localhost:8443/admin
- Postgres: localhost:5432

## Configuracoes principais

O `docker-compose.yml` ja define as variaveis usadas pela API:

- `KEYCLOAK_URL=http://keycloak:8080`
- `KEYCLOAK_REALM=hegis`
- `KEYCLOAK_CLIENT_ID=hackaton_client`

No Keycloak, garanta que o realm `hegis` existe e que o client `hackaton_client` esta configurado para emitir tokens.

## Carga de dados (opcional)

O container `python_carga` gera dados ficticios e insere no banco. Para executar:

```bash
docker compose --profile carga up --build
```

## Build local (sem Docker)

Requer Java 21 e Maven.

```bash
cd higia
mvn -DskipTests -pl infrastructure -am package
java -jar infrastructure/target/infrastructure-1.0-SNAPSHOT.jar
```

## Observacoes

- O banco e inicializado com `database/ddl.sql`.
- A API usa `issuer-uri`/`jwk-set-uri` do Keycloak no realm `hegis`.
