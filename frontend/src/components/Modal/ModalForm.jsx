import React, { useEffect, useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import Error from "../Helper/Error";

import "./ModalForm.css";

const defaultOption = { value: "", text: "Escolha uma opção." };

const ModalForm = ({
  title,
  schema,
  show,
  onHide,
  onSubmit,
  values,
  error,
  list,
  setList,
  indexRequirement
}) => {
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({});

  const handleOnHide = () => {
    setData({});
    setValidated(false);
    onHide();
  };

  const handleOnChange = (key, value) => {
    data[key] = value;
    setData({ ...data });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    setValidated(true);
    if (form.checkValidity() === false) {
      return event.stopPropagation();
    }
    if (onSubmit) {
      onSubmit(data);
    } else {
      if (indexRequirement >= 0) {
        const newList = [...list];
        newList[indexRequirement] = data;
        setList([...newList]);
      } else {
        setList([...list, data]);
      }
      handleOnHide();
    }
  };

  useEffect(() => {
    if (values) setData({ ...values });
    else setData({});
  }, [values, schema]);

  return (
    <Modal
      show={show}
      onHide={handleOnHide}
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">{title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {Object.entries(schema).map(([k, v]) => (
            <Form.Group className="groupModal" key={k} controlId={k}>
              <Form.Label className="labelModal">
                {v.label}
                {v.required && <span className="text-danger">*</span>}
              </Form.Label>
              <Form.Control
                className="inputModal"
                as={v.as || "input"}
                type={v.type || "text"}
                required={v.required}
                placeholder={v.placeholder}
                pattern={v.pattern}
                value={data[k] || ""}
                onChange={(e) => handleOnChange(k, e.target.value)}
              >
                {v.options &&
                  [defaultOption].concat(v.options).map((o, i) => (
                    <option key={i} value={o.value}>
                      {o.text}
                    </option>
                  ))}
              </Form.Control>
              {(v.required || v.feedback) && (
                <Form.Control.Feedback type="invalid">
                  {v.feedback ? v.feedback : "Esse campo deve ser preenchido."}
                </Form.Control.Feedback>
              )}
              {v.text && <Form.Text muted>{v.text}</Form.Text>}
            </Form.Group>
          ))}
          {error && <Error error={error} />}
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={handleOnHide}>Fechar</Button>
          <Button type="submit" variant="success">
            Salvar
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default ModalForm;
