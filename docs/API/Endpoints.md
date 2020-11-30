- [Registro e autenticação](#registro-e-autenticação)
- [Perfil de candidatas](#perfil-de-candidatas)
- [Perfil de empresas](#perfil-de-empresas)
- [Oportunidades](#oportunidades)

A seguir estão listados os endpoints da API com suas respectivas descrições. Esta documentação também pode ser acessada na interface do Swagger através do link https://pi.ggwadera.xyz/swagger-ui.html.

# Registro e autenticação

| Método | Endpoint             | Body? | Autenticado? | Descrição                                                                 |
| :----: | :------------------- | :---: | :----------: | :------------------------------------------------------------------------ |
|  POST  | /v1/api/register     |  Sim  |     Não      | Registra um novo usuário e cria um perfil em branco.                      |
|  POST  | /v1/api/authenticate |  Sim  |     Não      | Autentica um usuário pelo email e senha, gerando um novo token de acesso. |

# Perfil de candidatas

| Método | Endpoint                                | Body? | Autenticado? | Descrição                                                     |
| :----: | :-------------------------------------- | :---: | :----------: | :------------------------------------------------------------ |
|  GET   | /v1/api/profile/applicant               |  Não  |     Sim      | Retorna uma lista paginada com todos os perfis de candidatas. |
|  GET   | /v1/api/profile/applicant/_{id}_        |  Não  |     Sim      | Retorna o perfil com o respectivo ID.                         |
| PATCH  | /v1/api/profile/applicant/_{id}_        |  Sim  |     Sim      | Atualiza os dados do perfil com o respectivo ID.              |
|  GET   | /v1/api/profile/applicant/_{id}_/avatar |  Não  |     Não      | Retorna um link para a imagem do perfil com o respectivo ID.  |
| PATCH  | /v1/api/profile/applicant/_{id}_/avatar |  Sim  |     Sim      | Atualiza a imagem do perfil com o respectivo ID.              |

Obs.: para os endpoints de modificação de perfil, é feita a verificação a partir do usuário autenticado para chechar se os IDs correspondem, podendo autorizar ou não a edição do perfil.

# Perfil de empresas

| Método | Endpoint                              | Body? | Autenticado? | Descrição                                                    |
| :----: | :------------------------------------ | :---: | :----------: | :----------------------------------------------------------- |
|  GET   | /v1/api/profile/company               |  Não  |     Sim      | Retorna uma lista paginada com todos os perfis de empresas.  |
|  GET   | /v1/api/profile/company/_{id}_        |  Não  |     Sim      | Retorna o perfil com o respectivo ID.                        |
| PATCH  | /v1/api/profile/company/_{id}_        |  Sim  |     Sim      | Atualiza os dados do perfil com o respectivo ID.             |
|  GET   | /v1/api/profile/company/_{id}_/avatar |  Não  |     Não      | Retorna um link para a imagem do perfil com o respectivo ID. |
| PATCH  | /v1/api/profile/company/_{id}_/avatar |  Sim  |     Sim      | Atualiza a imagem do perfil com o respectivo ID.             |

Obs.: para os endpoints de modificação de perfil, é feita a verificação a partir do usuário autenticado para chechar se os IDs correspondem, podendo autorizar ou não a edição do perfil.

# Oportunidades

| Método | Endpoint                           | Body? | Autenticado? | Descrição                                                                                     |
| :----: | :--------------------------------- | :---: | :----------: | :-------------------------------------------------------------------------------------------- |
|  GET   | /v1/api/opportunity                |  Não  |     Sim      | Retorna uma lista paginada com todas as oportunidades ativas.                                 |
|  POST  | /v1/api/opportunity                |  Sim  |     Sim      | Cria uma nova oportunidade associada ao perfil da empresa que criou a requisição.             |
|  GET   | /v1/api/opportunity/_{id}_         |  Não  |     Não      | Retorna a oportunidade com o respectivo ID.                                                   |
| DELETE | /v1/api/opportunity/_{id}_         |  Não  |     Sim      | Deleta a oportunidade com o respectivo ID.                                                    |
| PATCH  | /v1/api/opportunity/_{id}_         |  Sim  |     Sim      | Atualiza a oportunidade com o respectivo ID.                                                  |
| PATCH  | /v1/api/opportunity/_{id}_/active  |  Não  |     Sim      | Alterna o status de ativo para a oportunidade com o respectivo ID.                            |
|  GET   | /v1/api/opportunity/_{id}_/applied |  Não  |     Sim      | Retorna uma lista com os perfis que candidataram-se à vaga com o respectivo ID.               |
|  POST  | /v1/api/opportunity/_{id}_/apply   |  Não  |     Sim      | Candidata a usuária atualmente autenticado para a oportunidade com o respectivo ID.           |
|  GET   | /v1/api/opportunity/applied        |  Não  |     Sim      | Retorna uma lista com as oportunidades que a usuária atualmente autenticada já se candidatou. |
|  GET   | /v1/api/opportunity/company/_{id}_ |  Não  |     Sim      | Retorna uma lista com as oportunidades da empresa com o respectivo ID.                        |