import React from 'react';
import { Form, Button, Spinner } from 'react-bootstrap';
import { useHistory, Link } from 'react-router-dom';
import Input from '../../components/Input/Input';
import Checkbox from '../../components/Checkbox/Checkbox';
import Error from '../../components/Helper/Error';
import useForm from '../../Hooks/useForm';
import useFetch from '../../Hooks/useFetch';
import { USER_REGISTER } from '../../APIs/APIs';
import './Register.css';

const UserRegister = () => {
  const nome = useForm();
  const email = useForm('email');
  const senha = useForm();
  const confirmarSenha = useForm();
  const tipo = useForm();
  const termo = useForm();
  const { loading, error, request } = useFetch();
  const history = useHistory();

  const optionsComboBox = [
    {value: '', text: 'Escolha uma opção'},
    {value: 'APPLICANT', text: 'Quero trabalhar'},
    {value: 'COMPANY', text: 'Quero recrutar'},
  ];

  async function registrar(nome, email, senha, tipo) {
    const { url, options } = USER_REGISTER({ name: nome.value, email: email.value, password: senha.value, type: tipo.value });
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
      termo.value() &&
      senha.value === confirmarSenha.value
    ) {
      const { response } = await registrar(nome, email, senha, tipo);
      if (response.ok) return history.push('/login');
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
          <Checkbox
            className="CheckBoxMin"
            type="checkbox"
            required
            label="Eu concordo com os termos de serviço e políticas de privacidade."
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
          <Error error={error} />
          <p id="cadastro">
            Já tem cadastro ?{' '}
            <Link id="login" to="/login">
              Faça o Login
          </Link>
          </p>
        </Form>
      </div>
    </div>
  );
};

export default UserRegister;
