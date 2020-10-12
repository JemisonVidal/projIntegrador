import React from 'react';
import { Col, Row } from 'react-bootstrap';

const ProfileCardItem = ({ title, value }) => {
  return (
    <Row className="profile-item">
      <Col sm={12} md="auto">
        <span className="font-weight-bold">{title}</span>
      </Col>
      <Col sm={12} md>
        {typeof value === 'string' || typeof value === 'number' ? (
          <span>{value}</span>
        ) : (
            value.map((v, i) => (
              <span key={i} className="d-block">
                {v}
              </span>
            ))
          )}
      </Col>
    </Row>
  );
};

export default ProfileCardItem;
