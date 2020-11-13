import React from 'react';
import { Button, Card, Container, ListGroup } from 'react-bootstrap';
import './ProfileCard.css';

const BaseCard = ({ title, titleChildren, children }) => {
  return (
    <Container fluid="md" className="py-2">
      <Card className="card-profile">
        <Card.Body className="p-2 p-md-3">
          {title && (
            <Card.Title className="d-flex justify-content-between align-items-center">
              {title}
              {titleChildren}
            </Card.Title>
          )}
          {children}
        </Card.Body>
      </Card>
    </Container>
  );
};

const ProfileCard = ({ title, canEdit, handleClick, children }) => {
  const editIcon = (
    <button className="btn-profile-edit" onClick={handleClick} title="Editar">
      <i className="far fa-edit"></i>
    </button>
  );
  return (
    <BaseCard title={title} titleChildren={canEdit && editIcon}>
      {children}
    </BaseCard>
  );
};

const List = ({
  title,
  canEdit,
  handleAdd,
  handleEdit,
  handleRemove,
  items,
  formatter,
}) => {
  const addButton = (
    <Button
      variant="outline-primary"
      className="btn-profile-add"
      onClick={handleAdd}
    >
      <i className="fas fa-plus mr-1"></i>
      Adicionar
    </Button>
  );
  return (
    <BaseCard title={title} titleChildren={canEdit && addButton}>
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
                  onClick={() => handleEdit(i)}
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
    </BaseCard>
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
