import React, { useContext, useEffect } from "react";
import { Card, CardDeck, Button } from "react-bootstrap";
import { Link, useHistory } from "react-router-dom";
import StoreContext from "../../components/Store/Context";
import Main from "../../components/Template/main/Main";
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
                Adicione novas habilidades e mostre a todos quais são seus
                pontos fortes que te tornam ainda mais relevante para diversas
                vagas !
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
              <Card.Text>
                Conhecimento é tudo, acrescente novos cursos para mostrar que
                você está sempre atualizada e pronta para novas vagas !
              </Card.Text>
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
                Edite ou preencha seu cartão de visitas para que mais empresas
                te encontrem !
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
                Acrescente experiências e mostre onde você trabalhou para que
                seu perfil fique ainda mais bonito !
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
              <Card.Text>
                Adicione uma vaga para encontrar profissionais e garantir um
                time de sucesso !
              </Card.Text>
            </Card.Body>
            <Link to={`/addOpportunity`}>
              <Button variant="primary">Adicionar Vaga</Button>
            </Link>
          </Card>
          <Card>
            <Card.Title>Procurar candidata</Card.Title>
            <Card.Img variant="top" src={searchSvg} />
            <Card.Body>
              <Card.Text>
                Procure candidatas que irão compor o time da sua empresa !
              </Card.Text>
            </Card.Body>
            <Link to={`/applicant`}>
              <Button variant="primary">Procurar Candidata</Button>
            </Link>
          </Card>
        </CardDeck>
        <CardDeck>
          <Card>
            <Card.Title>Sobre</Card.Title>
            <Card.Img variant="top" src={aboutSvg} />
            <Card.Body>
              <Card.Text>
                Altere ou acrescente coisas em seu perfil e se transforme em uma
                empresa ainda mais atrativa no mercado de trabalho !
              </Card.Text>
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
    <Main exibirBanner={true}>
      {user?.type === "applicant"
        ? renderProfileUser()
        : renderProfileCompany()}
    </Main>
  );
};

export default PagesHome;
