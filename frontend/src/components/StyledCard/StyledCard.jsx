import React from "react";
import { Card } from "react-bootstrap";
import "./StyledCard.css";

const StyledCard = ({ children, className, id }) => {
  let classCustom = "card-custom px-0";
  if (className) {
    classCustom += ` ${className}`;
  }
  return (
    <Card className={classCustom} id={id}>
      <Card.Body>{children}</Card.Body>
    </Card>
  );
};

const Title = ({ title, children, className }) => {
  let classCustom = "d-flex justify-content-between align-items-center";
  if (className) {
    classCustom += ` ${className}`;
  }
  return (
    <Card.Title className={classCustom}>
      {title}
      {children}
    </Card.Title>
  );
};
StyledCard.Title = Title;

export default StyledCard;
