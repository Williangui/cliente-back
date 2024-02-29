# Projeto Para cadastro de clientes

Este projeto é um exemplo de uma aplicação Spring Boot que utiliza Docker para o ambiente de desenvolvimento e execução. Ele inclui uma configuração do Docker Compose para o banco de dados PostgreSQL e um Dockerfile para empacotar a aplicação Spring Boot.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- Docker
- IntelliJ IDEA (ou qualquer outra IDE de sua preferência)
- JDK(Java Development Kit) 17

## Configuração e Execução

### Utilizando Docker Compose


1. Execute o docker na suua maquina;

2. Abra o terminal na pasta do projeto;

3. No terminal, execute o Docker Compose para criar e iniciar os containers:

   ```bash
   docker-compose up
   ```

4. A aplicação Spring Boot estará disponível em `http://localhost:8080`.

5. Para encerrar a execução dos containers, pressione `Ctrl + C` no terminal e execute:

   ```bash
   docker-compose down
   ```

### Utilizando IntelliJ IDEA

1. Abra o IntelliJ IDEA e importe o projeto Spring Boot.

2. Certifique-se de que o JDK esteja configurado corretamente nas configurações do projeto.

3. Execute a aplicação Spring Boot a partir da classe principal ou do plugin Spring Boot disponível no IntelliJ.

4. A aplicação estará disponível em `http://localhost:8080`.

## Configurações Adicionais

Para configurar parâmetros adicionais, como variáveis de ambiente, consulte o arquivo `docker-compose.yml` e o arquivo `Dockerfile` no diretório raiz do projeto.

Para configurações específicas da aplicação Spring Boot, consulte o arquivo `application.properties` no diretório `src/main/resources`.
