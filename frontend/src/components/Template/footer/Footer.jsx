import React from 'react';
import './Footer.css';
import logo from '../../../assets/images/Logo2RecruIT.png';
import facebookLogo from '../../../assets/images/facebook.png';
import linkedinLogo from '../../../assets/images/linkedin.png';
import twitterLogo from '../../../assets/images/twitter.png';

export default () => (
  <footer className="footer">
    <div className="col-footer-info">
      <span className="titulo-principal">RecruIT</span>
      <p className="texto">Sobre nós</p>
      <p className="texto">Contatos</p>
      <p className="texto">Termos</p>
    </div>
    <div className="col-footer-redesSociais">
      <span className="titulo-redesSociais">Redes Sociais</span>
      <a href="https://facebook.com" className="footerFacebookLink">
        <img className="logoFooterPng" src={facebookLogo} alt="Logo Facebook" />
      </a>
      <a href="https://linkedin.com" className="footerLink">
        <img className="logoFooterPng" src={linkedinLogo} alt="Logo LinkedIn" />
      </a>
      <a href="https://twitter.com" className="footerLink">
        <img className="logoFooterPng" src={twitterLogo} alt="Logo Twitter" />
      </a>
    </div>
    <div className="col-footer-logo">
      <img className="logo-footer" src={logo} alt="Logo RecruIT" />
    </div>
    <div className="col-footer-direitos">
      <p className="texto">RecruIT © 2020</p>
      <p className="texto">Todos os Direitos Reservados</p>
    </div>
  </footer>
);
