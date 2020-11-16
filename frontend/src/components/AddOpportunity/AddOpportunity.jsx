import React from 'react';
import ProfileCard from '../Profile/ProfileCard/ProfileCard';

const title = 'Cadastrar oportunidade';

const schema = {
  title: {
    label: 'Título',
    required: true,
    type: 'text'
  },
  descricao: {
    label: 'Descrição',
    required: true,
    type: 'text'
  }
};


const AddOpportunity = (data, setData, canEdit, handleSubmit) => {
  const onSubmit = async (body, i) => {};

  const onRemove = async i => {};

  return (
    <ProfileCard.List
      title={title}
      schema={schema}
      canEdit={canEdit}
      onAdd={onSubmit}
      onSubmit={onSubmit}
      onRemove={onRemove}
      items={data}
    />
  );
};

export default AddOpportunity;
