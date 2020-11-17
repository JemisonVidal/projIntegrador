import React from "react";
import "./Main.css";
import Footer from "../footer/Footer";
import Nav from "../nav/Nav";
import { Container } from "react-bootstrap";

const Main = (props) => (
  <React.Fragment>
    <Nav />
    <Container fluid="md" as="main">
      {props.children}
    </Container>
    <Footer />
  </React.Fragment>
);

export default Main;
