import React, { useState, useEffect, useRef } from "react";
import { Container, CardDeck, Card, Button, Pagination, Spinner, Form, FormControl } from "react-bootstrap";
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
  const [pageCurrent, setPageCurrent] = useState(0);
  const [candidatarCheck, setCandidatarCheck] = useState(null);
  const {request, loading} = useFetch();
  const [arrayOpportunity, setArrayOpportunity] = useState([]);
  const searchInput = useRef(null);

  // const handlerCandidatar = (event) => {
  //   setCandidatarCheck(!candidatarCheck);
  // }

  async function getOpportunity() {
    const {url, options} = GET_LIST_OPPORTUNITY(
      pageCurrent,
      searchInput.current.value
    );
    const {json, response} = await request(url, options);
    if (response.ok) {
      setArrayOpportunity(json.content);
      setPageCurrent(json.pageable?.pageNumber);
      setTotalPages(json.totalPages);
      // const mockApply = {...arrayOpportunity};
      // mockApply.listAppliedIDOpportunity = []
    }
  }

  useEffect(()=>{
    getOpportunity();
  }, [pageCurrent]);


  console.log(arrayOpportunity);
  

  async function handleSearchClick(event) {
    await getOpportunity();
  }

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
        <span className="sr-only">Loading...</span>
      </div>
    );
  }

  function renderListOpportunity() {
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
        return (
          <CardDeck key={opportunity.id}>
            <Card className="card">
              <Card.Body>
                <Card.Title className="title-card">
                  {opportunity.name}
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
      <Form className="search" inline>
          <FormControl
            ref={searchInput}
            type="text"
            placeholder="Pesquisar"
            className="form-control"
          />
          <Button className="btn-search ml-2" onClick={handleSearchClick}>
            <i class="fa fa-search" aria-hidden="true"></i>
          </Button>
        </Form>
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
