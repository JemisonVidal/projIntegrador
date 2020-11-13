import React, { useState } from 'react';
import ModalForm from '../../Modal/ModalForm';
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

const sortExperiencies = (e) =>
  e.sort((a, b) => new Date(a.initialDate) - new Date(b.initialDate));

const ApplicantExperiences = ({ data, setData, canEdit, handleSubmit }) => {
  const [showAdd, setShowAdd] = useState(false);
  const [showEdit, setShowEdit] = useState(false);
  const [editIndex, setEditIndex] = useState(null);

  const submit = async (newData) => {
    const { response } = await handleSubmit({ workExperiences: newData });
    return response.ok;
  };

  const handleShowEdit = (i) => {
    setEditIndex(i);
    setShowEdit(true);
  };

  const handleAdd = async (body) => {
    body.acting = !body.finalDate;
    const newData = [...data, body];
    if (await submit(newData)) {
      setShowAdd(false);
      setData(newData);
    }
  };

  const handleEdit = async (body) => {
    body.acting = !body.finalDate;
    const newData = [...data];
    newData[editIndex] = body;
    if (await submit(newData)) {
      setShowEdit(false);
      setData(newData);
    }
  };

  const handleRemove = async (i) => {
    if (!window.confirm('Deseja realmente apagar este item?')) return;
    const newData = [...data];
    newData.splice(i, 1);
    if (await submit(newData)) setData(newData);
  };

  return (
    <>
      <ProfileCard.List
        title={title}
        canEdit={canEdit}
        handleAdd={() => setShowAdd(true)}
        handleEdit={handleShowEdit}
        handleRemove={handleRemove}
        items={sortExperiencies(data)}
        formatter={formatExperience}
      />
      <ModalForm
        show={showAdd}
        onHide={() => setShowAdd(false)}
        onSubmit={handleAdd}
        schema={schema}
        title="Adicionar experiência"
      />
      <ModalForm
        show={showEdit}
        onHide={() => setShowEdit(false)}
        onSubmit={handleEdit}
        schema={schema}
        title="Editar experiência"
        values={data[editIndex]}
      />
    </>
  );
};

export default ApplicantExperiences;
