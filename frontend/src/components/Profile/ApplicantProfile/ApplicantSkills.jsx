import React from 'react';
import { skillMap, skillOptions } from '../../../utils/skills';
import ProfileCard from '../ProfileCard/ProfileCard';

const title = 'Habilidades';
const schema = {
  name: {
    label: 'Habilidade',
    required: true,
    type: 'text',
  },
  experienceYears: {
    label: 'Anos de Experiência',
    required: true,
    type: 'number'
  },
  knowledgeLevel: {
    label: 'Nível de Conhecimento',
    required: true,
    as: 'select',
    options: skillOptions
  }
};

const formatSkill = (e) => {
  if (!e) return;
  return (
    <div>
      <p className="font-weight-bold mb-0">{e.name}</p>
      <p className="mb-0">{skillMap[e.knowledgeLevel]}</p>
      {e.experienceYears && <p className="mb-0">{e.experienceYears} {e.experienceYears > 1 ? 'anos' : 'ano'} de experiência.</p>}
    </div>
  );
};

const ApplicantSkills = ({ data, setData, canEdit, handleSubmit }) => {
  const submit = async (newData) => {
    return await handleSubmit({ skills: newData });
  };

  const onSubmit = async (body, i) => {
    const newData = [...data];
    if (i >= 0) newData[i] = body;
    else newData.push(body);
    const { response, error } = await submit(newData);
    if (response.ok) {
      setData(newData);
    } else {
      throw new Error(error);
    }
  };

  const onRemove = async (i) => {
    if (!window.confirm('Deseja realmente apagar este item?')) return;
    const newData = [...data];
    newData.splice(i, 1);
    const { response } = await submit(newData);
    if (response.ok) setData(newData);
  };

  return (
    <>
      <ProfileCard.List
        title={title}
        schema={schema}
        canEdit={canEdit}
        onAdd={onSubmit}
        onEdit={onSubmit}
        onRemove={onRemove}
        items={data}
        formatter={formatSkill}
      />
    </>
  );
};

export default ApplicantSkills;
