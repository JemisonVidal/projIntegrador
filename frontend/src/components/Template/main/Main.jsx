import React, { Children } from 'react';
import './Main.css';
import Logo from '../logo/Logo';
import Nav from '../nav/Nav';
import Header from '../header/Header';
import Footer from '../footer/Footer';
import logo from '../../../assets/images/LogoRecruIT.png'

export default props =>
    <React.Fragment>
        <Logo />
        <Nav />
        <Header {...props} />
        <main className="content container-box-home">
            {/* <img className="image-background" src={logo} /> */}
            <div className="container form-box-home">
                {props.children}
            </div>
        </main>
        <Footer />
    </React.Fragment>
