import React, { Children } from 'react';
import './Main.css';
import Footer from '../footer/Footer';
import logo from '../../../assets/images/LogoRecruIT.png'

export default props =>
    <React.Fragment>
        <main className="content container-box-home">
            <div className="container-image-background">
                <div className="container form-box-home">
                    {props.children}
                    <Footer />
                </div>
            </div>
        </main>

    </React.Fragment>
