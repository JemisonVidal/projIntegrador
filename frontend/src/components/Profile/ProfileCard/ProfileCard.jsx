import React from 'react';
import { Card, Container } from 'react-bootstrap';
import './ProfileCard.css';

const ProfileCard = ({ title, canEdit, handleClick, children }) => {


  return (
    <Container fluid="md" className="py-2">
      <Card className="card-profile">
        <Card.Body className="p-2 p-md-3">
          {title && (
            <Card.Title className="d-flex justify-content-between align-items-center">
              {title}
              {canEdit && (
                <button className="btn-profile-edit" onClick={handleClick}>
                  <i className="far fa-edit"></i>
                </button>
              )}
            </Card.Title>
          )}
          {children}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default ProfileCard;
