# Projeto Spring Boot 2025 REST API's do 0 à AWS e GCP c Java e Docker
Projeto do curso de Spring Boot da Erudio Training com algumas funcionalidades.

## Tech Stack
- Java 21
- Spring Boot 3
- JPA
- HATEOAS
- Mapstruct
- Flyway
- Mockito
- TestContainers
- REST Assured
- CI/CD
- GitHub Actions
- Docker
- Docker HUB
## APIs

``POST /api/v1/person``: Cria uma nova pessoa com os detalhes fornecidos.

``POST /api/v2/person``: Cria uma nova pessoa com os novos detalhes fornecidos.

``GET /api/v1/person``: Recupera uma lista paginada de todos as pessoas.

``GET /api/v1/person/{id}``: Recupera uma pessoa pelo seu identificador exclusivo.

``PUT /api/v1/person/{id}``: Atualiza uma pessoa pelo seu ID.

``DELETE /api/v1/person/{id}``: Exclui um salário pelo seu ID.

``POST /api/v1/file/upload``: Faz o upload de um arquivo.

``GET /api/v1/file/upload``: Faz o download de um arquivo.
