import React, { useState, useEffect, useContext } from "react";
import { Container, CardDeck, Card, Spinner, Modal } from "react-bootstrap";
import { useHistory, Link } from "react-router-dom";
import StoreContext from "../../components/Store/Context";
import Main from "../Template/main/Main";
import useFetch from "../../Hooks/useFetch";
import { GET_OPPORTUNITY, POST_APPLY } from "../../APIs/APIs";
import heart from "../../assets/images/Heart.svg";
import heartFill from "../../assets/images/FilledHeart.svg";
import back from "../../assets/images/back.svg";
import SucessApplySVG from "../../assets/images/opportunity/sucessApply.svg";
import ConfirmationSVG from "../../assets/images/opportunity/confirmation.svg";

import "./Opportunity.css";
import { currencyFormatter } from "../../utils/formatters";
import { skillMap } from "../../utils/skills";

const ListOpportunity = ({ id }) => {
  const { user } = useContext(StoreContext);
  const { request, loading } = useFetch();
  const fetchApply = useFetch();
  const history = useHistory();
  const [opportunity, setOpportunity] = useState({});
  const [heartCheck, setHeartCheck] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [typeMessage, setTypeMessage] = useState(false);

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });

    async function getOpportunity() {
      const { url, options } = GET_OPPORTUNITY(id);
      const { json, response } = await request(url, options);
      if (response.ok) {
        setOpportunity(json);
        setHeartCheck(json.isApplied);
        setTypeMessage(heartCheck);
      }
    }

    getOpportunity();
  }, [request]);

  function renderLoading() {
    return (
      <div className="spinner-load" id="load-opportunity">
        <Spinner animation="border" />
      </div>
    );
  }

  const handlerHeartClick = async () => {
    if (heartCheck) {
      setTypeMessage(heartCheck);

      return setModalOpen(true);
    }

    const { response } = await apply(opportunity.id);
    if (response?.ok) {
      setModalOpen(true);
      setHeartCheck(true);
      setTypeMessage(heartCheck);

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

  const linkCompany = `/profile/company/${opportunity.companyId}`;

  function renderOpportunity() {
    return (
      <Container id="container-opportunity">
        <CardDeck key={opportunity.id}>
          <Card className="card">
            <Card.Body id="card-opportunity">
              <Card.Title className="title-card-opportunity">
                {opportunity.name}
              </Card.Title>
              <Card.Text className="title-empresa-opportunity">
                <Link className="linkToCompany" to={linkCompany}>
                  {opportunity.companyName}
                </Link>
              </Card.Text>
              <span className="titulo-campo-opportunity">Localização</span>
              <Card.Text>
                <i className="fa fa-map-marker" aria-hidden="true"></i>{" "}
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
          <button
            onClick={() => history.goBack()}
            className="buttonSelect buttonNo"
          >
            <img className="xIco" src={back} alt="Retornar" />
          </button>
          {user.type === "applicant" && (
            <button
              onClick={handlerHeartClick}
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
          )}
        </div>
        <Modal
          show={modalOpen}
          onHide={onHideModal}
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <Modal.Header closeButton></Modal.Header>
          {typeMessage ? (
            <>
              <p className="sucess-apply">
                <strong>Você ja está participando dessa vaga!</strong>
                <br />
                Infelizmente não será possível cancelar a inscrição, seus dados
                ja foram enviados ao recrutador.
                <br />
              </p>
              <img
                className="sucess-apply-img"
                src={ConfirmationSVG}
                alt="Candidatura registrada com sucesso."
              />
            </>
          ) : (
            <>
              <p className="sucess-apply">
                Agora você está participando desta Vaga!
                <br />
                <strong>BOA SORTE !</strong>
              </p>
              <img
                className="sucess-apply-img"
                src={SucessApplySVG}
                alt="Candidatura registrada com sucesso."
              />
            </>
          )}
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
