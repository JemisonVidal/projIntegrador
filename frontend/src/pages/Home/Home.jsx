import React from "react";
import { Card, CardDeck, Button } from "react-bootstrap";
import { useHistory, Link } from "react-router-dom";
import Main from "../../components/Template/main/Main";
import imageMain from "../../assets/images/main-photo.jpg";
import logo from "../../assets/images/Logo2RecruIT.png";
import skillSvg from "../../assets/images/home/skills.svg";
import courseSvg from "../../assets/images/home/course.svg";
import linkSvg from "../../assets/images/home/link.svg";
import workSvg from "../../assets/images/home/work.svg";

import "./Home.css";

const PagesHome = () => {
  var image = "http://placekitten.com/300/300";
  return (
    <Main>
      <div className="div-banner-home">
        <img className="image-banner-home" src={imageMain} />
        <img className="logo-banner-home" src={logo} />
        <h1>
          Recru<span>IT</span>
        </h1>
        <p>Descubra e seja descoberto</p>
      </div>
      <section className="data-profile">
        <CardDeck>
          <Card>
            <Card.Title>Habilidades</Card.Title>
            <Card.Img variant="top" src={skillSvg} />
            <Card.Body>
              <Card.Text>
                Acessar minhas habilidades(Pensar em uma frase)
              </Card.Text>
            </Card.Body>
            <Button variant="primary">Acessar minhas habilidades</Button>
          </Card>
          <Card>
            <Card.Title>Cursos</Card.Title>
            <Card.Img variant="top" src={courseSvg} />
            <Card.Body>
              <Card.Text>Acessar meus cursos(Pensar em uma frase)</Card.Text>
            </Card.Body>
            <Button variant="primary">Acessar meus cursos</Button>
          </Card>
        </CardDeck>
        <CardDeck>
          <Card>
            <Card.Title>Links Externos</Card.Title>
            <Card.Img variant="top" src={linkSvg} />
            <Card.Body>
              <Card.Text>
                Acessar meus links externos (Pensar em uma frase)
              </Card.Text>
            </Card.Body>
            <Button variant="primary">Acessar meus links externos</Button>
          </Card>
          <Card>
            <Card.Title>Experiências</Card.Title>
            <Card.Img variant="top" src={workSvg} />
            <Card.Body>
              <Card.Text>
                Acessar minhas Experiências(Pensar em uma frase)
              </Card.Text>
            </Card.Body>
            <Button variant="primary">Acessar minhas Experiências</Button>
          </Card>
        </CardDeck>
      </section>
    </Main>
  );
};

export default PagesHome;
