import React, { useEffect } from "react";
import { Container, Card } from "react-bootstrap";
import Main from "../Template/main/Main";
import logo from "../../assets/images/Logo2RecruIT.svg";
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
              A RecruIT é um portal cuja missão é conectar as mulheres de todo o
              Brasil às melhores oportunidades do mercado de trabalho de
              Tecnologia da Informação. Por ser uma área predominantemente
              masculina, a RecruIT nasceu com a finalidade de reverter esse
              cenário e incentivar a contratação de mulheres pelas empresas de
              TI, conectando as mulheres e as empresas e contribuir para um
              mercado de trabalho mais inclusivo e diverso. Em uma pesquisa
              recente realizada pela Pesquisa Nacional por Amostra de Domicílios
              (PNAD), dos 580 mil profissionais de TI que atuam no Brasil,
              apenas 20% são mulheres, mas essa realidade não é um problema
              exclusivo do mercado Brasileiro. Empresas como a Google, por
              exemplo, reportaram que apenas 30% do seu quadro de colaboradores
              são do sexo feminino. A valorização feminina no mercado de
              trabalho é um ponto importante para se discutir, pois ainda há
              muito a ser feito até que a igualdade de gênero seja uma realidade
              palpável. Só no ano de 2017 as mulheres ocupavam 10.9% das vagas
              no mercado de trabalho, segundo pesquisas, e esse número aumentou
              para 12.9% em 2020. Mesmo diante dessa tímida vitória, ainda há
              muitos desafios a serem superados, como a deigualdade salarial e a
              falta de oportunidades. Portanto nossa empresa nasce com o
              propósito de incentivar iniciativas para incentivar a inclusão e a
              diversidade no mercado de TI voltadas para o público feminino.
            </span>
          </Card.Text>

          <Card.Body className="title-about-us">
            <span>Referências e agradecimentos</span>
          </Card.Body>
          <Card.Text className="text-about-us">
            <span>
              Agradecemos primeiramente ao Santander pela oportunidade concedida
              para o curso oferecido pela Digital House. Aos instrutores Mário
              Marques e Gustavo valeiro pelo aprendizado e por compartilhar
              conosco seu conhecimento e suas experiências profissionais.
            </span>
          </Card.Text>

          <Container className="about-us">
            <Card className="cards card-mission card-img-top">
              <Card.Body className="col-md-12">
                <Card.Title className="title-card-about-us">Missão</Card.Title>
                <Card.Text className="card-text-about">
                  Tornar o mercado de TI mais justo, diverso e igualitário.
                </Card.Text>
              </Card.Body>
            </Card>

            <Card className="cards card-vision card-img-top">
              <Card.Body className="col-md-12">
                <Card.Title className="title-card-about-us">Visão</Card.Title>
                <Card.Text className="card-text-about">
                  Proporcionar às mulheres de todo o brasil a oportunidade de
                  ingressar no mercado de TI.
                </Card.Text>
              </Card.Body>
            </Card>

            <Card className="cards card- card-img-top">
              <Card.Body className="col-md-12">
                <Card.Title className="title-card-about-us">Valores</Card.Title>
                <Card.Text className="card-text-about">
                  Somos uma empresa que se baseia no respeito mútuo, na inclusão
                  e na diversidade. Somente assim seremos capazes de criar uma
                  sociedade mais justa e mais inclusiva.
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
