import React, { useEffect, useState } from "react";
import { Button, Col, ListGroup, Row } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import ModalForm from "../../Modal/ModalForm";
import StyledCard from "../../StyledCard/StyledCard";
import "./ProfileCard.css";

const checkHash = (hash, id) => hash && hash === id;

const showHashModal = (hash, id, setShow) => {
  if (checkHash(hash, id)) {
    setShow(true);
    document.getElementById(hash).scrollIntoView();
  }
};

const replaceHash = (history) => {
  if (history.location?.hash) history.replace(history.location.pathname);
};

const ProfileCard = ({ title, canEdit, handleClick, children }) => {
  const renderIcon = () => (
    <button className="btn-profile-edit" onClick={handleClick} title="Editar">
      <i className="far fa-edit"></i>
    </button>
  );
  return (
    <StyledCard className="card-profile">
      <StyledCard.Title title={title}>
        {canEdit && renderIcon()}
      </StyledCard.Title>
      {children}
    </StyledCard>
  );
};

const Item = ({ title, value, formatter }) => {
  return (
    <Row className="profile-item">
      <Col sm={12} md="auto">
        <span className="font-weight-bold">{title}</span>
      </Col>
      <Col sm={12} md>
        {formatter ? formatter(value) : value}
      </Col>
    </Row>
  );
};
ProfileCard.Item = Item;

const Info = ({
  id,
  title,
  schema,
  canEdit,
  onSubmit,
  preSubmit,
  data,
  setData
}) => {
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const history = useHistory();

  const handleError = (error) => {
    if (error?.msg === "Validation Errors") {
      setError(
        error.errors.reduce(
          (acc, e) =>
            `${acc}${schema[e.fieldName.split(".").pop()]?.label} ${e.message}`,
          ""
        )
      );
    } else {
      setError(error.msg);
    }
  };

  const handleSubmit = async (body) => {
    if (preSubmit) preSubmit(body);
    const { response, json } = await onSubmit(body);
    if (response.ok) {
      setData({ ...body });
      replaceHash(history);
    } else {
      handleError(json);
    }
  };

  const onHide = () => {
    setShowModal(false);
    setError(undefined);
  };

  const renderModal = () => {
    return (
      <ModalForm
        show={showModal}
        onHide={onHide}
        onSubmit={handleSubmit}
        schema={schema}
        title="Editar informações"
        values={data}
        error={error}
      />
    );
  };

  const renderIcon = () => (
    <button
      className="btn-profile-edit"
      onClick={() => setShowModal(true)}
      title="Editar"
    >
      <i className="far fa-edit"></i>
    </button>
  );

  useEffect(() => {
    const hash = history.location?.hash.substring(1);
    if (showModal && !hash) onHide();
    showHashModal(hash, id, setShowModal);
  }, [data]);

  return (
    <StyledCard className="card-profile" id={id}>
      <StyledCard.Title title={title}>
        {canEdit && renderIcon()}
      </StyledCard.Title>
      {Object.entries(schema).map(
        ([k, v]) =>
          (!v.hidden || canEdit) && (
            <ProfileCard.Item
              key={k}
              title={v.label}
              value={data[k]}
              formatter={v.formatter}
            />
          )
      )}
      {canEdit && renderModal()}
    </StyledCard>
  );
};
ProfileCard.Info = Info;

const List = ({
  id,
  title,
  schema,
  canEdit,
  onSubmit,
  preSubmit,
  items,
  setItems,
  formatter
}) => {
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editIndex, setEditIndex] = useState(undefined);
  const history = useHistory();

  const handleError = (error) => {
    if (error?.msg === "Validation Errors") {
      setError(
        error.errors.reduce(
          (acc, e) =>
            `${acc}${schema[e.fieldName.split(".").pop()]?.label} ${e.message}`,
          ""
        )
      );
    } else {
      setError(error.msg);
    }
  };

  const handleSubmit = async (item) => {
    if (preSubmit) preSubmit(item);
    if (editIndex >= 0) items[editIndex] = item;
    else items.push(item);
    const { response, json } = await onSubmit(items);
    if (response.ok) {
      setItems([...items]);
      if (history.location?.hash) history.replace(history.location.pathname);
    } else {
      handleError(json);
    }
  };

  const handleRemove = async (i) => {
    if (!window.confirm("Deseja realmente apagar este item?")) return;
    items.splice(i, 1);
    const { response } = await onSubmit(items);
    if (response.ok) setItems([...items]);
  };

  const handleShowEdit = (i) => {
    setEditIndex(i);
    setShowModal(true);
  };

  const onHide = () => {
    if (editIndex >= 0) setEditIndex(undefined);
    setShowModal(false);
    setError(undefined);
    replaceHash(history);
  };

  const renderAddButton = () => (
    <Button
      variant="outline-primary"
      className="btn-profile-add mb-1"
      onClick={() => setShowModal(true)}
    >
      <i className="fas fa-plus mr-1"></i>
      Adicionar
    </Button>
  );

  const renderModal = () => {
    return (
      <ModalForm
        show={showModal}
        onHide={onHide}
        onSubmit={handleSubmit}
        schema={schema}
        title={editIndex ? "Editar item" : "Adicionar item"}
        values={editIndex >= 0 ? items[editIndex] : undefined}
        error={error}
      />
    );
  };

  useEffect(() => {
    const hash = history.location?.hash.substring(1);
    if (showModal && !hash) onHide();
    showHashModal(hash, id, setShowModal);
  }, [items]);

  return (
    <StyledCard className="card-profile" id={id}>
      <StyledCard.Title title={title}>
        {canEdit && renderAddButton()}
      </StyledCard.Title>
      <ListGroup variant="flush" className="px-0">
        {items.map((e, i) => (
          <ListGroup.Item
            key={i}
            className="px-0 d-flex justify-content-between align-items-top"
          >
            {formatter ? formatter(e) : e}
            {canEdit && (
              <div className="flex-shrink-0 ml-2">
                <Button
                  variant="outline-primary"
                  className="btn-profile-edit-item mr-1"
                  onClick={() => handleShowEdit(i)}
                  title="Editar item"
                >
                  <i className="far fa-edit"></i>
                </Button>
                <Button
                  variant="outline-danger"
                  className="btn-profile-remove"
                  onClick={() => handleRemove(i)}
                  title="Remover item"
                >
                  <i className="far fa-trash-alt"></i>
                </Button>
              </div>
            )}
          </ListGroup.Item>
        ))}
      </ListGroup>
      {canEdit && renderModal()}
    </StyledCard>
  );
};
ProfileCard.List = List;

const Edit = ({ title, handleClick, handleSave, children }) => {
  return (
    <ProfileCard title={title} canEdit={true} handleClick={handleClick}>
      {children}
      <div className="d-flex justify-content-center">
        <Button
          id="save-profile"
          variant="primary"
          type="submit"
          onClick={handleSave}
        >
          Salvar
        </Button>
      </div>
    </ProfileCard>
  );
};
ProfileCard.Edit = Edit;

export default ProfileCard;
