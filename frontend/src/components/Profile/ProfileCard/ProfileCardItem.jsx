import React from 'react';
import { useState } from 'react';
import { Col, Row } from 'react-bootstrap';

const ProfileCardItem = ({ title, value, formatter }) => {
  return (
    <Row className="profile-item">
      <Col sm={12} md="auto">
        <span className="font-weight-bold">{title}</span>
      </Col>
      <Col sm={12} md>
        {value && formatter ? formatter(value) : value}
      </Col>
    </Row>
  );
};

const Edit = ({ name, data, fields, setData }) => {
  const [value, setValue] = useState(data.value || '');

  const handleOnChange = (e) => {
    e.preventDefault();
    const newData = { ...fields };
    const newValue = e.target.value;
    setValue(newValue);
    newData[name] = newValue;
    setData(newData);
  };

  return (
    <Row className="profile-item">
      <Col sm={12} md="auto">
        <label htmlFor={name} className="font-weight-bold">
          {data.text}
        </label>
      </Col>
      <Col sm={12} md>
        {data.type === 'textarea' ? (
          <textarea
            id={name}
            name={name}
            value={value}
            rows="3"
            onChange={handleOnChange}
          ></textarea>
        ) : (
          <input
            id={name}
            type={data.type || 'text'}
            name={name}
            value={value}
            onChange={handleOnChange}
            placeholder={data.placeholder}
          />
        )}
      </Col>
    </Row>
  );
};
ProfileCardItem.Edit = Edit;

export default ProfileCardItem;
