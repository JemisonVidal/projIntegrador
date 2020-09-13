import React from 'react';
import { Form, Button } from 'react-bootstrap';
import Input from '../../components/Input/Input';
import Checkbox from '../../components/Checkbox/Checkbox';
import './Register.css';

const UserRegister = () => {
  const options = [
    "Escolha uma opção",
    "Quero trabalhar",
    "Quero recrutar"
  ];

  return (
    <div className="container-box">
      <Form className="container form-box">
        <h1 className="titulo-registro">Cadastre-se</h1>
        <Input nameLabel="Nome" type="text" placeholder="Nome" required />
        <Input nameLabel="Email" type="email" placeholder="Email" required />
        <Input nameLabel="Senha" type="password" placeholder="Senha" required />
        <Input nameLabel="Confirmar Senha" type="password" placeholder="Confirmar Senha" required />
        <Input nameLabel="Quero trabalhar/Quero recrutar" as="select" required options={options} />
        <Checkbox className="CheckBoxMin" type="checkbox" label="Eu concordo com os termos de serviço e políticas de privacidade."></Checkbox>
        <Button id="Cadastrar" variant="primary" type="submit">
          Cadastrar
        </Button>
      </Form>

    </div >
  )
}

export default UserRegister

