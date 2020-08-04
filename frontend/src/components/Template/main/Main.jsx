import React from 'react';
import './Main.css';
import Logo from '../logo/Logo';
import Nav from '../nav/Nav';
import Header from '../header/Header';
import Footer from '../footer/Footer';

export default props =>
    <React.Fragment>
        <Logo />
        <Nav />
        <Header {...props} />
        <main className="content container-fluid">
            <div className="p-3 mt-3">
                {props.children}
            </div>
        </main>
        <Footer />
    </React.Fragment>
