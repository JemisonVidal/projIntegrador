import React, { useState } from "react";
import { Container, CardDeck, Card } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Main from "../Template/main/Main";
import heart from "../../assets/images/Heart.svg";
import heartFill from "../../assets/images/FilledHeart.svg";
import back from "../../assets/images/back.svg";

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

const ListOpportunity = () => {

  const history = useHistory();

  const handlerBackClick = () => {
    history.push('/listOpportunity');
  }

  const handlerHeartClick = () => {
    setHeartCheck(!heartCheck);
    history.push('/listOpportunity');
  }

  const [heartCheck, setHeartCheck] = useState(false);

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
          <button onClick={handlerBackClick} className="buttonSelect buttonNo"><img className="xIco" src={back} alt='Retornar' /></button>
          <button onClick={handlerHeartClick} disabled={heartCheck} className="buttonSelect buttonYes"><img className="heartIco" src={heartCheck ? heartFill : heart} alt='Candidatar' /></button>
        </div>
      </Container >
    </Main >
  );
};

export default ListOpportunity;
