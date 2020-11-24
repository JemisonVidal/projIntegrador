import React, { useEffect } from "react";
import { Container, Card } from "react-bootstrap";
import Main from "../Template/main/Main";
import logo from "../../assets/images/Logo2RecruIT.svg";
import email from "../../assets/images/email.svg";
import phone from "../../assets/images/phone.svg";
import whats from "../../assets/images/whatsapp.svg";
import "./Contacts.css";

const Contacts = () => {
  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
  }, []);

  return (
    <Main>
      <Container className="container-contacts">
        <Card className="card-contacts">
          <img className="logo-contacts" src={logo} alt="Logo" />
          <h1 className="title-contacts title-principal">
            Entre em contato conosco
          </h1>

          <Container className="contacts">
            <Card
              className="cards-contacts card-email card-img-top"
              style={{ width: "15rem" }}
            >
              <Card.Body className="col-md-12 body-contacts">
                <img className="icon-img" src={email} alt="Email" />
                <Card.Title className="title-card-contacts">Email</Card.Title>
                <Card.Text className="card-text-contacts">
                  Mande sua dúvida por e-mail!
                </Card.Text>
                <Card.Text className="card-text-contacts-content">
                  <span className="content">recruit@gmail.com </span>
                </Card.Text>
              </Card.Body>
            </Card>

            <Card
              className="cards-contacts card-phone card-img-top"
              style={{ width: "15rem" }}
            >
              <Card.Body className="col-md-12 body-contacts">
                <img className="icon-img" src={phone} alt="Phone" />
                <Card.Title className="title-card-contacts">
                  Telefone
                </Card.Title>
                <Card.Text className="card-text-contacts">
                  Contate-nos por telefone!
                </Card.Text>
                <Card.Text className="card-text-contacts-content">
                  <span className="content">(11) 3201-1233</span>
                </Card.Text>
              </Card.Body>
            </Card>

            <Card
              className="cards-contacts card-whats card-img-top"
              style={{ width: "15rem" }}
            >
              <Card.Body className="col-md-12 body-contacts">
                <img className="icon-img" src={whats} alt="Whatsapp" />
                <Card.Title className="title-card-contacts">
                  Whatsapp
                </Card.Title>
                <Card.Text className="card-text-contacts">
                  Entre em contato por Whatsapp!
                </Card.Text>
                <Card.Text className="card-text-contacts-content">
                  <span className="content">
                    (11) 9999-999 ou{" "}
                    <a
                      href="https://web.whatsapp.com/"
                      className="link-whatsapp"
                    >
                      Clique Aqui
                    </a>
                  </span>
                </Card.Text>
              </Card.Body>
            </Card>
          </Container>
        </Card>
      </Container>
    </Main>
  );
};

export default Contacts;
