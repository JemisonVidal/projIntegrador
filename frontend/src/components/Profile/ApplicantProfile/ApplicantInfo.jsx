import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import ProfileCard from '../ProfileCard/ProfileCard';
import ProfileCardItem from '../ProfileCard/ProfileCardItem';

const ApplicantInfo = ({ data, setData, canEdit, handleSubmit }) => {
  const [isEditing, setEditing] = useState(false);
  const [newData, setNewData] = useState({});
  const title = 'Informações Pessoais';

  function handleClick(event) {
    event.preventDefault();
    if (!isEditing) setEditing(true);
    else if (window.confirm('Cancelar alterações?')) setEditing(false);
  }
  
  const handleSave = async () => {
    const { response } = await handleSubmit(newData);
    if (response.ok) updateData();
  }

  const updateData = () => {
    const copy = {...data};
    Object.entries(newData).forEach(([k, v]) => copy[k].value = v)
    setData(copy);
    setNewData({});
    setEditing(false);
  }

  return (
    <ProfileCard title={title} canEdit={canEdit} handleClick={handleClick}>
      {isEditing
        ? Object.entries(data).map(([k, v]) => (
            <ProfileCardItem.Edit key={k} name={k} data={v} fields={newData} setData={setNewData} />
          ))
        : Object.values(data).map((e, i) => (
            <ProfileCardItem
              key={i}
              title={e.text}
              value={e.value}
              formatter={e.formatter}
            />
          ))}
      {isEditing && (
        <div className="d-flex justify-content-center">
          <Button id="save-profile" variant="primary" type="submit" onClick={handleSave}>
            Salvar
          </Button>
        </div>
      )}
    </ProfileCard>
  );
};

export default ApplicantInfo;
