import React from "react";
import "./Main.css";
import Footer from "../footer/Footer";
import Nav from "../nav/Nav";

const Main = (props) => (
  <React.Fragment>
    <Nav />
    <main>{props.children}</main>
    <Footer />
  </React.Fragment>
);

export default Main;
