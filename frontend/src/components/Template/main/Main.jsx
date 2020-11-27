import React from "react";
import "./Main.css";
import Footer from "../footer/Footer";
import Nav from "../nav/Nav";
import { Container } from "react-bootstrap";

const Main = ({ exibirBanner = false, children }) => (
  <React.Fragment>
    <Nav exibirBanner={exibirBanner} />
    <Container fluid="md" as="main">
      {children}
    </Container>
    <Footer />
  </React.Fragment>
);

export default Main;
