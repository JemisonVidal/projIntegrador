import React, { useContext, useState } from "react";
import { Navbar, Nav, Image, Dropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import StoreContext from "../../Store/Context";
import { useScrollPosition } from "../../../utils/useScrollPosition";
import "./Nav.css";

const NavBar = () => {
  const { user, avatar } = useContext(StoreContext);
  const [sticky, setSticky] = useState(false);

  const handleClick = () => {
    window.localStorage.removeItem("apptoken");
  };

  useScrollPosition(
    ({ prevPos, currPos }) => {
      const isShow = currPos.y > prevPos.y;
      if (isShow !== sticky) setSticky(isShow);
    },
    [sticky]
  );

  return (
    <Navbar
      className="Navbar"
      collapseOnSelect
      expand="lg"
      sx={{
        position: "sticky",
        transform: sticky ? "translateY(100%)" : "translateY(0)",
        transition: "transform 400ms ease-in",
        top: 0,
        left: 0
      }}
    >
      <Nav className="title">
        <Link to={`/`}>RecruIT</Link>
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
          <Link className="nav-item" to="/applicant">
            Candidatas
          </Link>
        </Nav>
        <Nav>
          {user.pid ? (
            <Dropdown alignRight>
              <Dropdown.Toggle
                className="dropdown"
                menuAlign="left"
                id="dropdown-menu-align-right"
              >
                <div className="avatar-div">
                  <Image id="image-avatar" className="avatar" src={avatar} />
                  <p className="eu-avatar">Eu</p>
                </div>
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <Dropdown.Item href={`/profile/${user.type}/${user.pid}`}>
                  Meu Perfil
                </Dropdown.Item>
                <Dropdown.Item onClick={handleClick} href="/login">
                  Logout
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          ) : (
            <Link className="nav-item" to="/login">
              Login
            </Link>
          )}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default NavBar;
