import React from 'react';
import { Container } from 'react-bootstrap';

import './User.css';

const User = ({ imgSrc, name, title }) => {
  return (
    <Container className="user text-center mb-3">
      <img
        src={imgSrc}
        alt={name}
        id="user-picture"
      />
      <h4 id="user-name" className="font-weight-bold">
        {name}
      </h4>
      <h5 id="user-title" className="font-weight-light">
        {title}
      </h5>
    </Container>
  );
};

export default User;
