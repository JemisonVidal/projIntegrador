import React from "react";
import { Form } from "react-bootstrap";
import "./Checkbox.css";

const Checkbox = (props) => {
  return <Form.Check {...props} />;
};
export default Checkbox;
