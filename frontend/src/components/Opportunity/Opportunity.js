import React, { useState, useEffect } from "react";
import { Container, CardDeck, Card, Spinner, Modal } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Main from "../Template/main/Main";
import useFetch from "../../Hooks/useFetch";
import { GET_OPPORTUNITY, POST_APPLY } from "../../APIs/APIs";
import heart from "../../assets/images/Heart.svg";
import heartFill from "../../assets/images/FilledHeart.svg";
import back from "../../assets/images/back.svg";
import SucessApplySVG from "../../assets/images/opportunity/sucessApply.svg";

import "./Opportunity.css";
import { currencyFormatter } from "../../utils/formatters";
import { skillMap } from "../../utils/skills";

const ListOpportunity = ({ id }) => {
  const { request, loading } = useFetch();
  const fetchApply = useFetch();
  const history = useHistory();
  const [opportunity, setOpportunity] = useState({});
  const [heartCheck, setHeartCheck] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    async function getOpportunity() {
      const { url, options } = GET_OPPORTUNITY(id);
      const { json, response } = await request(url, options);
      if (response.ok) {
        setOpportunity(json);
        setHeartCheck(json.isApplied);
      }
    }
    getOpportunity();
  }, [request]);

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
      </div>
    );
  }

  const handlerBackClick = () => {
    history.push("/listOpportunity");
  };

  const handlerHeartClick = async () => {
    const { response } = await apply(opportunity.id);
    if (response?.ok) {
      setHeartCheck(true);
      setModalOpen(true);
      setTimeout(() => {
        setModalOpen(false);
      }, 3000);
    }
  };

  async function apply(id) {
    const { url, options } = POST_APPLY(id);
    return await fetchApply.request(url, options);
  }

  function onHideModal() {
    setModalOpen(false);
  }

  function renderOpportunity() {
    return (
      <Container>
        <CardDeck key={opportunity.id}>
          <Card className="card">
            <Card.Body>
              <Card.Title className="title-card-opportunity">
                {opportunity.name}
              </Card.Title>
              <Card.Text className="title-empresa-opportunity">
                {opportunity.companyName}
              </Card.Text>
              <span className="titulo-campo-opportunity">Localização</span>
              <Card.Text>
                <i class="fa fa-map-marker" aria-hidden="true"></i>{" "}
                {opportunity.location}
              </Card.Text>
              <span className="titulo-campo-opportunity">
                Descrição da vaga
              </span>
              <Card.Text>{opportunity.description}</Card.Text>
              <span className="titulo-campo-opportunity">Requisitos</span>
              <Card.Text>
                {opportunity.requirements &&
                  opportunity.requirements
                    .map(
                      (requirement) =>
                        `${requirement.name} ${
                          skillMap[requirement.knowledgeLevel]
                        }`
                    )
                    .join(", ")}
              </Card.Text>
              <span className="titulo-campo-opportunity">Salário</span>
              <Card.Text>
                {" "}
                {opportunity.salary && currencyFormatter(opportunity.salary)}
              </Card.Text>
              <span className="titulo-campo-opportunity">Benefícios</span>
              <Card.Text>{opportunity.benefits}</Card.Text>
              <span className="titulo-campo-opportunity">
                Informações Adicionais
              </span>
              <Card.Text>{opportunity.text}</Card.Text>
            </Card.Body>
          </Card>
        </CardDeck>
        <div className="buttonsCandidatar">
          <button onClick={handlerBackClick} className="buttonSelect buttonNo">
            <img className="xIco" src={back} alt="Retornar" />
          </button>
          <button
            onClick={handlerHeartClick}
            disabled={heartCheck}
            className="buttonSelect buttonYes"
          >
            {fetchApply.loading ? (
              <Spinner animation="border" />
            ) : (
              <img
                className="heartIco"
                src={heartCheck ? heartFill : heart}
                alt="Candidatar"
              />
            )}
          </button>
        </div>
        <Modal
          show={modalOpen}
          onHide={onHideModal}
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <p className="sucess-apply">
            Agora você está participando desta Vaga!
            <br />
            <strong>BOA SORTE !</strong>
          </p>
          <img className="sucess-apply-img" src={SucessApplySVG}></img>
        </Modal>
      </Container>
    );
  }

  return (
    <Main>
      <Container fluid="md" className="py-2">
        {loading ? renderLoading() : renderOpportunity()}
      </Container>
    </Main>
  );
};

export default ListOpportunity;
