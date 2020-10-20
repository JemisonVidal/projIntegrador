import React from 'react';
import { Card, Container, Button } from 'react-bootstrap';
import ModalForm from '../../Modal/ModalForm';
import ProfileCardItem from './ProfileCardItem/ProfileCardItem';
import './ProfileCard.css';

const ProfileCard = ({ title, data, canEdit }) => {
  const [modalShow, setModalShow] = React.useState(false);

  function handleClick(event) {
    setModalShow(true);
  }

  return (
    <Container fluid="md" className="py-2">
      <Card className="card-profile">
        <Card.Body className="p-2 p-md-3">
          {title && (
            <Card.Title className="d-flex justify-content-between align-items-center">
              {title}
              {canEdit && (
                <Button className="btn-profile-edit" onClick={handleClick}>
                  <i className="far fa-edit"></i>
                </Button>
              )}
            </Card.Title>
          )}

          {Object.entries(data).map(([key, value]) => (
            <ProfileCardItem key={key} title={key} value={value} />
          ))}
        </Card.Body>
      </Card>
      <ModalForm
        title={title}
        data={data}
        show={modalShow}
        onHide={() => setModalShow(false)}
      />
    </Container>


  );
};

export default ProfileCard;
