import React, { useState } from "react";
import { Form, Button, Spinner, Modal } from "react-bootstrap";
import { useHistory, Link } from "react-router-dom";
import Input from "../../components/Input/Input";
import Error from "../../components/Helper/Error";
import useForm from "../../Hooks/useForm";
import useFetch from "../../Hooks/useFetch";
import { USER_REGISTER } from "../../APIs/userAPI";
import SucessSVG from "../../assets/images/register/sucessRegister.svg";
import "./Register.css";

const UserRegister = () => {
  const history = useHistory();
  const nome = useForm();
  const email = useForm("email");
  const senha = useForm("password");
  const confirmarSenha = useForm("password");
  const tipo = useForm();
  const [errorSubmit, setErrorSubmit] = useState(null);
  const [messageSucess, setMessageSucess] = useState(false);
  const { loading, request } = useFetch();

  const optionsComboBox = [
    { value: "", text: "Escolha uma opção" },
    { value: "APPLICANT", text: "Quero trabalhar" },
    { value: "COMPANY", text: "Quero recrutar" },
  ];

  async function registrar(nome, email, senha, tipo) {
    const { url, options } = USER_REGISTER({
      name: nome.value,
      email: email.value,
      password: senha.value,
      type: tipo.value,
    });
    return await request(url, options);
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (
      nome.validate() &&
      email.validate() &&
      senha.validate() &&
      confirmarSenha.validate() &&
      tipo.validate() &&
      senha.value === confirmarSenha.value
    ) {
      try {
        const { response, json } = await registrar(nome, email, senha, tipo);
        if (response?.ok) {
          setMessageSucess(true);
          setTimeout(() => {
            return history.push("/login");
          }, 3000);
        } else {
          setErrorSubmit(json.msg);
        }
      } catch (error) {
        setErrorSubmit("Desculpe, ocorreu uma falha, tente novamente");
      }
    } else {
      setErrorSubmit("Falha ao validar campos");
    }
  }

  return (
    <div className="container-box">
      <div className="container-image-background-cadastro">
        <Form className="container form-box" onSubmit={handleSubmit}>
          <h1 className="titulo-registro">Cadastre-se</h1>
          <Input
            namelabel="Nome"
            type="text"
            placeholder="Nome"
            required
            {...nome}
          />
          <Input
            namelabel="Email"
            type="email"
            placeholder="Email"
            required
            {...email}
          />
          <Input
            namelabel="Senha"
            type="password"
            placeholder="Senha"
            required
            {...senha}
          />
          <Input
            namelabel="Confirmar Senha"
            type="password"
            placeholder="Confirmar Senha"
            required
            {...confirmarSenha}
          />
          <Input
            namelabel="Quero trabalhar/Quero recrutar"
            as="select"
            required
            options={optionsComboBox}
            {...tipo}
          />
          {loading ? (
            <Button id="Cadastrar" variant="primary" disabled>
              <Spinner
                as="span"
                animation="border"
                size="sm"
                role="status"
                aria-hidden="true"
              />
              <span className="sr-only">Carregando...</span>
            </Button>
          ) : (
            <Button id="Cadastrar" variant="primary" type="submit">
              Cadastrar
            </Button>
          )}
          <Error error={errorSubmit} />
          <p id="cadastro">
            Já tem cadastro ?{" "}
            <Link id="login" to="/login">
              Faça o Login
            </Link>
          </p>
        </Form>
      </div>
      <Modal
        show={messageSucess}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <p className="sucess-register">
          Cadastro realizado com <strong>Sucesso!</strong>
          <br />
          Você será redicionado ao login em <strong>5 segundos</strong> ;)
        </p>

        <img className="sucess-register-img" src={SucessSVG}></img>
      </Modal>
    </div>
  );
};

export default UserRegister;
