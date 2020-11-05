import React from "react";
import { Navbar, Nav, Image } from "react-bootstrap";
import { Link } from "react-router-dom";
import "./Nav.css";

const NavBar = ({ avatar, type, id }) => {
  //TODO: Confirmar se iremos receber props ou utilizar o context/redux
  const mockPropAtavar = `http://placekitten.com/300/300`;
  type = `user`;
  id = 1;

  return (
    <>
      <Navbar className="Navbar" collapseOnSelect expand="lg">
        <Nav className="title">
          <Link to={`/`}>
            {/* <Navbar.Brand className="nav-item" href="#home"> */}
            RecruIT
            {/* </Navbar.Brand> */}
          </Link>
        </Nav>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Link className="nav-item" to="/listOpportunity">
              Oportunidades
            </Link>
            <Link className="nav-item" to="/company">
              Empresas
            </Link>
          </Nav>
          <Nav className="ml-auto">
            <Link to={`/profile/${type}/${id}`}>
              <Image className="avatar" src={mockPropAtavar} rounded />
            </Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </>
  );
};

export default NavBar;
