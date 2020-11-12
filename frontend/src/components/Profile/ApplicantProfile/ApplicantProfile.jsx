import React, { useState } from 'react';
import { useEffect } from 'react';
import ApplicantInfo from './ApplicantInfo';
import useFetch from '../../../Hooks/useFetch';
import { PATCH_PROFILE } from '../../../APIs/APIs';

const currencyFormat = new Intl.NumberFormat('pt-BR', {
  style: 'currency',
  currency: 'BRL',
});

const linkFormat = (v) => (
  <a href={v} target="blank">
    {v}
  </a>
);

const ApplicantProfile = ({ data, canEdit, profileId }) => {
  const { request } = useFetch();
  const [info, setInfo] = useState({});
  const [skills, setSkills] = useState([]);
  const [courses, setCourses] = useState([]);
  const [workExperiences, setWorkExperiences] = useState([]);

  const handleSubmit = (body) => {
    const { url, options } = PATCH_PROFILE('applicant', profileId, body);
    return request(url, options);
  };

  useEffect(() => {
    setInfo({
      about: { text: 'Sobre', value: data.about, type: 'textarea' },
      location: { text: 'Cidade onde mora', value: data.location },
      locationWanted: {
        text: 'Disposta a trabalhar em',
        value: data.locationWanted,
      },
      desiredSalary: {
        text: 'Salário desejado',
        value: data.desiredSalary,
        type: 'number',
        formatter: (v) => currencyFormat.format(v),
      },
      github: {
        text: 'Github',
        value: data.github,
        formatter: linkFormat,
        placeholder: 'https://github.com/usuaria',
      },
      linkedin: {
        text: 'LinkedIn',
        value: data.linkedin,
        formatter: linkFormat,
        placeholder: 'https://linkedin.com/in/usuaria',
      },
    });
    setSkills(data.skills);
    setCourses(data.courses);
    setWorkExperiences(data.workExperiences);
  }, [data]);

  return (
    <div>
      <ApplicantInfo
        data={info}
        setData={setInfo}
        canEdit={canEdit}
        handleSubmit={handleSubmit}
      />
    </div>
  );
};

export default ApplicantProfile;
