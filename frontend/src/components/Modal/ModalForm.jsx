import React from 'react'
import { Button, Container, Modal, Row } from 'react-bootstrap';
import Input from '../Input/Input'

const ModalForm = (props) => {
  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {props.title}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body className='show-grid'>
        <Container>
          <Row>
            {Object.entries(props.data).map(([key, value]) => (
              <Input
                key={key}
                namelabel={key}
                type="text"
                required
                value={value}
              />
            ))}

          </Row>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide} variant="secundary">Cancelar</Button>
        <Button variant="primary">Salvar</Button>
      </Modal.Footer>
    </Modal>
  );

}

export default ModalForm