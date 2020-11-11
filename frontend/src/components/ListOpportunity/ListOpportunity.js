import React, {useState} from "react";
import { Container, CardDeck, Card, Button, Pagination } from "react-bootstrap";
import Main from "../Template/main/Main";
import { Link } from "react-router-dom";

import "./ListOpportunity.css";

const mockOpportunity = [
  {
    id: 1,
    idEmpresa: 1,
    tituloVaga: "Programadora Node Js",
    descricao: "Desenvolver novas features em Node Js",
    requisitos: "Node, Front, Back, etc",
    beneficios: "beneficios",
    salario: 5000,
    textoLivre: "texto livre",
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

const paginationBasic = () => {
  let active = 2;
  let items = [];
  for (let number = 1; number <= 5; number++) {
    items.push(
      <Pagination.Item key={number} active={number === active}>
        {number}
      </Pagination.Item>,
    );
  }
  return (
    <div>
      <Pagination size="sm">{items}</Pagination>
    </div>
  );
};

const ListOpportunity = () => {
  const handlerCandidatar = (event) => {
    setCandidatarCheck(!candidatarCheck);
  }

  const [candidatarCheck, setCandidatarCheck] = useState(false);

  return (
    <Main>
      <Container fluid="md" className="py-2">
        {mockOpportunity &&
          mockOpportunity.map((opportunity) => {
            return (
              <CardDeck key={opportunity.id}>
                <Card className="card">
                  <Card.Body>
                    <Card.Title className="title-card">{opportunity.tituloVaga}</Card.Title>
                    <Card.Text><span className="titulo-campo">Localização:</span> <i class="fa fa-map-marker" aria-hidden="true"></i> {opportunity.localizacao}</Card.Text>
                    <Card.Text><span className="titulo-campo">Requisitos:</span> {opportunity.requisitos}</Card.Text>
                    <Card.Text><span className="titulo-campo">Benefícios:</span> {opportunity.beneficios}</Card.Text>
                    <Card.Text>
                      <span className="titulo-campo">Salário:</span>{" "}
                      {opportunity.salario.toLocaleString("pt-br", {
                        style: "currency",
                        currency: "BRL",
                      })}
                    </Card.Text>
                  </Card.Body>
                  <Link className="linkVaga" to={`/opportunity/${opportunity.id}`}>
                    <Button id={candidatarCheck ? "buttonGreen" : "buttonBlue"} className="buttonVaga" variant="primary" type="button">
                      {candidatarCheck ? "Candidatada" : "Candidatar-se"}
                    </Button>
                  </Link>
                </Card>
              </CardDeck>
            );
          })}
        {paginationBasic()}
      </Container>
    </Main >
  );
};

export default ListOpportunity;
