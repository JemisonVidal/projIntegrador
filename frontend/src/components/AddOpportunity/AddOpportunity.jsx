import React, { useState } from "react";
import { Card, Col, Form, Row, Button, Modal } from "react-bootstrap";
import StyledCard from "../StyledCard/StyledCard";

const AddOpportunity = (props) => {
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({});
  const [showModal, setShowModal] = useState(false);
  const [requirements, setRequirements] = useState([]);

  const handleSubmit = (event) => {
    const form = event.currentTarget;

    console.log("Olá mundo!!");
    console.log(data);

    event.preventDefault();

    if (form.checkValidity() === false) {
      event.stopPropagation();
    }

    setValidated(true);
  };

  const handleOnChange = (key, value) => {
    data[key] = value;
    setData({ ...data });
  };

  const renderAddButton = () => (
    <Button
      variant="outline-primary"
      className="btn-profile-add mb-1"
      onClick={() => setShowModal(true)}
    >
      <i className="fas fa-plus mr-1"></i>
      Adicionar
    </Button>
  );

  const renderModal = () => (
    <Modal show={showModal} onHide={() => setShowModal(false)}>
      <Modal.Header closeButton>
        <Modal.Header>Adicionar requisito</Modal.Header>
      </Modal.Header>
      <Modal.Footer>
        <Button variant="secondary" onClick={() => setShowModal(true)}>
          Close
        </Button>
        <Button variant="primary" onClick={() => setShowModal(false)}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );

  return (
    <>
      <Card>
        <Card.Body>
          <Card.Title>Adiconar vaga</Card.Title>
          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            <Row>
              <Col>
                <Form.Group controlId="name">
                  <Form.Label>
                    Nome<span className="text-danger">*</span>
                  </Form.Label>
                  <Form.Control
                    placeholder="Título da vaga"
                    required
                    onChange={(e) => handleOnChange("name", e.target.value)}
                  />
                  <Form.Control.Feedback type="invalid">
                    O nome da vaga é obrigatório
                  </Form.Control.Feedback>
                </Form.Group>
              </Col>
              <Col>
                <Form.Group controlId="location">
                  <Form.Label>
                    Localização<span className="text-danger">*</span>
                  </Form.Label>
                  <Form.Control
                    placeholder="Detalhes da vaga"
                    required
                    onChange={(e) => handleOnChange("location", e.target.value)}
                  />
                  <Form.Control.Feedback type="invalid">
                    A localização da vaga é obrigatório
                  </Form.Control.Feedback>
                </Form.Group>
              </Col>
            </Row>
            <Form.Group controlId="description">
              <Form.Label>
                Descrição<span className="text-danger">*</span>
              </Form.Label>
              <Form.Control
                placeholder="Local da vaga"
                as="textarea"
                rows={5}
                required
                onChange={(e) => handleOnChange("description", e.target.value)}
              />
              <Form.Control.Feedback type="invalid">
                É necessário fornecer uma descrição para a vaga
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="benefits">
              <Form.Label>Benefícios</Form.Label>
              <Form.Control
                placeholder="Benefícios"
                onChange={(e) =>
                  handleOnChange("benefits", e.target.value || null)
                }
              />
            </Form.Group>
            <Form.Group controlId="salary">
              <Form.Label>Salário</Form.Label>
              <Form.Control
                type="number"
                placeholder="Benefícios"
                onChange={(e) =>
                  handleOnChange("salary", Number(e.target.value || 0))
                }
              />
            </Form.Group>
            <Form.Group controlId="text">
              <Form.Label>Observações (Texto livre)</Form.Label>
              <Form.Control
                placeholder="Texto livre"
                as="textarea"
                rows={5}
                onChange={(e) => handleOnChange("text", e.target.value)}
              />
            </Form.Group>
            <Form.Check
              custom
              type="checkbox"
              id="active"
              label="Ativo"
              onChange={(e) => handleOnChange("active", e.target.value)}
            />
            <Card.Title className="d-flex justify-content-between align-items-center">
              Requisitos {renderAddButton()}
            </Card.Title>
            {renderModal()}
            <Button type="submit" variant="primary">
              Salvar
            </Button>{" "}
            <Button variant="danger">Cancelar</Button>
          </Form>
        </Card.Body>
      </Card>
    </>
  );
};

export default AddOpportunity;
