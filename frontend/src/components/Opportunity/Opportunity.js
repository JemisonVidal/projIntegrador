import React, { useState, useEffect, useContext } from "react";
import { Container, CardDeck, Card, Spinner, Modal } from "react-bootstrap";
import { useHistory, Link } from "react-router-dom";
import StoreContext from "../../components/Store/Context";
import Main from "../Template/main/Main";
import useFetch from "../../Hooks/useFetch";
import {
  GET_OPPORTUNITY,
  POST_APPLY,
  GET_APPLICANTS_OPPORTUNITY
} from "../../APIs/APIs";
import heart from "../../assets/images/Heart.svg";
import heartFill from "../../assets/images/FilledHeart.svg";
import editSVG from "../../assets/images/opportunity/editar.svg";
import back from "../../assets/images/back.svg";
import SucessApplySVG from "../../assets/images/opportunity/sucessApply.svg";
import ConfirmationSVG from "../../assets/images/opportunity/confirmation.svg";
import SimpleImage from "../../assets/images/businesswoman-blue.svg";
import LinkedinLogo from "../../assets/images/linkedin-applicants.svg";
import GithubLogo from "../../assets/images/github-applicants.svg";
import WhatsappLogo from "../../assets/images/whats-applicants.svg";

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
  const [applicants, setApplicants] = useState([]);

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

    async function getApplicantsOpportunity() {
      const { url, options } = GET_APPLICANTS_OPPORTUNITY(id);
      const { json, response } = await request(url, options);
      if (response.ok) {
        setApplicants(json);
      }
    }

    getOpportunity();
    getApplicantsOpportunity();
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

  function renderEdit() {
    if (user.pid !== opportunity.companyId) return;
    return (
      <Link
        className="buttonSelect buttonYes"
        to={{
          pathname: `/addOpportunity/`,
          state: {
            action: "edit",
            idOpportunity: opportunity.id
          }
        }}
      >
        <button className="buttonRemove">
          <img className="heartIco" src={editSVG} alt="Candidatar" />
        </button>
      </Link>
    );
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
            {user.pid === opportunity.companyId && renderApplicants()}
          </Card>
        </CardDeck>
        <div className="buttonsCandidatar">
          <button
            onClick={() => history.goBack()}
            className="buttonSelect buttonNo"
          >
            <img className="xIco" src={back} alt="Retornar" />
          </button>
          {user.type === "applicant" ? (
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
          ) : (
            renderEdit()
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

  function renderApplicants() {
    return (
      <Container id="container-opportunity">
        <h2 className="applicants-opportunity">Candidatas inscritas</h2>
        <CardDeck className="deck-opportunity">
          {renderApplicantCard()}
        </CardDeck>
      </Container>
    );
  }

  function renderApplicantCard() {
    return (
      applicants &&
      applicants.map((applicant) => {
        const url = `https://api.whatsapp.com/send?phone=55${applicant.phoneNumber}&text=Ol%C3%A1%2C%20tudo%20bem%20%3F%20Encontrei%20seu%20perfil%20no%20RecruIT.%20Podemos%20conversar%20%3F`;
        const urlPerfil = `/profile/applicant/${applicant.id}`;
        return (
          <div key={applicant.id}>
            <Card className="cardApplicants">
              <div>
                <div className="imgApplicants">
                  <Link to={urlPerfil}>
                    <img
                      className="srcApplicants"
                      src={applicant.imgSrc || SimpleImage}
                      alt="Foto do perfil"
                    ></img>
                  </Link>
                </div>
                <div className="informationApplicants">
                  <h6 className="nameApplicants">{applicant.name}</h6>
                  <p className="descriptionApplicants">{applicant.location}</p>
                  <div className="divButtonApplicants">
                    <a
                      href={applicant.phoneNumber && url}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="footerLink"
                    >
                      <img
                        className="logoFooterPng"
                        src={WhatsappLogo}
                        alt="WhatsApp"
                      />
                    </a>
                    <a
                      href={applicant.linkedin}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="footerLink"
                    >
                      <img
                        className="logoFooterPng"
                        src={LinkedinLogo}
                        alt="LinkedIn"
                      />
                    </a>
                    <a
                      href={applicant.github}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="footerLink"
                    >
                      <img
                        className="logoFooterPng"
                        src={GithubLogo}
                        alt="GitHub"
                      />
                    </a>
                  </div>
                </div>
              </div>
            </Card>
          </div>
        );
      })
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
