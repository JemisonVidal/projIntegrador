import React from 'react';
import { Button, Card, Container } from 'react-bootstrap';
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

const Edit = ({ title, handleClick, handleSave, children }) => {
  return (
    <Container fluid="md" className="py-2">
      <Card className="card-profile">
        <Card.Body className="p-2 p-md-3">
          {title && (
            <Card.Title className="d-flex justify-content-between align-items-center">
              {title}
              <button className="btn-profile-edit" onClick={handleClick}>
                <i className="far fa-edit"></i>
              </button>
            </Card.Title>
          )}
          {children}
          <div className="d-flex justify-content-center">
            <Button
              id="save-profile"
              variant="primary"
              type="submit"
              onClick={handleSave}
            >
              Salvar
            </Button>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );
};
ProfileCard.Edit = Edit;

export default ProfileCard;
