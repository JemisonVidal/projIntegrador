import React from 'react';
import { Form } from 'react-bootstrap';
import './Input.css';

const Input = (props) => {
  return (
    <>
      <Form.Label className="label">{props.nameLabel}</Form.Label>
      <Form.Control className="Input"{...props}>
        {props.options && props.options.map(op => <option>{op}</option>)}

      </Form.Control>
    </>
  );
}
export default Input