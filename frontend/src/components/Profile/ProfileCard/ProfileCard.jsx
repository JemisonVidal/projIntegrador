import React from 'react';
import { Card, Container } from 'react-bootstrap';

import './ProfileCard.css';
import ProfileCardItem from './ProfileCardItem/ProfileCardItem';

const ProfileCard = ({ title, data, canEdit }) => {
  return (
    <Container fluid="md" className="py-2">
      <Card className="card-profile">
        <Card.Body className="p-2 p-md-3">
          {title && (
            <Card.Title className="d-flex justify-content-between align-items-center">
              {title}
              {canEdit && (
                <button className="btn">
                  <i className="far fa-edit"></i>
                </button>
              )}
            </Card.Title>
          )}

          {Object.entries(data).map(([key, value]) => (
            <ProfileCardItem key={key} title={key} value={value} />
          ))}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default ProfileCard;
