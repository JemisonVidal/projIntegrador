import React, { useContext, useEffect } from "react";
import { Card, CardDeck, Button } from "react-bootstrap";
import { Link, useHistory } from "react-router-dom";
import StoreContext from "../../components/Store/Context";
import Main from "../../components/Template/main/Main";
import imageMain from "../../assets/images/main-photo.jpg";
import logo from "../../assets/images/Logo2RecruIT.png";
import skillSvg from "../../assets/images/home/skills.svg";
import courseSvg from "../../assets/images/home/course.svg";
import linkSvg from "../../assets/images/home/link.svg";
import workSvg from "../../assets/images/home/work.svg";
import searchSvg from "../../assets/images/home/search.svg";
import opportunitySvg from "../../assets/images/home/opportunity.svg";
import aboutSvg from "../../assets/images/home/about.svg";

import "./Home.css";

const PagesHome = () => {
  const history = useHistory();
  const { user } = useContext(StoreContext);

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
  }, []);

  const renderProfileUser = () => {
    return (
      <section className="data-profile">
        <CardDeck>
          <Card>
            <Card.Title>Habilidades</Card.Title>
            <Card.Img variant="top" src={skillSvg} />
            <Card.Body>
              <Card.Text>
                Acessar minhas habilidades
              </Card.Text>
            </Card.Body>
            <Link to={`/profile/applicant/${user.pid}#skills`}>
              <Button variant="primary">Adicionar uma habilidade</Button>
            </Link>
          </Card>
          <Card>
            <Card.Title>Cursos</Card.Title>
            <Card.Img variant="top" src={courseSvg} />
            <Card.Body>
              <Card.Text>Acessar meus cursos</Card.Text>
            </Card.Body>
            <Link to={`/profile/applicant/${user.pid}#courses`}>
              <Button variant="primary">Adicionar um curso</Button>
            </Link>
          </Card>
        </CardDeck>
        <CardDeck>
          <Card>
            <Card.Title>Links Externos</Card.Title>
            <Card.Img variant="top" src={linkSvg} />
            <Card.Body>
              <Card.Text>
                Acessar meus links externos
              </Card.Text>
            </Card.Body>
            <Link to={`/profile/applicant/${user.pid}#info`}>
              <Button variant="primary">Acessar meus links externos</Button>
            </Link>
          </Card>
          <Card>
            <Card.Title>Experiências</Card.Title>
            <Card.Img variant="top" src={workSvg} />
            <Card.Body>
              <Card.Text>
                Acessar minhas Experiências
              </Card.Text>
            </Card.Body>
            <Link to={`/profile/applicant/${user.pid}#experiences`}>
              <Button variant="primary">Adicionar uma experiência</Button>
            </Link>
          </Card>
        </CardDeck>
      </section>
    );
  };

  const renderProfileCompany = () => {
    function handleOnClick(event) {
      return history.push(`/profile/company/${user.pid}`);
    }

    return (
      <section className="data-profile">
        <CardDeck>
          <Card>
            <Card.Title>Adicionar Vaga</Card.Title>
            <Card.Img variant="top" src={opportunitySvg} />
            <Card.Body>
              <Card.Text>Adicionar vaga</Card.Text>
            </Card.Body>
            <Button variant="primary" onClick={handleOnClick}>
              Adicionar Vaga
            </Button>
          </Card>
          <Card>
            <Card.Title>Procurar candidata</Card.Title>
            <Card.Img variant="top" src={searchSvg} />
            <Card.Body>
              <Card.Text>Procurar candidata</Card.Text>
            </Card.Body>
            <Button variant="primary" onClick={handleOnClick}>
              Procurar Candidata
            </Button>
          </Card>
        </CardDeck>
        <CardDeck>
          <Card>
            <Card.Title>Sobre</Card.Title>
            <Card.Img variant="top" src={aboutSvg} />
            <Card.Body>
              <Card.Text>Acessar sobre</Card.Text>
            </Card.Body>
            <Button variant="primary" onClick={handleOnClick}>
              Sobre
            </Button>
          </Card>
        </CardDeck>
      </section>
    );
  };

  return (
    <Main>
      <div className="div-banner-home">
        <img className="image-banner-home" src={imageMain} alt="Banner" />
        <img className="logo-banner-home" src={logo} alt="Logo RecruIT" />
        <h1>
          Recru<span>IT</span>
        </h1>
        <p>Descubra e seja descoberto</p>
      </div>
      {user?.type === "applicant"
        ? renderProfileUser()
        : renderProfileCompany()}
      {/* TODO: ADICIONAR O HOME DA EMPRESA COM OS CARDS CRIAR VAGA/BUSCAR CANDIDATA */}
    </Main>
  );
};

export default PagesHome;
