import React from "react";
import "./Footer.css";
import logo from "../../../assets/images/Logo2RecruIT.png";
import facebookLogo from "../../../assets/images/facebook.png";
import linkedinLogo from "../../../assets/images/linkedin.png";
import twitterLogo from "../../../assets/images/twitter.png";
import { Link } from "react-router-dom";

const Footer = () => (
  <footer className="footer">
    <div className="col-footer-info">
      <span className="titulo-principal">RecruIT</span>
      <Link to="/aboutus">
        <p className="texto-footer">Sobre nós</p>
      </Link>
      <Link to="/contacts">
        <p className="texto-footer">Contatos</p>
      </Link>
      <Link to="/terms">
        <p className="texto-footer">Termos</p>
      </Link>
    </div>
    <div className="col-footer-redesSociais">
      <span className="titulo-redesSociais">Redes Sociais</span>
      <a href="https://facebook.com" className="footerFacebookLink">
        <img className="logoFooterPng" src={facebookLogo} alt="Facebook" />
      </a>
      <a href="https://linkedin.com" className="footerLink">
        <img className="logoFooterPng" src={linkedinLogo} alt="LinkedIn" />
      </a>
      <a href="https://twitter.com" className="footerLink">
        <img className="logoFooterPng" src={twitterLogo} alt="Twitter" />
      </a>
    </div>
    <div className="col-footer-logo">
      <img className="logo-footer" src={logo} alt="RecruIT" />
    </div>
    <div className="col-footer-direitos">
      <p className="texto-footer">RecruIT © 2020</p>
      <p className="texto-footer">Todos os Direitos Reservados</p>
    </div>
  </footer>
);

export default Footer;
