import React, { useState } from "react";
import { Container, ResponsiveEmbed } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import { PATCH_AVATAR } from "../../../APIs/profileAPI";
import logo from "../../../assets/images/businesswoman.svg";
import useFetch from "../../../Hooks/useFetch";
import ModalForm from "../../Modal/ModalForm";

import "./User.css";

const schema = {
  name: {
    label: "Nome",
    type: "text",
    required: true
  },
  imgSrc: {
    label: "Imagem do perfil",
    type: "text",
    placeholder: "https://exemplo.com/imagem.png"
  },
  title: {
    label: "Título",
    type: "text",
    placeholder: "Desenvolvedora Full-Stack"
  }
};

const User = ({ type, id, imgSrc, name, title, canEdit }) => {
  const [showModal, setShowModal] = useState(false);
  const history = useHistory();
  const { request } = useFetch();

  const handleSubmit = async (body) => {
    const { url, options } = PATCH_AVATAR(type, id, body);
    const { response } = await request(url, options);
    if (response.ok) history.go();
  };

  const renderModal = () => {
    const values = {
      imgSrc,
      name,
      title
    };
    return (
      <ModalForm
        show={showModal}
        onHide={() => setShowModal(false)}
        onSubmit={handleSubmit}
        schema={schema}
        title={"Editar perfil"}
        values={values}
      />
    );
  };

  return (
    <Container className="user text-center mb-3">
      <ResponsiveEmbed aspectRatio="1by1" id="user-picture">
        <img src={imgSrc ? imgSrc : logo} alt={name} />
      </ResponsiveEmbed>
      <h4 id="user-name" className="font-weight-bold">
        {name}
      </h4>
      <h5 id="user-title" className="font-weight-light">
        {title}
      </h5>
      {canEdit && (
        <button className="btn-profile-edit" onClick={() => setShowModal(true)}>
          <i className="far fa-edit"></i>
        </button>
      )}
      {canEdit && renderModal()}
    </Container>
  );
};

export default User;
