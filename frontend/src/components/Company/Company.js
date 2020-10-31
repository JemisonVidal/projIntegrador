import React from "react";
import { Container, CardDeck, Card, Button } from "react-bootstrap";
import Main from "../../components/Template/main/Main";

import "./Company.css";

const mockCompany = [
  {
    descricao: "Empresa 1",
    visao:
      "Mussum Ipsum, cacilds vidis litro abertis, Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Praesent malesuada urna nisi, quis volutpat erat hendrerit non",
    local: "Itu/SP",
    ramoCategoria: "tecnolgia / ERP",
    site: "https://mussumipsum.com/",
    Linkedin: "https://linkedin/mussumipsum",
  },
  {
    descricao: "Empresa 2",
    visao:
      "Mauris nec dolor in eros commodo tempor. Aenean aliquam molestie leo",
    local: "São Paulo/SP",
    ramoCategoria: "Software house",
    site: "https://mussumipsum.com/",
    Linkedin: "https://linkedin/mussumipsum",
  },
  {
    descricao: "Empresa 3",
    visao:
      "Quem manda na minha terra sou euzis! Paisis, filhis, espiritis santis",
    local: "Rio de Janeiro/SP",
    ramoCategoria: "Software house - web",
    site: "https://mussumipsum.com/",
    linkedin: "https://linkedin/mussumipsum",
  },
];

const Company = () => {
  return (
    <Main>
      <Container fluid="md" className="py-2">
        {mockCompany &&
          mockCompany.map((mockCompany) => {
            return (
              <CardDeck>
                <Card>
                  <Card.Body>
                    <Card.Title>{mockCompany.descricao}</Card.Title>
                    <Card.Text>Visão Geral: {mockCompany.visao}</Card.Text>
                    <Card.Text>local: {mockCompany.local}</Card.Text>
                    <Card.Text>Ramo:{mockCompany.ramoCategoria}</Card.Text>
                    <Card.Text>site: {mockCompany.site}</Card.Text>
                    <Card.Text>linkedin:{mockCompany.linkedin}</Card.Text>
                  </Card.Body>
                  <Button variant="primary" type="submit">
                    Ver Perfil
                  </Button>
                </Card>
              </CardDeck>
            );
          })}
      </Container>
    </Main>
  );
};

export default Company;
