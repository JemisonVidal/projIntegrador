import React from 'react'
import { Navbar, Nav, NavDropdown, Form, FormControl, Button } from 'react-bootstrap'
import './Nav.css'

const NavBar = (props) => {
  return (
    <>
      <Navbar className="Navbar" collapseOnSelect expand="lg"  >
        <Navbar.Brand className="nav-item" href="#home">RecruIT</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link className="nav-item" href="#Oportunidades">Oportunidades</Nav.Link>
            <Nav.Link className="nav-item" href="#Empresas">Empresas</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </>
  )
}

export default NavBar
