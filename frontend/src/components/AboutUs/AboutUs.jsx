import React, { useEffect } from "react";
import { Container, Card } from "react-bootstrap";
import Main from "../Template/main/Main";
import logo from "../../assets/images/Logo2RecruIT.svg";
import missao from "../../assets/images/missao.png";
import visao from "../../assets/images/visao.png";
import valores from "../../assets/images/valores.png";
import "./AboutUs.css";

const AboutUs = () => {
  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
  }, []);

  return (
    <Main>
      <Container className="container-about-us">
        <Card className="card-about-us">
          <img className="logo-about-us" src={logo} alt="Logo Sobre Nós" />
          <h1 className="title-about-us title-principal">Quem somos</h1>
          <Card.Text className="text-about-us">
            <span>
              Sed ut perspiciatis unde omnis iste natus error sit voluptatem
              accusantium doloremque laudantium, totam rem aperiam, eaque ipsa
              quae ab illo inventore veritatis et quasi architecto beatae vitae
              dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
              aspernatur aut odit aut fugit, sed quia consequuntur magni dolores
              eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam
              est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci
              velit, sed quia non numquam eius modi tempora incidunt ut labore
              et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima
              veniam, quis nostrum exercitationem ullam corporis suscipit
              laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem
              vel eum iure reprehenderit qui in ea voluptate velit esse quam
              nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo
              voluptas nulla pariatur?
            </span>
          </Card.Text>

          <Card.Body className="title-about-us">
            <span>Referências e agradecimentos</span>
          </Card.Body>
          <Card.Text className="text-about-us">
            <span>
              Sed ut perspiciatis unde omnis iste natus error sit voluptatem
              accusantium doloremque laudantium, totam rem aperiam, eaque ipsa
              quae ab illo inventore veritatis et quasi architecto beatae vitae
              dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
              aspernatur aut odit aut fugit, sed quia consequuntur magni dolores
              eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam
              est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci
              velit, sed quia non numquam eius modi tempora incidunt ut labore
              et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima
              veniam, quis nostrum exercitationem ullam corporis suscipit
              laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem
              vel eum iure reprehenderit qui in ea voluptate velit esse quam
              nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo
              voluptas nulla pariatur?
            </span>
          </Card.Text>

          <Container className="about-us">
            <Card className="cards card-mission card-img-top">
              <Card.Body className="col-md-12">
                <Card.Title className="title-card-about-us">Missão</Card.Title>
                <Card.Text className="card-text-about">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut
                  enim ad minim veniam, quis nostrud exercitation
                </Card.Text>
              </Card.Body>
            </Card>

            <Card className="cards card-vision card-img-top">
              <Card.Body className="col-md-12">
                <Card.Title className="title-card-about-us">Visão</Card.Title>
                <Card.Text className="card-text-about">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                  do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                  Ut enim ad minim veniam, quis nostrud exercitation
                </Card.Text>
              </Card.Body>
            </Card>

            <Card className="cards card- card-img-top">
              <Card.Body className="col-md-12">
                <Card.Title className="title-card-about-us">Valores</Card.Title>
                <Card.Text className="card-text-about">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                  do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                  Ut enim ad minim veniam, quis nostrud exercitation
                </Card.Text>
              </Card.Body>
            </Card>
          </Container>
        </Card>
      </Container>
    </Main>
  );
};

export default AboutUs;
