import React from "react";
import { Form } from "react-bootstrap";
import "./Input.css";

const Input = ({
  namelabel,
  classes,
  type,
  as,
  value,
  onChange,
  onBlur,
  options,
  error,
  required,
  rows
}) => {
  const [valueModal, setValueModal] = React.useState(value);

  return (
    <>
      <Form.Label className={`label ${classes?.label}`} htmlFor={namelabel}>
        {namelabel} {required && <span className="text-danger">*</span>}
      </Form.Label>
      {type || type === "textarea" ? (
        <Form.Control
          className={`Input ${classes?.input}`}
          id={namelabel}
          name={namelabel}
          {...(type === "textarea" ? { as: "textarea" } : { type: type })}
          rows={rows}
          value={onChange ? value : valueModal}
          onChange={
            onChange
              ? onChange
              : (e) => {
                  setValueModal(e.target.value);
                }
          }
          onBlur={onBlur}
          required={required}
        />
      ) : (
        <Form.Control
          className="Input"
          id={namelabel}
          name={namelabel}
          as={as}
          value={value}
          onChange={onChange}
          onBlur={onBlur}
          required={required}
        >
          {options &&
            options.map(({ value, text }) => (
              <option value={value} key={value}>
                {text}
              </option>
            ))}
        </Form.Control>
      )}
      {error && <p className="error">{error}</p>}
    </>
  );
};
export default Input;
