import React from "react";
import ProfileCard from "../ProfileCard/ProfileCard";

const title = "Cursos e Certificados";
const schema = {
  name: {
    label: "Nome",
    required: true,
    type: "text"
  },
  institution: {
    label: "Instituição",
    required: true,
    type: "text"
  },
  conclusionMonth: {
    label: "Mês de conclusão",
    type: "number"
  },
  conclusionYear: {
    label: "Ano de conclusão",
    required: true,
    type: "number"
  },
  workLoad: {
    label: "Carga horária (horas)",
    type: "number"
  }
};

const formatCourse = (e) => {
  if (!e) return;
  return (
    <div>
      <p className="font-weight-bold mb-0">{e.name}</p>
      <p className="mb-0">{e.institution}</p>
      <p className="mb-0">
        Conclusão em{" "}
        {e.conclusionMonth && `${e.conclusionMonth}`.padStart(2, "0") + "/"}
        {e.conclusionYear}
      </p>
      {e.workLoad && <p className="mb-0">Carga horária {e.workLoad} horas</p>}
    </div>
  );
};

const ApplicantCourses = ({ data, setData, canEdit, handleSubmit }) => {
  const submit = async (newData) => {
    return await handleSubmit({ courses: newData });
  };

  return (
    <ProfileCard.List
      id="courses"
      title={title}
      schema={schema}
      canEdit={canEdit}
      items={data}
      setItems={setData}
      onSubmit={submit}
      formatter={formatCourse}
    />
  );
};

export default ApplicantCourses;
