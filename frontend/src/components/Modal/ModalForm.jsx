import React, { useEffect, useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';

const defaultOption = {value: '', text: 'Escolha uma opção.'};

const ModalForm = ({
  title,
  schema,
  show,
  onHide,
  onSubmit,
  values,
}) => {
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({});

  function handleOnChange(key, value) {
    data[key] = value;
    setData({ ...data });
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    setValidated(true);
    if (form.checkValidity() === false) {
      return event.stopPropagation();
    }
    onSubmit(data);
  };

  useEffect(() => {
    if (values) setData({ ...values });
    else
      setData(
        Object.keys(schema).reduce((acc, k) => {
          acc[k] = '';
          return acc;
        }, {})
      );
  }, [values, schema]);

  return (
    <Modal
      show={show}
      onHide={onHide}
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">{title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {Object.entries(schema).map(([k, v]) => (
            <Form.Group key={k} controlId={k}>
              <Form.Label>
                {v.label}
                {v.required && '*'}
              </Form.Label>
              <Form.Control
                as={v.as || 'input'}
                type={v.type || 'text'}
                required={v.required}
                placeholder={v.placeholder}
                value={data[k] || ''}
                onChange={(e) => handleOnChange(k, e.target.value)}
              >
                {v.options && [defaultOption].concat(v.options).map((o, i) => <option key={i} value={o.value}>{o.text}</option>)}
              </Form.Control>
              {v.required && (
                <Form.Control.Feedback type="invalid">
                  Esse campo deve ser preenchido.
                </Form.Control.Feedback>
              )}
              {v.text && <Form.Text muted>{v.text}</Form.Text>}
            </Form.Group>
          ))}
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={onHide}>Fechar</Button>
          <Button type="submit" variant="success">
            Salvar
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default ModalForm;
