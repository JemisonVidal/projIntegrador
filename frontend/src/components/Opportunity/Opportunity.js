import React from "react";
import { Container, CardDeck, Card, Button } from "react-bootstrap";
import Main from "../Template/main/Main";

import "./Opportunity.css";

const mockOpportunity = [
  {
    id: 1,
    idEmpresa: 1,
    tituloVaga: "Programadora Node Js",
    descricao: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
    requisitos: "Node, Front, Back, etc",
    beneficios: "beneficios",
    salario: 5000,
    textoLivre: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
    localizacao: "São Paulo, SP",
    status: "aberta",
  },
  {
    id: 2,
    idEmpresa: 2,
    tituloVaga: "Programadora React",
    descricao: "Executar manutenções e novas features em react",
    requisitos: "Node, Front, Back, etc",
    beneficios: "beneficios",
    salario: 6000,
    textoLivre: "texto livre",
    localizacao: "São Paulo, SP",
    status: "aberta",
  },
  {
    id: 3,
    idEmpresa: 3,
    tituloVaga: "Programadora full stack",
    descricao: "Desenvolver novas features em Node Js e React",
    requisitos: "Node, Front, Back, etc",
    beneficios: "beneficios",
    salario: 8000,
    textoLivre: "texto livre",
    localizacao: "São Paulo, SP",
    status: "aberta",
  },
];

const opportunity = mockOpportunity.filter(vaga => vaga.id === 1);
console.log(opportunity);

const ListOpportunity = ({ idVaga }) => {
  return (
    <Main>
      <Container fluid="md" className="py-2">
        <CardDeck key={opportunity[0].id}>
          <Card className="card">
            <Card.Body>
              <Card.Title className="title-card-opportunity">{opportunity[0].tituloVaga}</Card.Title>
              <Card.Text className="title-empresa-opportunity">{opportunity[0].idEmpresa}</Card.Text>
              <span className="titulo-campo-opportunity">Localização</span>
              <Card.Text><i class="fa fa-map-marker" aria-hidden="true"></i> {opportunity[0].localizacao}</Card.Text>
              <span className="titulo-campo-opportunity">Descrição da vaga</span>
              <Card.Text>{opportunity[0].descricao}</Card.Text>
              <span className="titulo-campo-opportunity">Requisitos</span>
              <Card.Text>{opportunity[0].requisitos}</Card.Text>
              <span className="titulo-campo-opportunity">Salário</span>
              <Card.Text>
                {" "}
                {opportunity[0].salario.toLocaleString("pt-br", {
                  style: "currency",
                  currency: "BRL",
                })}
              </Card.Text>
              <span className="titulo-campo-opportunity">Benefícios</span>
              <Card.Text>{opportunity[0].beneficios}</Card.Text>
              <span className="titulo-campo-opportunity">Informações Adicionais</span>
              <Card.Text>{opportunity[0].textoLivre}</Card.Text>
            </Card.Body>
          </Card>
        </CardDeck>
        <div className="buttonsCandidatar">
          <div className="buttonSelect buttonNo"><span className="titulo-campo-opportunity"><svg className="xIco" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M24 20.188l-8.315-8.209 8.2-8.282-3.697-3.697-8.212 8.318-8.31-8.203-3.666 3.666 8.321 8.24-8.206 8.313 3.666 3.666 8.237-8.318 8.285 8.203z" /></svg></span></div>
          <div className="buttonSelect buttonYes"><span className="titulo-campo-opportunity"><svg className="heartIco" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M6.28 3c3.236.001 4.973 3.491 5.72 5.031.75-1.547 2.469-5.021 5.726-5.021 2.058 0 4.274 1.309 4.274 4.182 0 3.442-4.744 7.851-10 13-5.258-5.151-10-9.559-10-13 0-2.676 1.965-4.193 4.28-4.192zm.001-2c-3.183 0-6.281 2.187-6.281 6.192 0 4.661 5.57 9.427 12 15.808 6.43-6.381 12-11.147 12-15.808 0-4.011-3.097-6.182-6.274-6.182-2.204 0-4.446 1.042-5.726 3.238-1.285-2.206-3.522-3.248-5.719-3.248z" /></svg></span></div>
        </div>
      </Container>
    </Main>
  );
};

export default ListOpportunity;
