import { useState } from 'react';

const useEditMode = () => {
  const [editing, setEditing] = useState(false);

  const toggleEditMode = () => {
    if (!editing) setEditing(true);
    else if (window.confirm('Cancelar alterações?')) setEditing(false);
  };

  return {
    editing,
    setEditing,
    toggleEditMode,
  };
};

export default useEditMode;
