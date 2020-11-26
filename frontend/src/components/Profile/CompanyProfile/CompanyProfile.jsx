import React, { useState, useEffect } from "react";
import useFetch from "../../../Hooks/useFetch";
import {
  GET_COMPANY_OPPORTUNITIES,
  PATCH_PROFILE
} from "../../../APIs/profileAPI";
import {
  dateFormatter,
  linkFormatter,
  locationFormatter
} from "../../../utils/formatters";
import StyledCard from "../../StyledCard/StyledCard";
import { Button, Col, Row } from "react-bootstrap";
import ProfileCard from "../ProfileCard/ProfileCard";
import { Link } from "react-router-dom";

const infoSchema = {
  about: { label: "Sobre", as: "textarea", required: true },
  location: {
    label: "Localização",
    required: true,
    placeholder: "São Paulo, SP"
  },
  category: {
    label: "Ramo",
    required: true
  },
  startDate: {
    label: "Data de ínicio",
    type: "date",
    required: true,
    formatter: dateFormatter
  },
  site: {
    label: "Site",
    type: "url",
    formatter: linkFormatter,
    placeholder: "https://sitedaempresa.com.br",
    feedback: "Por favor digite uma URL válida."
  },
  linkedin: {
    label: "LinkedIn",
    type: "url",
    formatter: linkFormatter,
    placeholder: "https://linkedin.com/company/empresa",
    feedback: "Por favor digite uma URL válida."
  }
};

const Opportunities = ({ id }) => {
  const { request } = useFetch();
  const [opportunities, setOpportunities] = useState([]);

  useEffect(() => {
    (async () => {
      const { url, options } = GET_COMPANY_OPPORTUNITIES(id);
      const { response, json } = await request(url, options);
      if (response.ok) {
        setOpportunities(json);
      }
    })();
  }, [id]);

  return (
    <StyledCard>
      <StyledCard.Title title="Oportunidades"></StyledCard.Title>
      <Row>
        {opportunities.map((opportunity) => (
          <Col sm={6} lg={4} className="mb-3" key={opportunity.id}>
            <StyledCard className="h-100">
              <StyledCard.Title title={opportunity.name} />
              <p>{locationFormatter(opportunity.location)}</p>
              <Link to={`/opportunity/${opportunity.id}`}>
                <Button>Visualizar vaga</Button>
              </Link>
            </StyledCard>
          </Col>
        ))}
      </Row>
    </StyledCard>
  );
};

const CompanyProfile = ({ data, canEdit, profileId }) => {
  const { request } = useFetch();
  const [info, setInfo] = useState({});

  const handleSubmit = (body) => {
    const { url, options } = PATCH_PROFILE("company", profileId, body);
    return request(url, options);
  };

  useEffect(() => {
    setInfo(
      Object.entries(data).reduce((acc, [k, v]) => {
        if (k in infoSchema) acc[k] = v;
        return acc;
      }, {})
    );
  }, [data]);

  return (
    <div>
      <ProfileCard.Info
        title="Informações Gerais"
        schema={infoSchema}
        canEdit={canEdit}
        onSubmit={async (body) => await handleSubmit(body)}
        data={info}
        setData={setInfo}
      />
      <Opportunities id={profileId} />
    </div>
  );
};

export default CompanyProfile;
