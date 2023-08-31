# CoffeDelivery_SpringApi
Este é um repositório que contém uma API de entrega de café desenvolvida em Java usando Spring Boot, Spring Security, JPA, Hibernate, PostgreSQL e implantada no Heroku.

## Descrição
Este projeto é uma API RESTful que permite a realização de pedidos de café e a entrega dos mesmos. A API foi desenvolvida usando o framework Spring Boot e persiste os dados no banco de dados PostgreSQL por meio do JPA e do Hibernate. A aplicação também foi implantada no Heroku, permitindo acesso remoto à API.

## Funcionalidades
A API possui as seguintes funcionalidades:

- Cadastro de usuários: permite o registro de novos usuários para que possam administrar a pagina e administrar produtos.
- Gerenciamento de produtos: permite o administrar de diferentes tipos de café disponíveis para entrega.
- Gerenciamento de pedidos: os clientes podem fazer pedidos dos cafés e os administradores podem gerenciar os pedidos.

## Pré-requisitos
Para executar o projeto localmente, é necessário ter o seguinte ambiente configurado:

Java JDK 8 ou superior
Maven
PostgreSQL
Uma IDE de desenvolvimento Java, como o Eclipse ou o IntelliJ IDEA

## Configuração
Siga as etapas abaixo para configurar e executar o projeto localmente:

Clone o repositório do GitHub:

``` git clone https://github.com/KaioAntonio/CoffeDelivery_SpringApi.git ```
Importe o projeto em sua IDE de desenvolvimento Java.

Configure as informações de conexão com o banco de dados PostgreSQL no arquivo application.properties:

``` spring.datasource.url=jdbc:postgresql://localhost:5432/nome-do-banco-de-dados ```


## Contribuição
Se você deseja contribuir para este projeto, siga as etapas abaixo:

Faça um fork deste repositório.

Crie uma branch para a sua feature ou correção de bug:



``` git checkout -b minha-feature ```
``` spring.datasource.username=seu-usuario ```
``` spring.datasource.password=sua-senha ```

Execute o projeto a partir da classe principal CoffeDeliveryApplication.java.

A API estará disponível em http://localhost:8080. Você pode utilizar uma ferramenta como o Postman para testar os endpoints.
