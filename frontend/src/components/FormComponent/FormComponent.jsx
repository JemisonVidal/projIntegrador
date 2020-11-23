import React, { useState, useEffect } from "react";
import { Form, Card, Button } from "react-bootstrap";
import Error from "../Helper/Error";

const defaultOption = { value: "", text: "Escolha uma opção." };

const FormComponent = ({
  title,
  schema,
  onSubmit,
  values,
  error,
  children
}) => {
  const [validated, setValidated] = useState(false);
  const [data, setData] = useState({});

  const handleOnChange = (key, value) => {
    data[key] = value;
    setData({ ...data });
  };

  const handleOnSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    setValidated(true);

    if (form.checkValidity() === false) {
      return event.stopPropagation();
    }

    onSubmit(data);
  };

  useEffect(() => {
    error = { error };
    if (values) {
      setData({ ...values });
    } else {
      setData({});
    }
  }, [values, schema]);

  return (
    <Form noValidate validated={validated} onSubmit={handleOnSubmit}>
      <Card>
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Text>
            {Object.entries(schema).map(([k, v]) => {
              return (
                <Form.Group key={k} controlId={k}>
                  <Form.Label>
                    {v.label}
                    {v.required && <span className="text-danger">*</span>}
                  </Form.Label>
                  <Form.Control
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
                      {v.feedback
                        ? v.feedback
                        : "Esse campo deve ser preenchido."}
                    </Form.Control.Feedback>
                  )}
                  {v.text && <Form.Text muted>{v.text}</Form.Text>}
                </Form.Group>
              );
            })}
            {children}
            {error && <Error error={error} />}
            <Button type="submit" variant="success">
              Salvar
            </Button>
          </Card.Text>
        </Card.Body>
      </Card>
    </Form>
  );
};

export default FormComponent;
