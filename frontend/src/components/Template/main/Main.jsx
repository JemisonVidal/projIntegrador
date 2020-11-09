import React, { Children } from "react";
import "./Main.css";
import Footer from "../footer/Footer";
import logo from "../../../assets/images/LogoRecruIT.png";
import Nav from "../nav/Nav";

export default (props) => (
  // <React.Fragment>
  //   <main className="content container-box-home">
  //     <div className="container-image-background">
  //       <div className="container form-box-home">
  //         <Nav />
  //         {props.children}
  //         <Footer />
  //       </div>
  //     </div>
  //   </main>
  // </React.Fragment>
  <React.Fragment>
    <Nav />
    <main>{props.children}</main>
    <Footer />
  </React.Fragment>
);
