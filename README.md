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

Caso queira utilizar a aplicação JAVA pelo docker, precisa alterar a configuração do `docker-compose.yml` o command de Keycloak para:

```yaml
start-dev --import-realm --hostname-url=http://keycloak:8080 --hostname-admin-url=http://localhost:8443
```

e executar com o profile `api`:
```bash
docker compose --profile api up --build
```

caso ocorra um erro para criar o volume, dê permissão para a pasta `.docker`:

```bash
chmod 777 .docker
```

O `docker-compose.yml` ja define as variaveis usadas pela API:

- `KEYCLOAK_URL=http://keycloak:8080`
- `KEYCLOAK_REALM=hegia`
- `KEYCLOAK_CLIENT_ID=hackaton_client`



No Keycloak, garanta que o realm `hegia` existe e que o client `hackaton_client` esta configurado para emitir tokens.

Tenha certeza que o usuário tenha permissão na pasta `.docker/keycloak` para persistir os dados do Keycloak.

E ainda nessa pasta é possivel configurar o realm, clients e usuarios do Keycloak. O arquivo 
`.docker/keycloak/import/realm-hegia.json` é importado automaticamente na primeira execucao do container.

adicione o seguinte contéudo o (O Healm precisa ser o mesmo configurado na API, por padrão `hegia`): 
```json
{
  "realm": "hegia",
  "enabled": true,
  "clients": [
    {
      "clientId": "hackaton_client",
      "enabled": true,
      "protocol": "openid-connect",
      "publicClient": true,
      "standardFlowEnabled": true,
      "directAccessGrantsEnabled": true,
      "serviceAccountsEnabled": false,
      "redirectUris": ["*"],
      "webOrigins": ["*"]
    }
  ],
  "users": [
    {
      "username": "hackaton_user",
      "enabled": true,
      "emailVerified": true,
      "credentials": [
        { "type": "password", "value": "hackaton123", "temporary": false }
      ]
    }
  ]
}
```

## Autenticação

Para realizar chamadas autenticadas, obtenha um token JWT do Keycloak:

```bash
curl --location 'http://localhost:8443/realms/hegia/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=hackaton_client' \
--data-urlencode 'username=hackaton_user' \
--data-urlencode 'password=hackaton123'
```

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
