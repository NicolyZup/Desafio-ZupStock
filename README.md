# ZupStock :ballot_box:
Repositório criado para entrega do desafio: Gerenciamento de Estoque - API

## :pushpin: Desafio proposto 
Desenvolver um sistema de gerenciamento de estoque usando o Spring
Framework e um banco de dados PostgreSQL. O sistema permitirá a criação,
atualização, leitura e exclusão de produtos no estoque por meio de requisições
HTTP. As interações com o sistema serão simuladas usando o Postman.

Requisitos:

● Utilizar o Spring Framework (Spring Boot, Spring Data JPA) para criar a
aplicação.

● Usar um banco de dados PostgreSQL ou H2 para armazenar os dados dos
produtos.

● Criar endpoints REST para as operações CRUD (criação, leitura, atualização,
exclusão) de produtos.

● Permitir a criação de novos produtos com informações como nome,
descrição, preço e quantidade que deseja armazenar.

● Possibilitar a atualização dos detalhes de um produto existente.

● Permitir a obtenção da lista de todos os produtos ou de um produto
específico por ID ou nome.

● Possibilitar a exclusão de produtos.

● Implementar tratamento de erros e exceções apropriados.

● Implementar testes unitários com cobertura de 100%.

● Documentar a API com o Swagger, tornando-a compreensível para outros
desenvolvedores.


### :round_pushpin: Endpoints 

**1. Listar todos os produtos:** Endpoint responsável por listar todos os produtos da lista. Se não houver produtos na lista é retornando a informação de lista vazia.

**2. Listar produto por id:** Endpoint responsável por listar produto por id, caso o id for inexistente ou um tipo inválido (por exemplo, letra) é retornado mensagem de erro.

**3. Cadastrar produto:** Endpoint responsável por cadastrar um produto. Espera-se receber um body com os seguintes campos: nome, descricao, preco e quantidade, com os devidos tratamentos caso os campos forem null ou passados vazios("").

**4. Editar um produto:** Endpoint responsável por editar as informações de um produto. Espera-se receber um body com o campo a ser alterado (nome, descricao, preco e/ou quantidade), com os devidos tratamentos caso o campo for null ou vazio ("").

**5. Excluir um produto:** Endpoint responsável por excluir um produto por id, com os devidos tratamentos caso o id não existir na lista ou o id for um tipo inválido, por exemplo, uma letra.

##  :pushpin: Tratamento de Exceções 
Na pasta exception é encontrada a classe responsável pelas tratativas de exceções. As tratativas são focadas nos dados inseridos pelo usuário, sendo elas:

- Usuário não pode passar um id (path variable) sem ser no formato de número, como por exemplo, uma letra;
- Usuário não pode passar campos em branco, como por exemplo, com aspas vazias ("");
- Usuário não pode informar valores menores ou iguais a zero nos campos preco e quantidade;
- Usuário não pode mandar o body sem campos.

## :pushpin: Postman
Para conferir as requisições e suas respostas, só acessar [aqui](https://documenter.getpostman.com/view/20786077/2s9Y5ctfeK) a documentação do Postman.

## :pushpin: Swagger 
Clique [aqui](http://localhost:8080/swagger-ui/index.html#/) para acessar o link do Swagger (necessário estar com o projeto rodando).

<img src="./stock/src/main/resources/img/swagger.png" width="600">

## :pushpin: Arquitetura em camadas 
Pasta controller: Responsável pela requisições HTTP, interage com a camada de serviço (service) encaminhando as solicitações.

Pasta service: Responsável pela lógica e regras de negócio das solicitações vindas da camada controller.

Pasta repository: Responsável pela persistência dos dados no banco de dados.

Pasta exception: Responsável pela tratativa de erros e exceções.

Pasta models: Responsável pelas entidades(tabelas), abstração e modelagem dos dados.

Pasta models/dtos: Responsável pelos DTOS. objetos de transferência entre as camadas.

## :pushpin: Testes 

Os testes foram aplicados na camada Service e Controller, com 100% de cobertura. Foram utilizados para os testes: Mockito, JUnit e MockMvc.

<img src="./stock/src/main/resources/img/testes.png" width="600">

## :pushpin: Ferramentas e Tecnologias usadas
- Java 11
- IntelliJ
- Spring Boot (JPA, Web, Validadtion)
- Javax
- Lombok
- Banco de dados relacional: PostgreSQL





