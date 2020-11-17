import React, { useState } from "react";
import FormComponent from "../FormComponent/FormComponent";
import { Form } from "react-bootstrap";

const schema = {
  name: {
    label: "Nome",
    type: "text",
    required: true
  },
  location: {
    label: "Localização",
    type: "text",
    required: true
  },
  description: {
    label: "Descrição",
    type: "text",
    required: true,
    as: "textarea"
  },
  benefits: {
    label: "Benefícios",
    type: "text",
    required: true
  },
  salary: {
    label: "Salário",
    type: "number",
    required: true
  },
  freeText: {
    label: "Texto livre",
    type: "text",
    as: "textarea"
  }
};

const AddOpportunity = (props) => {
  const [error, setError] = useState(null);

  return (
    <FormComponent
      title="Adicionar oportunidade"
      schema={schema}
      onSubmit={(valor) => {
        valor.active = true;
        console.log(valor);
      }}
      error={error}
    >
      <Form.Check type="checkbox" label="Status" id="status"></Form.Check>
    </FormComponent>
  );
};

export default AddOpportunity;
