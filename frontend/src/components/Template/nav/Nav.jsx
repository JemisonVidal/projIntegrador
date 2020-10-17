import React from 'react'
import { Navbar, Nav, NavDropdown, Form, FormControl, Button } from 'react-bootstrap'
import './Nav.css'

const NavBar = (props) => {
  return (
    <>
      <Navbar collapseOnSelect expand="lg" >
        <Navbar.Brand href="#home">RecruIT</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link href="#Oportunidades">Oportunidades</Nav.Link>
            <Nav.Link href="#Empresas">Empresas</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </>
  )
}

export default NavBar
