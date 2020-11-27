import React, { useState, useContext } from "react";
import { Container, Tab, Tabs } from "react-bootstrap";
import Main from "../Template/main/Main";
import { ALL_OPPORTUNITYS, MY_OPPORTUNITYS } from "./listEnum";
import ListOpportunity from "./ListOpportunity";
import StoreContext from "../../components/Store/Context";

const TabListOpportunity = () => {
  const [key, setKey] = useState("allOpportunity");
  const { user } = useContext(StoreContext);
  return (
    <Main>
      <Container fluid="md" className="py-2">
        <Tabs
          id="controlled-tab-example"
          activeKey={key}
          onSelect={(k) => setKey(k)}
        >
          <Tab eventKey="allOpportunity" title="Todas Oportunidades">
            {<ListOpportunity type={ALL_OPPORTUNITYS} />}
          </Tab>
          {user?.type === "applicant" && (
            <Tab eventKey="myOpportunity" title="Minhas Oportunidades">
              {<ListOpportunity type={MY_OPPORTUNITYS} />}
            </Tab>
          )}
        </Tabs>
      </Container>
    </Main>
  );
};

export default TabListOpportunity;
