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
      <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Laborum quam eligendi officiis? Quo enim eius magnam numquam modi. Voluptas, et in fuga voluptate repellat accusamus voluptatem voluptatum hic excepturi tempore.</p>
      <p>Autem quod saepe iste excepturi eaque eveniet, qui aperiam aliquid? Mollitia exercitationem ducimus fuga! Ratione alias voluptate dolor, dolores a eligendi libero ea quas nesciunt sequi ullam natus illo fuga.</p>
      <p>Vel adipisci vitae, unde dicta iure illo atque, fuga mollitia nihil excepturi voluptatum eveniet rerum quae laborum magnam minus. Quaerat, rerum quasi officia tempora numquam incidunt iste quis inventore harum?</p>
      <p>Voluptates vitae beatae sed, veritatis tempora voluptate! Sint minima molestias ducimus consequatur sapiente possimus quod harum tempora consectetur aut magnam, totam fuga alias dolores aperiam quibusdam eveniet ea quisquam corrupti.</p>
      <p>Ipsum aut iusto expedita qui dicta veritatis, autem .</p>
    </Main>
  );
};

export default PagesHome;
