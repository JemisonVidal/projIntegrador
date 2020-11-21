import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Button } from "react-bootstrap";

const RequirementsCard = ({ id, title, schema, onChange, items, setItems }) => {
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editIndex, setEditIndex] = useState(undefined);
  const history = useHistory();

  return (
    <Button
      variant="outline-primary"
      className="btn-profile-add"
      onClick={() => setShowModal(true)}
    >
      <i className="fas fa-plus mr-1"></i>
      Adicionar
    </Button>
  );
};

export default RequirementsCard;
