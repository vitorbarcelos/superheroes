# SuperHeroes

Backend do SuperHeroes

## Executando a aplicação

Para rodar o projeto, é necessário ter **Docker** e **Docker Compose** instalados na sua máquina. Os arquivos
`Dockerfile` e `docker-compose.yml` contêm as especificações dos containers necessários para executar a aplicação. É
recomendado revisar esses arquivos antes de iniciar.

Use os seguintes comandos no terminal para iniciar o backend em **modo de desenvolvimento**:

### Desenvolvimento

1. Iniciar o backend em modo desenvolvimento:

```bash
docker compose up -d
```

2. Visualizar os logs da aplicação em tempo real:

```bash
docker container logs -f superheroes
```

Para iniciar o backend em **modo produção**, use os comandos abaixo:

### Produção

1. Criar uma imagem a partir do Dockerfile:

```bash
docker buildx build --target prod -t superheroes-backend:latest . --no-cache
```

2. Criar um container a partir da imagem criada e definir as variáveis de ambiente:

```bash
docker run -d -p "8080:8080" --name superheroes-backend \
-e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/superheroes \
-e SPRING_DATASOURCE_USERNAME=user \
-e SPRING_DATASOURCE_PASSWORD=password \
[...]
superheroes-backend:latest
```

3. Visualizar os logs da aplicação em tempo real:

```bash
docker container logs -f superheroes-backend
```

> O `[...]` indica que outras variáveis de ambiente podem ser necessárias para a aplicação iniciar corretamente.

## Documentação

A documentação da API está disponível via **Swagger**, acessível em:

```
http://localhost:8080/swagger-ui.html
```

No Swagger, você encontrará informações sobre os módulos da aplicação e endpoints, incluindo os métodos HTTP
disponíveis, parâmetros de entrada e respostas para cada endpoint.

## Testes

O projeto já inclui testes unitários completos para todos os módulos importantes (controllers, services, mappers,
validators, exceptions).

✅ Cobertura atual: 100%

### Testes Unitários

```bash
./mvnw test -Dspring.profiles.active=test
```

### Cobertura de Testes

```bash
./mvnw test -Dspring.profiles.active=test jacoco:report
```

## Autor

Vitor Barcelos

