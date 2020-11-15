import React from "react";
import "./Main.css";
import Footer from "../footer/Footer";
import Nav from "../nav/Nav";

export default (props) => (
  <React.Fragment>
    <Nav />
    <main>{props.children}</main>
    <Footer />
  </React.Fragment>
);
