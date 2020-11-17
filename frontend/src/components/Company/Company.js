import React, { useState, useEffect, useRef } from "react";
import {
  Container,
  CardDeck,
  Card,
  Button,
  Form,
  FormControl,
  Spinner
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
  const [pageCurrent, setPageCurrent] = useState(0);
  const [companys, setCompanys] = useState(null);
  const searchInput = useRef(null);

  const { request, loading } = useFetch();

  async function getCompany() {
    const { url, options } = GET_COMPANYS(
      pageCurrent,
      searchInput.current.value
    );
    const { json, response } = await request(url, options);
    if (response.ok) {
      setCompanys(json.content);
      setPageCurrent(json.pageable?.pageNumber);
      setTotalPages(json.totalPages);
    }
  }

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
    getCompany();
  }, [pageCurrent]);

  function handleVerPerfilClick(event) {
    return history.push(`/profile/company/${event.target.id}`);
  }

  async function handleSearchClick(event) {
    await getCompany();
  }

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
        <span className="sr-only">Loading...</span>
      </div>
    );
  }

  function renderCompanys() {
    if (companys && companys.length <= 0) {
      return (
        <p className="messageOps">
          Ops... Não encontramos nenhuma empresa com esse nome.
          <br /> :(
          <br />
          Tente novamente !
        </p>
      );
    }
    return (
      companys &&
      companys.map((company, index) => {
        return (
          <CardDeck key={index}>
            <Card>
              <Card.Body>
                <Card.Title className="title-company-search">
                  {company.name}
                </Card.Title>
                <Card.Text>
                  <strong>Visão Geral:</strong> {company.about}
                </Card.Text>
                <Card.Text>
                  <strong>Localização:</strong>{" "}
                  <i className="fa fa-map-marker" aria-hidden="true"></i>{" "}
                  {company.location}
                </Card.Text>
                <Card.Text>
                  <strong>Ramo:</strong> {company.category}
                </Card.Text>
                <Card.Text>
                  <strong>Site:</strong>{" "}
                  <a className="link-perfil" href={company.site} target="blank">
                    {company.site}
                  </a>
                </Card.Text>
                <Card.Text>
                  <strong>Linkedin:</strong>{" "}
                  <a
                    className="link-perfil"
                    href={company.linkedin}
                    target="blank"
                  >
                    {company.linkedin}
                  </a>
                </Card.Text>
              </Card.Body>
              <Button
                id={company.id}
                variant="primary"
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
            ref={searchInput}
            type="text"
            placeholder="Pesquisar"
            className=" form-control"
          />
          <Button className="btn-search ml-2" onClick={handleSearchClick}>
            <i className="fa fa-search" aria-hidden="true"></i>
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
