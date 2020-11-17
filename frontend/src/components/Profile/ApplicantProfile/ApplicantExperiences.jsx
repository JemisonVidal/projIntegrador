import React from "react";
import ProfileCard from "../ProfileCard/ProfileCard";

const title = "Experiências";
const dateRegex = /(^\d{4})-(\d{2}).*/; // regex para capturar o ano e o mês
const schema = {
  company: {
    label: "Empresa",
    required: true,
    type: "text"
  },
  location: {
    label: "Localização",
    required: true,
    type: "text",
    placeholder: "São Paulo, SP"
  },
  position: {
    label: "Cargo",
    required: true,
    type: "text"
  },
  initialDate: {
    label: "Data de início",
    required: true,
    type: "date"
  },
  finalDate: {
    label: "Data de término",
    type: "date",
    text: "Deixe este campo em branco caso for seu cargo atual."
  },
  activities: {
    label: "Atividades",
    as: "textarea"
  }
};

const formatExperience = (e) => {
  if (!e) return;
  const initial = e.initialDate.replace(dateRegex, "$2/$1");
  const final = e.acting ? "atual" : e.finalDate.replace(dateRegex, "$2/$1");
  return (
    <div>
      <p className="font-weight-bold mb-0">{e.company}</p>
      <p className="mb-0">{e.location}</p>
      <p className="mb-0">{e.position}</p>
      <p className="mb-1">
        {initial} - {final}
      </p>
      <p className="mb-1">{e.activities}</p>
    </div>
  );
};

const sortExperiences = (e) =>
  e.sort((a, b) => new Date(a.initialDate) - new Date(b.initialDate));

const ApplicantExperiences = ({ data, setData, canEdit, handleSubmit }) => {
  const submit = async (newData) => {
    return await handleSubmit({ workExperiences: newData });
  };

  return (
    <ProfileCard.List
      id="experiences"
      title={title}
      schema={schema}
      canEdit={canEdit}
      items={sortExperiences(data)}
      setItems={setData}
      onSubmit={submit}
      preSubmit={(item) => (item.acting = !item.finalDate)}
      formatter={formatExperience}
    />
  );
};

export default ApplicantExperiences;
