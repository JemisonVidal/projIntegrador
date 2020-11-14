import React, { useState } from 'react';
import { useEffect } from 'react';
import ProfileInfo from '../ProfileInfo';
import useFetch from '../../../Hooks/useFetch';
import { PATCH_PROFILE } from '../../../APIs/APIs';
import ApplicantExperiences from './ApplicantExperiences';
import ApplicantSkills from './ApplicantSkills';
import ApplicantCourses from './ApplicantCourses';
import { currencyFormatter, linkFormatter } from '../../../utils/formatters';

const ApplicantProfile = ({ data, canEdit, profileId }) => {
  const { request } = useFetch();
  const [info, setInfo] = useState({});
  const [skills, setSkills] = useState([]);
  const [courses, setCourses] = useState([]);
  const [workExperiences, setWorkExperiences] = useState([]);

  const handleSubmit = (body) => {
    const { url, options } = PATCH_PROFILE("applicant", profileId, body);
    return request(url, options);
  };

  useEffect(() => {
    setInfo({
      about: { text: "Sobre", value: data.about, type: "textarea" },
      location: { text: "Cidade onde mora", value: data.location },
      locationWanted: {
        text: "Disposta a trabalhar em",
        value: data.locationWanted,
      },
      desiredSalary: {
        text: "Salário desejado",
        value: data.desiredSalary,
        type: "number",
        formatter: currencyFormatter,
      },
      github: {
        text: "Github",
        value: data.github,
        formatter: linkFormatter,
        placeholder: "https://github.com/usuaria",
      },
      linkedin: {
        text: "LinkedIn",
        value: data.linkedin,
        formatter: linkFormatter,
        placeholder: "https://linkedin.com/in/usuaria",
      },
    });
    setSkills(data.skills || []);
    setCourses(data.courses || []);
    setWorkExperiences(data.workExperiences || []);
  }, [data]);

  return (
    <div>
      <ProfileInfo
        data={info}
        setData={setInfo}
        canEdit={canEdit}
        handleSubmit={handleSubmit}
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
