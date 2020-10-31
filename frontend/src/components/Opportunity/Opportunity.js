import React from "react";
import { Container, CardDeck, Card, Button } from "react-bootstrap";
import Main from "../../components/Template/main/Main";

import "./Opportunity.css";

const mockOpportunity = [
  {
    idEmpresa: 1,
    tituloVaga: "Programadora Node Js",
    descricao: "Desenvolver novas features em Node Js",
    beneficios: "beneficios",
    salario: 5000,
    textoLivre: "texto livre",
    status: "aberta",
  },
  {
    idEmpresa: 2,
    tituloVaga: "Programadora React",
    descricao: "Executar manutenções e novas features em react",
    beneficios: "beneficios",
    salario: 6000,
    textoLivre: "texto livre",
    status: "aberta",
  },
  {
    idEmpresa: 3,
    tituloVaga: "Programadora full stack",
    descricao: "Desenvolver novas features em Node Js e React",
    beneficios: "beneficios",
    salario: 8000,
    textoLivre: "texto livre",
    status: "aberta",
  },
];

const Opportunity = () => {
  return (
    <Main>
      <Container fluid="md" className="py-2">
        {mockOpportunity &&
          mockOpportunity.map((opportunity) => {
            return (
              <CardDeck>
                <Card>
                  <Card.Body>
                    <Card.Title>{opportunity.tituloVaga}</Card.Title>
                    <Card.Text>Descrição: {opportunity.descricao}</Card.Text>
                    <Card.Text>Benefícios: {opportunity.beneficios}</Card.Text>
                    <Card.Text>
                      Salário:{" "}
                      {opportunity.salario.toLocaleString("pt-br", {
                        style: "currency",
                        currency: "BRL",
                      })}
                    </Card.Text>
                  </Card.Body>
                  <Button variant="primary" type="submit">
                    Candidatar-se
                  </Button>
                </Card>
              </CardDeck>
            );
          })}
      </Container>
    </Main>
  );
};

export default Opportunity;
