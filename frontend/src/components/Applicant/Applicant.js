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
import { GET_ALL_APPLICANT } from "../../APIs/profileAPI";
import "./Applicant.css";

const Applicant = () => {
  const history = useHistory();
  const [totalPages, setTotalPages] = useState(0);
  const [pageCurrent, setPageCurrent] = useState(0);
  const [applicants, setApplicants] = useState(null);
  const searchInput = useRef(null);

  const { request, loading } = useFetch();

  async function getCompany() {
    const { url, options } = GET_ALL_APPLICANT(
      pageCurrent,
      searchInput.current.value
    );
    const { json, response } = await request(url, options);
    if (response.ok) {
      setApplicants(json.content);
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
    return history.push(`/profile/applicant/${event.target.id}`);
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
    if (applicants && applicants.length <= 0) {
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
      applicants &&
      applicants.map((applicant, index) => {
        return (
          <CardDeck key={index}>
            <Card>
              <Card.Body>
                <div className="container-card-perfil">
                  <img src={applicant.imgSrc} />
                  <Card.Title className="title-applicant-search">
                    {applicant.name}
                  </Card.Title>
                </div>
                <Card.Text>
                  <strong>Cargo de interesse:</strong> {applicant.title}
                </Card.Text>
                <Card.Text>
                  <strong>Sobre mim:</strong> {applicant.about}
                </Card.Text>
                <Card.Text>
                  <strong>Localização:</strong>{" "}
                  <i className="fa fa-map-marker" aria-hidden="true"></i>{" "}
                  {applicant.location}
                </Card.Text>
                <Card.Text>
                  <strong>Telefone:</strong> {applicant.phoneNumber}
                </Card.Text>
                <Card.Text>
                  <strong>Github:</strong>{" "}
                  <a
                    className="link-perfil"
                    href={applicant.github}
                    target="blank"
                  >
                    {applicant.github}
                  </a>
                </Card.Text>
                <Card.Text>
                  <strong>Linkedin:</strong>{" "}
                  <a
                    className="link-perfil"
                    href={applicant.linkedin}
                    target="blank"
                  >
                    {applicant.linkedin}
                  </a>
                </Card.Text>
              </Card.Body>
              <Button
                id={applicant.id}
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

export default Applicant;
