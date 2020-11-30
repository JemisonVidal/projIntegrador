# Projeto Integrador

# O que é ? 
  O projeto Integrador é o projeto final para conclusão do curso de Desenvolvimento Web Full Stack da instituição de ensino Digital House Brasil, em parceira com o Banco Santander. O projeto aqui desenvolvido consiste em uma plataforma de recrutamento voltata exclusivamente para o público feminino. A plataforma, cujo o nome é Recruit,
  tem como objetivo principal conectar as mulheres de todo o brasil às melhores oportunidades do mercado de trabalho. A idéia nasceu como uma iniciativa para solucionar
  um problema que está presente no cotidiano do brasil e do resto do mundo: a pouca presença de mulheres no mercado de TI. A plataforma visa ser uma iniciativa para 
  tornar o mercado de trabalho mais justo, inclusivo e diverso incentivando empresas a divulgar oportunidades e processos seletivos voltado ao publico feminino.
  
  ![ebe53bce-0823-4e5f-9852-c2c027ab0c11](https://user-images.githubusercontent.com/5355538/100669757-a0936280-333c-11eb-98ba-d6de05624a2e.jpg)

# Integrantes
 - Jemison Vidal
 - Antonio Gabriel
 - Priscila Tiemi
 - Guilherme Gwadera
 - Luis
 - Mirla Karina
  
# Links importantes
  Alguns links importantes. Neles estão o preview da aplicação, a documentação da API, o protótipo, a retro e outros documentos importantes para melhor compreensão
  do projeto.
  - **Aplicação**: https://pi-recruit.netlify.app/ 
  - **Swagger**: https://pi.ggwadera.xyz/swagger-ui.html# 
  - **Projeto**: https://github.com/JemisonVidal/projIntegrador
  - **Board**: https://github.com/JemisonVidal/projIntegrador/projects/1
  - **Protótipo**: https://github.com/JemisonVidal/projIntegrador/tree/develop/docs/Prototipo
  - **Retro**: https://github.com/JemisonVidal/projIntegrador/tree/develop/docs/Retros/Retro%20%231 (Apenas a primeira, o restante está no Miro)
  - **Acordos**: https://github.com/JemisonVidal/projIntegrador/tree/develop/docs/Acordos%20do%20time
  - **Lean inception**: https://github.com/JemisonVidal/projIntegrador/tree/develop/docs/lean%20inception
# Instruções para compilar e executar o projeto


  ## Backend
  O backend foi todo construido utilizando Spring utilizando a linguagem Java, Spring Data JPA com o Hibernate como ORM (Object Relational Mapping). para executar o projeto,
  basta fazer clonar o repositório no GitHub utilizando o comando ```git clone```, abrir o projeto em alguma IDE de sua escolha (recomendados Eclipse ou IntelliJ) e compilar
  o projeto. O endereço local onde o projeto roda é ```localhost:8080```. Para acessar a documentação provida pelo Swagger localmente, basta digitar ```http://localhost:8080/swagger-ui.html```.

  ## Frontent
  Para executar o frontend, basta acessar a pasta frontend e seguir os seguintes passos:
  ### Instruções para o NPM
  - Para instalar as dependências do projeto, execute o comando ```npm install``` e aguarde o NPM instalar as dependencias do projeto;
  - Execute o projeto utilizando o comando ```npm run start```;
  - Abra o projeto acessando o navegador e digitando na barra de endereços ```localhost:3000```;

  ![2bef279a-83ad-491c-894c-f0be34b8960d](https://user-images.githubusercontent.com/5355538/100669748-9ec99f00-333c-11eb-869c-e70f9d1310eb.jpg)

  ### Instruções para o Yarn
  - Para instalar as dependências do projeto, execute o comando ```yarn install``` e aguarde o NPM instalar as dependencias do projeto;
  - Execute o projeto utilizando o comando ```yarn start```;
  - Abra o projeto acessando o navegador e digitando na barra de endereços ```localhost:3000```;

  ![c500194b-e7a6-485f-ab8a-d538a77d2299](https://user-images.githubusercontent.com/5355538/100669753-a0936280-333c-11eb-8c40-c207e144c3a5.jpg)
  ![a395a821-c40f-4194-9008-5b2f0279a105](https://user-images.githubusercontent.com/5355538/100669749-9ffacc00-333c-11eb-9971-7fda6e419966.jpg)

# Requisitos do Projeto
 - Tema Livre
 - O projeto desenvolvido deverá contemplar front end e back end.
 - O desenvolvimento deverá seguir o método ágil scrum, sendo organizado emsprints.
 - Os participantes devem ter presença no desenvolvimento de todas as etapas doprojeto, será analisado os commit realizados, por tanto usem e abusem do git.
 - É obrigatório que implemente sistema de segurança ( Autenticação e Autorização ),incluindo mecanismos para recuperação de senha.
 - Para segurança, será utilizado o framework Spring Security, o mecanismo pode sertanto por jwt puro como também impĺementar um serviço de OAuth.
 - O sistema deverá persistir informações em um banco de dados relacional, pedimospara que utilize o MySQL que será abordado no curso.
 - As operações de leitura/busca, deverá contemplar buscas por id, genéricas, e outroscampos que sejam interessantes para o projeto.
 - As buscas que retornam mais de uma entidade por requisição, ou seja, uma coleçãode objetos, devem ser paginadas.
 - Ainda no quesito dados, é obrigatório a utilização de frameworks ORM, emespecífico Hibernate e também em conjunto com o Spring Data
 - O backend deve contar com pelo menos ​80% ​de cobertura  por testesautomatizados
 - Para os testes poderá ser utilizado frameworks como junit e mockito
 - A api deverá  seguir o modelo REST, com todas as boas práticas e recomendaçõesque o modelo exige.
 - Utilização dos frameworks Spring, (Framework, Boot, Data e Security)-
 - Caso utilize algum framework de frontend, como react, angular, etc, não garantimoso suporte técnico,  e é necessário o consenso de todos os integrantes do grupoquanto a essa escolha
 - A api deve ser documentada, você pode escolher qualquer ferramenta (ex: postman,swagger), mas deverá também estar no readme do seu github.
 - Erros devem ser tratados pelo back-end e não podem parar a execução do seuprojeto (O stacktrace de erro não pode ser exibido no frontend, em vez disso, exibauma mensagem amigável).
