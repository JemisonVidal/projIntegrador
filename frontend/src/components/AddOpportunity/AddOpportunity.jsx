import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import {
  Card,
  Col,
  Form,
  Row,
  Button,
  Modal,
  ListGroup,
  Container,
  CardDeck
} from "react-bootstrap";
import ModalForm from "../../components/Modal/ModalForm";
import { skillMap, skillOptions } from "../../utils/skills";
import useFetch from "../../Hooks/useFetch";
import { CREATE_OPPORTUNITY } from "../../APIs/APIs";
import Error from "../../components/Helper/Error";
import SucessSVG from "../../assets/images/register/sucessRegister.svg";

import "./AddOpportunity.css";

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
      const location = response.headers.get("Location");
      const codigoVaga = location.substring(42, location.length);
      history.push(`/opportunity/${codigoVaga}`);
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

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
  }, []);

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
      <Container id="container-opportunity">
        <CardDeck>
          <Card className="card">
            <Card.Body id="card-opportunity">
              <Card.Title className="titleAddOportunity">
                Adicionar Oportunidade
              </Card.Title>
              <Form noValidate validated={validated} onSubmit={onHandleSubmit}>
                <Row>
                  <Col>
                    <Form.Group
                      className="groupAddOpportunity"
                      controlId="name"
                    >
                      <Form.Label className="titleGroup">
                        Nome<span className="text-danger">*</span>
                      </Form.Label>
                      <Form.Control
                        className="inputAddOpportunity"
                        placeholder="Título"
                        required
                        onChange={(e) => handleOnChange("name", e.target.value)}
                      />
                      <Form.Control.Feedback type="invalid">
                        O nome da vaga é obrigatório
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                  <Col>
                    <Form.Group
                      className="groupAddOpportunity"
                      controlId="location"
                    >
                      <Form.Label className="titleGroup">
                        Localização<span className="text-danger">*</span>
                      </Form.Label>
                      <Form.Control
                        className="inputAddOpportunity"
                        placeholder="Cidade"
                        required
                        onChange={(e) =>
                          handleOnChange("location", e.target.value)
                        }
                      />
                      <Form.Control.Feedback type="invalid">
                        A localização da vaga é obrigatório
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                </Row>
                <Form.Group
                  className="groupAddOpportunity"
                  controlId="description"
                >
                  <Form.Label className="titleGroup">
                    Descrição<span className="text-danger">*</span>
                  </Form.Label>
                  <Form.Control
                    className="inputAddOpportunity"
                    placeholder="Descrição Completa"
                    as="textarea"
                    rows={5}
                    required
                    onChange={(e) =>
                      handleOnChange("description", e.target.value)
                    }
                  />
                  <Card.Title className="d-flex justify-content-between align-items-center divRequisitos">
                    <span className="tituloRequisitos">Requisitos</span>{" "}
                    {renderAddButton()}
                  </Card.Title>
                  <ListGroup className="card-requirements">
                    {requirements &&
                      requirements.map((req, index) => {
                        return (
                          <ListGroup.Item
                            key={index}
                            className="px-0 d-flex justify-content-between align-items-top groupExperience"
                          >
                            <p>
                              <strong>Habilidade: </strong>
                              {req.name}
                              <br />
                              <strong>Experiencia: </strong>
                              {req.experienceYears}{" "}
                              {req.experienceYears > 1 ? "anos" : "ano"} de
                              experiência
                              <br />
                              <strong>Nível: </strong>
                              {skillMap[req.knowledgeLevel]}
                              <br />
                            </p>
                            <div className="flex-shrink-0 ml-2 editRequisitos">
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
                <Form.Group
                  className="groupAddOpportunity"
                  controlId="benefits"
                >
                  <Form.Label className="titleGroup">Benefícios</Form.Label>
                  <Form.Control
                    className="inputAddOpportunity"
                    as="textarea"
                    rows={5}
                    placeholder="Benefícios da oportunidade"
                    onChange={(e) =>
                      handleOnChange("benefits", e.target.value || null)
                    }
                  />
                </Form.Group>
                <Form.Group className="groupAddOpportunity" controlId="salary">
                  <Form.Label className="titleGroup">Salário</Form.Label>
                  <Form.Control
                    className="inputAddOpportunity"
                    type="number"
                    placeholder="Salário da oportunidade"
                    onChange={(e) =>
                      handleOnChange("salary", Number(e.target.value || 0))
                    }
                  />
                </Form.Group>
                <Form.Group className="groupAddOpportunity" controlId="text">
                  <Form.Label className="titleGroup">
                    Observações (Texto livre)
                  </Form.Label>
                  <Form.Control
                    className="inputAddOpportunity"
                    placeholder="Texto livre da oportunidade"
                    as="textarea"
                    rows={5}
                    onChange={(e) => handleOnChange("text", e.target.value)}
                  />
                </Form.Group>
                <Button className="buttonSave" type="submit" variant="primary">
                  Salvar
                </Button>
                <Error error={errorCadastro} />
              </Form>
            </Card.Body>
          </Card>
        </CardDeck>
      </Container>
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
