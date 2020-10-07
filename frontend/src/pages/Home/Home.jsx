import React from 'react';
import { Carousel } from 'react-bootstrap';
import { useHistory, Link } from 'react-router-dom';
import Main from '../../components/Template/main/Main';
import imageMain from '../../assets/images/main-photo.jpg'
import logo from '../../assets/images/Logo2RecruIT.png'
import Nav from '../../components/Template/nav/Nav'


import './Home.css';

const PagesHome = () => {
  var image = 'http://placekitten.com/300/300';
  return (
    <Main>
      <Nav>
        <Link className="link-nav" to={'/profile/tipo/id'}>
          <img className="linkNavPhoto" src={image}></img>
          <h4>Rebeca Souza</h4>
        </Link>
      </Nav>
      <div className="div-banner-home">
        <img className="image-banner-home" src={imageMain} />
        <img className="logo-banner-home" src={logo} />
        <h1>Recru<span>IT</span></h1>
        <p>Descubra e seja descoberto</p>
      </div>
      <div>
        <Carousel className="carousel" indicators={true}>
          <Carousel.Item>
            <div className="divCarousel">
              <a href="" className="titleCarousel">Adicionar Habilidades</a>
            </div>
          </Carousel.Item>
          <Carousel.Item>
            <div className="divCarousel">
              <a href="" className="titleCarousel">Adicionar Cursos</a>
            </div>
          </Carousel.Item>
          <Carousel.Item>
            <div className="divCarousel">
              <a href="" className="titleCarousel">Adicionar Experiências</a>
            </div>
          </Carousel.Item>
          <Carousel.Item>
            <div className="divCarousel">
              <a href="" className="titleCarousel">Adicionar Links Externos</a>
            </div>
          </Carousel.Item>
        </Carousel>
      </div>
    </Main >
  );
};

export default PagesHome;
