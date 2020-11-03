import React, { useState, useEffect } from "react";
import axios from 'axios';
import { Container, CardDeck, Card, Button } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Main from "../../components/Template/main/Main";
import Img from "../../../src/assets/images/mdi_search.png"

import "./Company.css";


const mockCompany = [
  {
    id: 1,
    descricao: "Empresa 1",
    visao:
      "Mussum Ipsum, cacilds vidis litro abertis, Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Praesent malesuada urna nisi, quis volutpat erat hendrerit non",
    local: "Itu/SP",
    ramoCategoria: "tecnolgia / ERP",
    site: "https://mussumipsum.com/",
    Linkedin: "https://linkedin/mussumipsum",
  },
  {
    id: 2,
    descricao: "Empresa 2",
    visao:
      "Mauris nec dolor in eros commodo tempor. Aenean aliquam molestie leo",
    local: "São Paulo/SP",
    ramoCategoria: "Software house",
    site: "https://mussumipsum.com/",
    Linkedin: "https://linkedin/mussumipsum",
  },
  {
    id: 3,
    descricao: "Empresa 3",
    visao:
      "Quem manda na minha terra sou euzis! Paisis, filhis, espiritis santis",
    local: "Rio de Janeiro/SP",
    ramoCategoria: "Software house - web",
    site: "https://mussumipsum.com/",
    linkedin: "https://linkedin/mussumipsum",
  },
];
{/*Mudar de cima para o que está parecido com o Procurar no figma */}

const App = ()=>{
  const [post, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [postPerPage, setPostPerPage] = useState(10);

  useEffect(()=>{
    const fetchPosts = async()=>{
      setLoading(true);
      const res = await axios.get('http://localhost:3000/company');
      setPosts(res.data);
      setLoading(false);
    }

    fetchPosts();
  }, []);
}

const Company = () => {
  const history = useHistory();

  function handleVerPerfilClick(event) {
    return history.push(`/profile/company/${event.target.id}`);
  }

  return (
    <Main>
      <Container fluid="md" className="py-2">
          <input className="form-control" type="search" placeholder="Ex: Rebeca Souza"/>
          <Button className="btn-search" type="submit">
            <img className = "img" src={Img}/>
             </Button>
        {mockCompany &&
          mockCompany.map((mockCompany) => {
            return (
              <CardDeck key={mockCompany.id}>
                <Card>
                  <Card.Body>
                    <Card.Title>{mockCompany.descricao}</Card.Title>
                    <Card.Text>Visão Geral: {mockCompany.visao}</Card.Text>
                    <Card.Text>local: {mockCompany.local}</Card.Text>
                    <Card.Text>Ramo:{mockCompany.ramoCategoria}</Card.Text>
                    <Card.Text>site: {mockCompany.site}</Card.Text>
                    <Card.Text>linkedin:{mockCompany.linkedin}</Card.Text>
                  </Card.Body>
                  <Button
                    id={mockCompany.id}
                    variant="primary"
                    type="submit"
                    onClick={handleVerPerfilClick}
                  >
                    Ver Perfil
                  </Button>
                </Card>
              </CardDeck>
            );
          })}
      </Container>
    </Main>
  );
};

export default Company;
