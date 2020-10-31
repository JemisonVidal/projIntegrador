import React from "react";
import { Button, Container, Modal, Row } from "react-bootstrap";
import Input from "../Input/Input";

const ModalForm = ({ title, data, show, onHide, setDados, onClickSalvar }) => {
  function handleOnChange(event) {
    const key = event.target.name;
    const value = event.target.value;

    let newDados = { ...data };
    newDados[key] = value;

    setDados({ ...newDados });
  }

  return (
    <Modal
      title={title}
      show={show}
      onHide
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body className="show-grid">
        <Container>
          <Row>
            {Object.entries(data).map(([key, value]) => (
              <Input
                key={key}
                namelabel={key}
                type="text"
                required
                value={value}
                onChange={handleOnChange}
              />
            ))}
          </Row>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={onHide} variant="secundary">
          Cancelar
        </Button>
        <Button onClick={onClickSalvar} variant="primary">
          Salvar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ModalForm;
