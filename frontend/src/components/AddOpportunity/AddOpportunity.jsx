import React, { useEffect, useState } from "react";
import { useHistory, useLocation } from "react-router-dom";
import {
  Card,
  Col,
  Form,
  Row,
  Button,
  Modal,
  ListGroup,
  Container,
  CardDeck,
  Spinner
} from "react-bootstrap";
import Checkbox from "../../components/Checkbox/Checkbox";
import ModalForm from "../../components/Modal/ModalForm";
import Input from "../../components/Input/Input";
import { skillMap, skillOptions } from "../../utils/skills";
import useFetch from "../../Hooks/useFetch";
import useForm from "../../Hooks/useForm";
import {
  CREATE_OPPORTUNITY,
  GET_OPPORTUNITY,
  PATCH_OPPORTUNITY
} from "../../APIs/APIs";
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
  const location = useLocation();
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({ active: true });
  const [showModal, setShowModal] = useState(false);
  const [requirements, setRequirements] = useState([]);
  const [error, setError] = useState(null);
  const [errorCadastro, setErrorCadastro] = useState(null);
  const [editIndex, setEditIndex] = useState(undefined);
  const { loading, request } = useFetch();
  const [messageSucess, setMessageSucess] = useState(false);
  const [active, setActive] = useState(true);

  const titulo = useForm(false);
  const localizacao = useForm(false);
  const descricao = useForm(false);
  const beneficios = useForm(false);
  const salario = useForm(false);
  const observacao = useForm(false);

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
    async function getOpportunity() {
      if (location.state?.action === "edit") {
        const { url, options } = GET_OPPORTUNITY(location.state.idOpportunity);
        const { json, response } = await request(url, options);
        if (response.ok) {
          titulo.setValue(json.name);
          localizacao.setValue(json.location);
          descricao.setValue(json.description);
          beneficios.setValue(json.benefits);
          salario.setValue(json.salary);
          observacao.setValue(json.text);
          setActive(json.active);
          setRequirements(json.requirements);
        }
      }
    }

    getOpportunity();
  }, []);

  function buildBody() {
    return {
      active,
      benefits: beneficios.value,
      description: descricao.value,
      location: localizacao.value,
      name: titulo.value,
      requirements,
      salary: salario.value,
      text: observacao.value
    };
  }

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

    let url,
      options = "";
    if (location.state?.action === "edit") {
      const response = PATCH_OPPORTUNITY(
        location.state.idOpportunity,
        buildBody()
      );
      url = response.url;
      options = response.options;
    } else {
      const response = CREATE_OPPORTUNITY(buildBody());
      url = response.url;
      options = response.options;
    }

    const { response, json } = await request(url, options);
    if (response?.ok) {
      setMessageSucess(true);
      setData({ active: true });
      if (location.state?.action === "edit") {
        history.push(`/opportunity/${location.state?.idOpportunity}`);
      } else {
        const location = response.headers.get("Location");
        const codigoVaga = location.substring(42, location.length);
        history.push(`/opportunity/${codigoVaga}`);
      }
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
        indexRequirement={editIndex}
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

  if (loading) {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
        <span className="sr-only">Loading...</span>
      </div>
    );
  }

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
                    <Form.Group className="groupAddOpportunity">
                      <Input
                        namelabel="Nome"
                        classes={{
                          label: "titleGroup",
                          input: "inputAddOpportunity"
                        }}
                        feedback="O nome da vaga é obrigatório"
                        type="text"
                        placeholder="Nome"
                        required
                        {...titulo}
                      />
                      <Form.Control.Feedback type="invalid">
                        O nome da vaga é obrigatório
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                  <Col>
                    <Form.Group className="groupAddOpportunity">
                      <Input
                        namelabel="Localização"
                        classes={{
                          label: "titleGroup",
                          input: "inputAddOpportunity"
                        }}
                        feedback="A localização da vaga é obrigatório"
                        type="text"
                        placeholder="Cidade"
                        required
                        {...localizacao}
                      />
                      <Form.Control.Feedback type="invalid">
                        A localização da vaga é obrigatório
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                </Row>
                <Form.Group className="groupAddOpportunity">
                  <Input
                    namelabel="Descrição"
                    classes={{
                      label: "titleGroup",
                      input: "inputAddOpportunity"
                    }}
                    feedback="A descrição da vaga é obrigatório"
                    type="textarea"
                    rows={5}
                    placeholder="Descrição Completa"
                    required
                    {...descricao}
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
                </Form.Group>
                <Form.Group className="groupAddOpportunity">
                  <Input
                    namelabel="Benefícios"
                    classes={{
                      label: "titleGroup",
                      input: "inputAddOpportunity"
                    }}
                    placeholder="Benefícios"
                    required
                    type="textarea"
                    rows={5}
                    {...beneficios}
                  />
                </Form.Group>
                <Form.Group className="groupAddOpportunity">
                  <Input
                    namelabel="Salário"
                    classes={{
                      label: "titleGroup",
                      input: "inputAddOpportunity"
                    }}
                    type="text"
                    placeholder="Salário"
                    required
                    {...salario}
                  />
                </Form.Group>
                <Form.Group className="groupAddOpportunity">
                  <Input
                    namelabel="Observações (Texto livre)"
                    classes={{
                      label: "titleGroup",
                      input: "inputAddOpportunity"
                    }}
                    placeholder="Texto livre da oportunidade"
                    required
                    type="textarea"
                    rows={5}
                    {...observacao}
                  />
                </Form.Group>
                <Checkbox
                  className="CheckBoxMin"
                  type="checkbox"
                  checked={active}
                  onClick={() => setActive(!active)}
                  label="Ativo"
                />
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
