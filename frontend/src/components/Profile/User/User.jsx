import React, { useState } from 'react';
import { Button, Container, FormControl, InputGroup } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';
import { PATCH_PROFILE } from '../../../APIs/APIs';
import logo from '../../../assets/images/Logo2RecruIT.svg'
import useFetch from '../../../Hooks/useFetch';

import './User.css';

const UserEdit = ({type, id, imgSrc, title}) => {

  const [newImg, setNewImg] = useState(imgSrc);
  const [newTitle, setNewTitle] = useState(title);
  const history = useHistory();

  const { request } = useFetch();
  
  const handleSave = async () => {
    const {url, options} = PATCH_PROFILE(type, id, {imgSrc: newImg, title: newTitle});
    const {response} = await request(url, options)
    if (response.ok) history.go();
  }

  return (
    <div className='user-input'>
      <InputGroup className='my-3'>
        <InputGroup.Prepend>
          <InputGroup.Text id="input-img">Imagem</InputGroup.Text>
        </InputGroup.Prepend>
        <FormControl placeholder='https://exemplo.com/imagem.png' aria-describedby="input-img" value={newImg} onChange={(e) => setNewImg(e.target.value)} />
      </InputGroup>
      <InputGroup className='mb-1'>
        <InputGroup.Prepend>
          <InputGroup.Text id="input-title">Título</InputGroup.Text>
        </InputGroup.Prepend>
        <FormControl placeholder='Desenvolvedora Full-Stack' aria-describedby="input-title" value={newTitle} onChange={(e) => setNewTitle(e.target.value)} />
      </InputGroup>
      <div className="d-flex justify-content-center">
        <Button id="save-profile" variant="primary" type="submit" onClick={handleSave}>
          Salvar
        </Button>
      </div>
    </div>
  )

}

const User = ({ type, id, imgSrc, name, title, canEdit }) => {

  const [edit, setEdit] = useState(false);

  const handleClick = (e) => {
    e.preventDefault();
    if (!edit) setEdit(true);
    else if (window.confirm('Cancelar alterações?')) setEdit(false);
  }
  
  return (
    <Container className="user text-center mb-3">
      <img
        src={imgSrc ? imgSrc : logo}
        alt={name}
        id="user-picture"
      />
      <h4 id="user-name" className="font-weight-bold">
        {name}
      </h4>
      <h5 id="user-title" className="font-weight-light">
        {title}
      </h5>
      {canEdit && (
        <button className="btn-profile-edit" onClick={handleClick}>
          <i className="far fa-edit"></i>
        </button>
      )}
      {edit && <UserEdit type={type} id={id} imgSrc={imgSrc} title={title} />}
    </Container>
  );
};

export default User;
