import React from 'react';
import ProfileCard from '../ProfileCard/ProfileCard';

const UserProfile = ({ data }) => {
  const canEdit = data.canEdit;

  const personalInfo = {
    Cidade: data.city,
    'Tempo de experiência': data.experience,
    'Disposta a trabalhar em': data.desiredCity,
    'Lista de habilidades': data.skills.join(', '),
    'Links externos': [
      <span>
        Github: <a href={data.links.github} target="_blank" rel="noopener noreferrer" >{data.links.github}</a>
      </span>,
      <span>
        LinkedIn: <a href={data.links.linkedin} target="_blank" rel="noopener noreferrer">{data.links.linkedin}</a>
      </span>,
    ],
  };

  const experience = data.companies.reduce((acc, company) => {
    acc[company.name] = [
      company.position,
      `${company.initialDate} - ${
        company.acting ? 'atual' : company.finalDate
      }`,
    ];
    return acc;
  }, {});

  const coursesInfo = data.courses.reduce((acc, course) => {
    acc[course.institution] = [course.name, course.conclusionYear];
    return acc;
  }, {});

  return (
    <div>
      <ProfileCard
        title="Informações Gerais"
        data={personalInfo}
        canEdit={canEdit}
      />
      <ProfileCard title="Experiência" data={experience} canEdit={canEdit} />
      <ProfileCard
        title="Formação Acadêmica"
        data={coursesInfo}
        canEdit={canEdit}
      />
    </div>
  );
};

export default UserProfile;
