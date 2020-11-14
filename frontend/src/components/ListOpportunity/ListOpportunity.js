import React, { useState, useEffect } from "react";
import { Container, CardDeck, Card, Button, Pagination, Spinner } from "react-bootstrap";
import Main from "../Template/main/Main";
import useFetch from "../../Hooks/useFetch";
import PaginationPage from "../Pagination/Pagination";
import { GET_LIST_OPPORTUNITY } from "../../APIs/APIs"
import { Link } from "react-router-dom";

import "./ListOpportunity.css";
import { currencyFormatter } from "../../utils/formatters";
import { skillMap } from "../../utils/skills";

const ListOpportunity = () => {
  const [totalPages, setTotalPages] = useState(0);
  const [pageCurrent, setPageCurrent] = useState(null);
  const [candidatarCheck, setCandidatarCheck] = useState(false);
  const {request, loading} = useFetch();
  const [arrayOpportunity, setArrayOpportunity] = useState([]);

  // const handlerCandidatar = (event) => {
  //   setCandidatarCheck(!candidatarCheck);
  // }

  useEffect(()=>{
    async function getOpportunity() {
      const {url, options} = GET_LIST_OPPORTUNITY();
      const {json, response} = await request(url, options);
      if (response.ok) {
        setArrayOpportunity(json.content);
        setPageCurrent(json.pageable?.pageNumber + 1);
        setTotalPages(json.totalPages);
      }
    }
    getOpportunity();
  }, [request, pageCurrent]);

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
      </div>
    );
  }

  function renderListOpportunity() {
    return (
    arrayOpportunity &&
      arrayOpportunity.map((opportunity) => {
        return (
          <CardDeck key={opportunity.id}>
            <Card className="card">
              <Card.Body>
                <Card.Title className="title-card">
                  {opportunity.title}
                </Card.Title>
                <Card.Text>
                  <span className="titulo-campo">Localização:</span>{" "}
                  <i class="fa fa-map-marker" aria-hidden="true"></i>{" "}
                  {opportunity.location}
                </Card.Text>
                <Card.Text>
                  <span className="titulo-campo">Requisitos:</span>{" "}
                  {opportunity.requirements &&
                      opportunity.requirements.map((requirement) => `${requirement.name} ${skillMap[requirement.knowledgeLevel]}`).join(", ")}
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
              <Link
                className="linkVaga"
                to={`/opportunity/${opportunity.id}`}
              >
                <Button
                  // onClick={handlerCandidatar}
                  id={candidatarCheck ? "buttonGreen" : "buttonBlue"}
                  className="buttonVaga"
                  variant="primary"
                  type="button"
                >
                  {candidatarCheck ? "Candidatada" : "Candidatar-se"}
                </Button>
              </Link>
            </Card>
          </CardDeck>
        );
      })
    );
  }

  return (
    <Main>
      <Container fluid="md" className="py-2">
      {loading ? renderLoading() : renderListOpportunity()}
        {
          <PaginationPage
            pageCurrent={pageCurrent}
            totalPages={totalPages}
            setPageCurrent={setPageCurrent}
          />
        }
      </Container>
    </Main>
  );
};

export default ListOpportunity;
