import React from "react";
import { Container } from "react-bootstrap";
import AddOpportunity from "../../components/AddOpportunity/AddOpportunity";
import Main from "../../components/Template/main/Main";

const PagesAddOpportunity = (props) => {
  return (
    <Main>
      <Container fluid="md" className="py-2">
        <AddOpportunity />
      </Container>
    </Main>
  );
};

export default PagesAddOpportunity;
