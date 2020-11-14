import React, { useState, useEffect, useContext } from "react";
import { Container, CardDeck, Card, Button, Pagination, Form, FormControl } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Main from "../../components/Template/main/Main";
import Img from "../../../src/assets/images/mdi_search.png";
import useFetch from "../../Hooks/useFetch";
import { GET_SEARCH } from "../../APIs/APIs";
import StoreContext from "../../components/Store/Context";
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

const Company = () => {
  const history = useHistory();
  const [totalPages, setTotalPages] = useState(1)
  const {request, loading} = useFetch();
  const {appToken} = useContext(StoreContext);
  
  useEffect(async ()=>{
    //usar dependência
    async function getPagination(){
      const {url, options} = GET_SEARCH(appToken);
      const {json, response} = await request(url, options);
      console.log(response, json);
    }
    getPagination();
  }, []);

  function handleVerPerfilClick(event) {
    return history.push(`/profile/company/${event.target.id}`);
  } 

const paginationBasic = () => {
    let active = 1;
    let items = [];
  for (let number = 1; number <= totalPages; number++) {
  items.push(
    <Pagination.Item  key={number} active={number === active}>
      {number}
    </Pagination.Item>,
  );
}

  return(
  <div>
    <Pagination className = "pagination-company" size="sm">{items}</Pagination>
  </div>
)
    }


//fazer o get aqui
//setTotalPages(coloca o get aqui);

  return (
    <Main>
      <Container fluid="md" className="py-2">
        
          <Form className="search" inline>
            <FormControl type="text" placeholder="Pesquisar" className=" form-control" />
            <Button className ="btn-search" type="submit">
              <img className = "img-search" src={Img} alt="Procurar"/>
            </Button>
          </Form>

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
          {paginationBasic()}
      </Container>
    </Main>
  );
};

export default Company;
