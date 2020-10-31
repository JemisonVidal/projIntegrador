import React, { useState } from "react";
import { Card, Container, Button } from "react-bootstrap";
import ModalForm from "../../Modal/ModalForm";
import ProfileCardItem from "./ProfileCardItem/ProfileCardItem";
import "./ProfileCard.css";

const ProfileCard = ({ title, data, canEdit }) => {
  const [modalShow, setModalShow] = React.useState(false);
  const [dados, setDados] = useState(data);

  function handleClick(event) {
    setModalShow(true);
  }

  function handleOnHide() {
    setModalShow(false);
  }

  function handleSalvar() {
    //TODO: FAZER A REQUEST PARA ATUALIZAR OS DADOS
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
        data={dados}
        show={modalShow}
        onHide={handleOnHide}
        setDados={setDados}
        onClickSalvar={handleSalvar}
      />
    </Container>
  );
};

export default ProfileCard;
