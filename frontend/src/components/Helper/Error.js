import React from "react";
import { Alert } from "react-bootstrap";

const Error = ({ error }) => {
  if (!error) return null;
  return <Alert variant="danger">{error}</Alert>;
};

export default Error;
