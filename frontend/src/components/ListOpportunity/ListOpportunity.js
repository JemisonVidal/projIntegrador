import React, { useState, useEffect, useRef, useContext } from "react";
import { CardDeck, Card, Button, Spinner, Form, Col } from "react-bootstrap";
import useFetch from "../../Hooks/useFetch";
import PaginationPage from "../Pagination/Pagination";
import { GET_LIST_OPPORTUNITY, GET_MY_OPPORTUNITY } from "../../APIs/APIs";
import { Link } from "react-router-dom";
import StoreContext from "../../components/Store/Context";
import { currencyFormatter } from "../../utils/formatters";
import { skillMap } from "../../utils/skills";
import { MY_OPPORTUNITYS } from "./listEnum";

import "./ListOpportunity.css";

const ListOpportunity = ({ type }) => {
  const [totalPages, setTotalPages] = useState(0);
  const [pageCurrent, setPageCurrent] = useState(0);
  const { request, loading } = useFetch();
  const [arrayOpportunity, setArrayOpportunity] = useState([]);
  const searchInput = useRef(null);
  const requirementsInput = useRef(null);
  const { user } = useContext(StoreContext);

  async function getOpportunity() {
    let url;
    let options;
    if (type === MY_OPPORTUNITYS) {
      const payloadMyOpportunity = GET_MY_OPPORTUNITY(user.pid);
      url = payloadMyOpportunity.url;
      options = payloadMyOpportunity.options;
    } else {
      const payloadAllOpportunity = GET_LIST_OPPORTUNITY(
        pageCurrent,
        searchInput.current.value,
        requirementsInput.current.value
      );
      url = payloadAllOpportunity.url;
      options = payloadAllOpportunity.options;
    }

    const { json, response } = await request(url, options);
    if (response.ok) {
      if (type === MY_OPPORTUNITYS) {
        setArrayOpportunity(json);
      } else {
        setArrayOpportunity(json?.content);
        setPageCurrent(json?.pageable?.pageNumber);
        setTotalPages(json?.totalPages);
      }
    }
  }

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
    getOpportunity();
  }, [pageCurrent]);

  async function handleSearchClick(event) {
    event.preventDefault();
    await getOpportunity();
  }

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
      </div>
    );
  }

  function renderListOpportunity(typeSearch) {
    if (arrayOpportunity && arrayOpportunity.length <= 0) {
      return (
        <p className="messageOps">
          Ops... Não encontramos nenhuma vaga com esse nome.
          <br /> :(
          <br />
          Tente novamente !
        </p>
      );
    }

    return (
      arrayOpportunity &&
      arrayOpportunity.map((opportunity) => {
        if (typeSearch === MY_OPPORTUNITYS && !opportunity.isApplied)
          return null;

        return (
          <div key={opportunity.id}>
            <Card className="card h-100">
              <Card.Body>
                <Card.Title className="title-card">
                  {opportunity.name}
                </Card.Title>
                <Card.Text className="title-card-company">
                  {opportunity.companyName}
                </Card.Text>
                <Card.Text>
                  <span className="titulo-campo">Localização:</span>{" "}
                  <i className="fa fa-map-marker" aria-hidden="true"></i>{" "}
                  {opportunity.location}
                </Card.Text>
                <Card.Text>
                  <span className="titulo-campo">Requisitos:</span>{" "}
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
                <Card.Text>
                  <span className="titulo-campo">Benefícios:</span>{" "}
                  {opportunity.benefits}
                </Card.Text>
                <Card.Text>
                  <span className="titulo-campo">Salário:</span>{" "}
                  {currencyFormatter(opportunity.salary)}
                </Card.Text>
              </Card.Body>
              <Link className="linkVaga" to={`/opportunity/${opportunity.id}`}>
                <Button
                  // onClick={handlerCandidatar}
                  id={opportunity.isApplied ? "buttonGreen" : "buttonBlue"}
                  className="buttonVaga"
                  variant="primary"
                  type="button"
                >
                  {opportunity.isApplied ? "Candidatada" : "Ver Oportunidade"}
                </Button>
              </Link>
            </Card>
          </div>
        );
      })
    );
  }

  return (
    <>
      <Form className="mt-2" onSubmit={handleSearchClick}>
        <Form.Row className="align-items-center justify-content-around">
          <Col xs={12} sm={5} className="mb-2 mb-sm-0">
            <Form.Control
              ref={searchInput}
              type="text"
              placeholder="Pesquisar por nome"
            />
          </Col>
          <Col xs={12} sm={5} className="mb-2 mb-sm-0">
            <Form.Control
              ref={requirementsInput}
              type="text"
              placeholder="Pesquisar por requisitos"
            />
          </Col>
          <Col xs={12} sm={1}>
            <Button type="submit" className="w-100">
              <i className="fa fa-search" aria-hidden="true"></i>
              <span className="d-sm-none ml-2">Buscar</span>
            </Button>
          </Col>
        </Form.Row>
      </Form>
      {loading ? (
        renderLoading()
      ) : (
        <CardDeck className="deck-opportunity">
          {renderListOpportunity(type)}
        </CardDeck>
      )}
      <PaginationPage
        pageCurrent={pageCurrent}
        totalPages={totalPages}
        setPageCurrent={setPageCurrent}
      />
    </>
  );
};

export default ListOpportunity;
