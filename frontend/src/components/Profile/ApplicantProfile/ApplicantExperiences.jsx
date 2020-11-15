import React from 'react';
import ProfileCard from '../ProfileCard/ProfileCard';

const title = 'Experiências';
const dateRegex = /(^\d{4})-(\d{2}).*/; // regex para capturar o ano e o mês
const schema = {
  company: {
    label: 'Empresa',
    required: true,
    type: 'text',
  },
  location: {
    label: 'Localização',
    required: true,
    type: 'text',
    placeholder: 'São Paulo, SP',
  },
  position: {
    label: 'Cargo',
    required: true,
    type: 'text',
  },
  initialDate: {
    label: 'Data de início',
    required: true,
    type: 'date',
  },
  finalDate: {
    label: 'Data de término',
    type: 'date',
    text: 'Deixe este campo em branco caso for seu cargo atual.',
  },
  activities: {
    label: 'Atividades',
    as: 'textarea',
  },
};

const formatExperience = (e) => {
  if (!e) return;
  const initial = e.initialDate.replace(dateRegex, '$2/$1');
  const final = e.acting ? 'atual' : e.finalDate.replace(dateRegex, '$2/$1');
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

  const onSubmit = async (body, i) => {
    body.acting = !body.finalDate;
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
        items={sortExperiences(data)}
        formatter={formatExperience}
      />
    </>
  );
};

export default ApplicantExperiences;
