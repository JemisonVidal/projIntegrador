import React, { useState, useEffect } from "react";
import {
  Container,
  CardDeck,
  Card,
  Button,
  Form,
  FormControl,
  Spinner,
} from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Main from "../../components/Template/main/Main";
import useFetch from "../../Hooks/useFetch";
import PaginationPage from "../Pagination/Pagination";
import { GET_COMPANYS } from "../../APIs/companyAPI";
import "./Company.css";

const Company = () => {
  const history = useHistory();
  const [totalPages, setTotalPages] = useState(0);
  const [pageCurrent, setPageCurrent] = useState(null);
  const [wordSearch, setWordSearch] = useState(null);
  const [companys, setCompanys] = useState(null);

  const { request, loading } = useFetch();

  async function getCompany() {
    const { url, options } = GET_COMPANYS();
    const { json, response } = await request(url, options);
    if (response.ok) {
      setCompanys(json.content);
      setPageCurrent(json.pageable?.pageNumber + 1);
      setTotalPages(json.totalPages);
    }
  }

  useEffect(() => {
    getCompany();
  }, [request, wordSearch, pageCurrent]);

  function handleVerPerfilClick(event) {
    return history.push(`/profile/company/${event.target.id}`);
  }

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
      </div>
    );
  }

  function renderCompanys() {
    return (
      companys &&
      companys.map((company, index) => {
        if (!company.name) return;
        return (
          <CardDeck key={index}>
            <Card>
              <Card.Body>
                <Card.Title>{company.name}</Card.Title>
                <Card.Text>
                  <strong>Visão Geral:</strong> {company.about}
                </Card.Text>
                <Card.Text>
                  <strong>local:</strong> {company.location}
                </Card.Text>
                <Card.Text>
                  <strong>Ramo:</strong>
                  {company.category}
                </Card.Text>
                <Card.Text>
                  <strong>site:</strong> {company.site}
                </Card.Text>
                <Card.Text>
                  <strong>linkedin:</strong>
                  {company.linkedin}
                </Card.Text>
              </Card.Body>
              <Button
                id={company.id}
                variant="primary"
                type="submit"
                onClick={handleVerPerfilClick}
              >
                Ver Perfil
              </Button>
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
            type="text"
            placeholder="Pesquisar"
            className=" form-control"
          />
          <Button className="btn-search ml-2" type="submit">
            <i class="fa fa-search" aria-hidden="true"></i>
          </Button>
        </Form>
        {loading ? renderLoading() : renderCompanys()}
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

export default Company;
