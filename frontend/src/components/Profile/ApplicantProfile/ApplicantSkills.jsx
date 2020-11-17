import React from "react";
import { skillMap, skillOptions } from "../../../utils/skills";
import ProfileCard from "../ProfileCard/ProfileCard";

const title = "Habilidades";
const schema = {
  name: {
    label: "Habilidade",
    required: true,
    type: "text"
  },
  experienceYears: {
    label: "Anos de Experiência",
    required: true,
    type: "number"
  },
  knowledgeLevel: {
    label: "Nível de Conhecimento",
    required: true,
    as: "select",
    options: skillOptions
  }
};

const formatSkill = (e) => {
  if (!e) return;
  return (
    <div>
      <p className="font-weight-bold mb-0">{e.name}</p>
      <p className="mb-0">{skillMap[e.knowledgeLevel]}</p>
      {e.experienceYears && (
        <p className="mb-0">
          {e.experienceYears} {e.experienceYears > 1 ? "anos" : "ano"} de
          experiência.
        </p>
      )}
    </div>
  );
};

const ApplicantSkills = ({ data, setData, canEdit, handleSubmit }) => {
  const submit = async (newData) => {
    return await handleSubmit({ skills: newData });
  };

  return (
    <ProfileCard.List
      id="skills"
      title={title}
      schema={schema}
      canEdit={canEdit}
      items={data}
      setItems={setData}
      onSubmit={submit}
      formatter={formatSkill}
    />
  );
};

export default ApplicantSkills;
