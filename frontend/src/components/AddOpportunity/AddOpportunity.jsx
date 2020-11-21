import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import {
  Card,
  Col,
  Form,
  Row,
  Button,
  Modal,
  ListGroup
} from "react-bootstrap";
import ModalForm from "../../components/Modal/ModalForm";
import { skillOptions } from "../../utils/skills";
import useFetch from "../../Hooks/useFetch";
import { CREATE_OPPORTUNITY } from "../../APIs/APIs";
import Error from "../../components/Helper/Error";
import SucessSVG from "../../assets/images/register/sucessRegister.svg";

import "./AddOpporunity.css";

const schema = {
  name: {
    label: "Habilidade",
    required: true,
    type: "text"
  },
  experienceYears: {
    label: "Anos de Experiência",
    required: true,
    type: "number"
  },
  knowledgeLevel: {
    label: "Nível de Conhecimento",
    required: true,
    as: "select",
    options: skillOptions
  }
};

const AddOpportunity = (props) => {
  const history = useHistory();
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({ active: true });
  const [showModal, setShowModal] = useState(false);
  const [requirements, setRequirements] = useState([]);
  const [error, setError] = useState(null);
  const [errorCadastro, setErrorCadastro] = useState(null);
  const [editIndex, setEditIndex] = useState(undefined);
  const { loading, request } = useFetch();
  const [messageSucess, setMessageSucess] = useState(false);

  const replaceHash = (history) => {
    if (history.location?.hash) history.replace(history.location.pathname);
  };

  const onHandleSubmit = async (event) => {
    event.preventDefault();
    if (!event.target[7]) return;

    const form = event.currentTarget;
    setValidated(true);
    if (form.checkValidity() === false) {
      return event.stopPropagation();
    }

    const body = { ...data };
    body.requirements = [...requirements];

    const { url, options } = CREATE_OPPORTUNITY(body);
    const { response, json } = await request(url, options);
    if (response?.ok) {
      setMessageSucess(true);
      setData({ active: true });
      setTimeout(() => {
        setMessageSucess(false);
        history.push("/listOpportunity");
      }, 3000);
    } else {
      setError(json);
    }
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

  const onHide = () => {
    if (editIndex >= 0) setEditIndex(undefined);
    setShowModal(false);
    setError(undefined);
    replaceHash(history);
  };

  const renderModal = () => {
    return (
      <ModalForm
        show={showModal}
        onHide={onHide}
        schema={schema}
        title={editIndex ? "Editar item" : "Adicionar item"}
        values={editIndex >= 0 ? requirements[editIndex] : undefined}
        error={error}
        list={requirements}
        setList={setRequirements}
        onChangeList={handleOnChange}
      />
    );
  };

  const handleRemove = async (i) => {
    if (!window.confirm("Deseja realmente apagar este item?")) return;
    const newRequirements = [...requirements];
    newRequirements.splice(i, 1);
    setRequirements(newRequirements);
  };

  const handleShowEdit = (i) => {
    setEditIndex(i);
    setShowModal(true);
  };

  return (
    <>
      <Card>
        <Card.Body>
          <Card.Title>Adiconar vaga</Card.Title>
          <Form noValidate validated={validated} onSubmit={onHandleSubmit}>
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
                placeholder="Descrição da"
                as="textarea"
                rows={5}
                required
                onChange={(e) => handleOnChange("description", e.target.value)}
              />
              <Card.Title className="d-flex justify-content-between align-items-center">
                Requisitos {renderAddButton()}
              </Card.Title>
              <ListGroup className="card-requirements">
                {requirements &&
                  requirements.map((req, index) => {
                    return (
                      <ListGroup.Item
                        key={index}
                        className="px-0 d-flex justify-content-between align-items-top"
                      >
                        <p>
                          <strong>Habilidade: </strong>
                          {req.name}
                          <br />
                          <strong>Experiencia: </strong>
                          {req.experienceYears}
                          <br />
                          <strong>Nível: </strong>
                          {req.knowledgeLevel}
                          <br />
                        </p>
                        <div className="flex-shrink-0 ml-2">
                          <Button
                            variant="outline-primary"
                            className="btn-profile-edit-item mr-1"
                            onClick={() => handleShowEdit(index)}
                            title="Editar item"
                          >
                            <i className="far fa-edit"></i>
                          </Button>
                          <Button
                            variant="outline-danger"
                            className="btn-profile-remove"
                            onClick={() => handleRemove(index)}
                            title="Remover item"
                          >
                            <i className="far fa-trash-alt"></i>
                          </Button>
                        </div>
                      </ListGroup.Item>
                    );
                  })}
              </ListGroup>
              {renderModal()}
              <Form.Control.Feedback type="invalid">
                É necessário fornecer uma descrição para a vaga
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="benefits">
              <Form.Label>Benefícios</Form.Label>
              <Form.Control
                as="textarea"
                rows={5}
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
                placeholder="Salário"
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
            <Button type="submit" variant="primary">
              Salvar
            </Button>
            <Error error={errorCadastro} />
          </Form>
        </Card.Body>
      </Card>
      <Modal
        show={messageSucess}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <p className="sucess-register">
          Cadastro com <strong>Sucesso!</strong>
        </p>
        <img
          className="sucess-register-img"
          src={SucessSVG}
          alt="Cadastrato com sucesso."
        ></img>
      </Modal>
    </>
  );
};

export default AddOpportunity;
