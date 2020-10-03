import React, { Children } from 'react';
import './Main.css';
import Logo from '../logo/Logo';
import Nav from '../nav/Nav';
import Header from '../header/Header';
import Footer from '../footer/Footer';
import logo from '../../../assets/images/LogoRecruIT.png'

export default props =>
    <React.Fragment>
        <main className="content container-box-home">
            <Header {...props} />
            <div className="container-image-background">
                <div className="container form-box-home">
                    {props.children}
                </div>
            </div>
        </main>
        <Footer />
    </React.Fragment>
