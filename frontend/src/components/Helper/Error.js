import React from "react";
import { Alert } from "react-bootstrap";
import "./Error.css";

const Error = ({ error }) => {
  if (!error) return null;
  return (
    <Alert className="error-request" variant="danger">
      {error}
    </Alert>
  );
};

export default Error;
