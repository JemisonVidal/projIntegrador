import React, { useState } from 'react';
import ProfileCard from '../ProfileCard/ProfileCard';
import ProfileCardItem from '../ProfileCard/ProfileCardItem';
import useEditMode from '../../../Hooks/useEditMode';

const ApplicantInfo = ({ data, setData, canEdit, handleSubmit }) => {
  const { editing, setEditing, toggleEditMode } = useEditMode();
  const [newData, setNewData] = useState({});
  const title = 'Informações Pessoais';

  const handleSave = async () => {
    const { response } = await handleSubmit(newData);
    if (response.ok) updateData();
  };

  const updateData = () => {
    const copy = { ...data };
    Object.entries(newData).forEach(([k, v]) => (copy[k].value = v));
    setData(copy);
    setNewData({});
    setEditing(false);
  };

  if (editing)
    return (
      <ProfileCard.Edit title={title} handleClick={toggleEditMode} handleSave={handleSave}>
        {Object.entries(data).map(([k, v]) => (
          <ProfileCardItem.Edit
            key={k}
            name={k}
            data={v}
            fields={newData}
            setData={setNewData}
          />
        ))}
      </ProfileCard.Edit>
    );

  return (
    <ProfileCard title={title} canEdit={canEdit} handleClick={toggleEditMode}>
      {Object.values(data).map((e, i) => (
        <ProfileCardItem
          key={i}
          title={e.text}
          value={e.value}
          formatter={e.formatter}
        />
      ))}
    </ProfileCard>
  );
};

export default ApplicantInfo;
