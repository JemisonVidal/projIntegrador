import React from 'react';
import Main from '../../components/Template/main/Main';
import imageMain from '../../assets/images/main-photo.jpg'
import logo from '../../assets/images/Logo2RecruIT.png'

import './Home.css';

const PagesHome = () => {
  return (
    <Main>
      <div className="div-banner-home">
        <img className="image-banner-home" src={imageMain} />
        <img className="logo-banner-home" src={logo} />
        <h1>Recru<span>IT</span></h1>
        <p>Descubra e seja descoberto</p>
      </div>
    </Main>
  );
};

export default PagesHome;
