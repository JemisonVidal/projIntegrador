import React from 'react'
import { NavDropdown, Form, FormControl, Button } from 'react-bootstrap'
import './Nav.css'
import { Link } from 'react-router-dom'

export default props =>
    <aside className="menu-area">
        <div className="navBar">
            <i className="fas fa-bars" id="menuHamburger"></i>
            <div className="photoNav">
                {props.children}
            </div>
        </div>
    </aside >
