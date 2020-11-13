import React, { useEffect, useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';

const ModalForm = ({ title, schema, show, onHide, onSubmit, values }) => {
  const [data, setData] = useState({});

  function handleOnChange(key, value) {
    data[key] = value;
    setData({ ...data });
  }

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
      <Form>
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
              />
              {v.text && <Form.Text muted>{v.text}</Form.Text>}
            </Form.Group>
          ))}
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={onHide}>Fechar</Button>
          <Button variant="success" onClick={() => onSubmit(data)}>
            Salvar
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default ModalForm;
