import React, { useState, useContext } from "react";
import { useHistory, Link } from "react-router-dom";
import StoreContext from "../../components/Store/Context";
import { Form, Button, Spinner } from "react-bootstrap";
import logo from "../../assets/images/LogoRecruIT.png";
import eye from "../../assets/images/eye-solid.svg";
import Input from "../Input/Input";
import Checkbox from "../Checkbox/Checkbox";
import useFetch from "../../Hooks/useFetch";
import useForm from "../../Hooks/useForm";
import Error from "../Helper/Error";
import { USER_LOGIN } from "../../APIs/userAPI";

import "./Login.css";

const UserLogin = () => {
  const email = useForm("email");
  const senha = useForm();
  const [erroLogin, setErroLogin] = useState(null);
  const { setToken } = useContext(StoreContext);
  const history = useHistory();
  const { loading, request } = useFetch();

  async function login(email, senha) {
    const { url, options } = USER_LOGIN({
      email: email.value,
      password: senha.value
    });
    return await request(url, options);
  }

  async function onSubmit(e) {
    e.preventDefault();

    if (email.validate() && senha.validate()) {
      const { json, response } = await login(email, senha);
      if (response?.ok) {
        setToken(json.token);
        return history.push("/");
      }
      setErroLogin("Usuário ou senha inválido");
    }
  }

  return (
    <div className="container-box-login">
      <div className="container-image-background-login">
        <Form className="container form-box-login" onSubmit={onSubmit}>
          <h1 className="logo-login">
            <img className="logo-banner-login" src={logo} alt="Logo RecruIT" />
          </h1>
          <h2>
            Recru<span>IT</span>
          </h2>
          <Input
            namelabel="Email"
            type="email"
            placeholder="Email"
            className="form-register-login"
            required
            {...email}
          />
          <Input
            namelabel="Senha"
            type="password"
            placeholder="Senha"
            className="form-register-login"
            required
            {...senha}
          />
          <Checkbox
            className="CheckBoxMin"
            type="checkbox"
            label="Lembrar minha senha"
          />
          {loading ? (
            <Button id="entrarBtn" variant="primary" disabled>
              <Spinner
                className="spinner-login"
                as="span"
                animation="border"
                size="sm"
                role="status"
                variant="light"
              />
            </Button>
          ) : (
            <Button id="btnEntrar" variant="primary" type="submit">
              Entrar
            </Button>
          )}
          <Error error={erroLogin} />
          <p id="links-login">
            <Link id="esqueci-senha" to="/login">
              Esqueci minha senha
            </Link>
          </p>
          <p id="links-login">
            Não tem uma conta ?{" "}
            <Link id="cadastro-login" to="/register">
              Cadastre-se
            </Link>
          </p>
        </Form>
      </div>
    </div>
  );
};

export default UserLogin;
