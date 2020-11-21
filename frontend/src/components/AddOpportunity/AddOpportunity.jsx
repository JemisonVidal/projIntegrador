import React, { useState, useEffect } from "react";
import { Card, Col, Form, Row, Button } from "react-bootstrap";

const AddOpportunity = (props) => {
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({
    name: null,
    location: null,
    description: null,
    benefits: null,
    salary: null,
    text: null,
    active: false,
    requirements: []
  });

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

  return (
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
          <Button type="submit" variant="primary">
            Salvar
          </Button>{" "}
          <Button variant="danger">Cancelar</Button>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default AddOpportunity;
