import React, { useState } from "react";
import { useEffect } from "react";
import { Spinner } from "react-bootstrap";
import useFetch from "../../../Hooks/useFetch";
import { PATCH_PROFILE } from "../../../APIs/profileAPI";
import ApplicantExperiences from "./ApplicantExperiences";
import ApplicantSkills from "./ApplicantSkills";
import ApplicantCourses from "./ApplicantCourses";
import { currencyFormatter, linkFormatter } from "../../../utils/formatters";
import ProfileCard from "../ProfileCard/ProfileCard";

const infoSchema = {
  about: { label: "Sobre", as: "textarea" },
  location: { label: "Cidade onde mora", placeholder: "São Paulo, SP" },
  locationWanted: {
    label: "Disposta a trabalhar em",
    placeholder: "São Paulo, SP"
  },
  desiredSalary: {
    label: "Salário desejado",
    type: "number",
    formatter: currencyFormatter
  },
  github: {
    label: "Github",
    type: "url",
    formatter: linkFormatter,
    placeholder: "https://github.com/usuaria",
    feedback: "Por favor digite uma URL válida."
  },
  linkedin: {
    label: "LinkedIn",
    type: "url",
    formatter: linkFormatter,
    placeholder: "https://linkedin.com/in/usuaria",
    feedback: "Por favor digite uma URL válida."
  }
};

const ApplicantProfile = ({ data, canEdit, profileId }) => {
  const { request, loading } = useFetch();
  const [info, setInfo] = useState({});
  const [skills, setSkills] = useState([]);
  const [courses, setCourses] = useState([]);
  const [workExperiences, setWorkExperiences] = useState([]);

  const handleSubmit = (body) => {
    const { url, options } = PATCH_PROFILE("applicant", profileId, body);
    return request(url, options);
  };

  useEffect(() => {
    setInfo(
      Object.entries(data).reduce((acc, [k, v]) => {
        if (k in infoSchema) acc[k] = v;
        return acc;
      }, {})
    );
    setSkills(data.skills || []);
    setCourses(data.courses || []);
    setWorkExperiences(data.workExperiences || []);
  }, [data]);

  if (loading) {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
      </div>
    );
  }

  return (
    <div>
      <ProfileCard.Info
        id="info"
        title="Informações Gerais"
        schema={infoSchema}
        canEdit={canEdit}
        onSubmit={async (body) => await handleSubmit(body)}
        data={info}
        setData={setInfo}
      />
      <ApplicantSkills
        data={skills}
        setData={setSkills}
        canEdit={canEdit}
        handleSubmit={handleSubmit}
      />
      <ApplicantExperiences
        data={workExperiences}
        setData={setWorkExperiences}
        canEdit={canEdit}
        handleSubmit={handleSubmit}
      />
      <ApplicantCourses
        data={courses}
        setData={setCourses}
        canEdit={canEdit}
        handleSubmit={handleSubmit}
      />
    </div>
  );
};

export default ApplicantProfile;
