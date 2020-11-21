import React from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import Main from "../../components/Template/main/Main";

import "./Page404.css";

const Page404 = () => {
  return (
    <Main>
      <div className="paginaErro">
        <h1>Está página não está funcionando</h1>
        <p>
          O link pode não estar funcionando ou a Página pode ter sido removida.
          Verifique se o link que você está tentando abrir está correto.
        </p>
        <Link to={`/`}>
          <Button className="buttonHome404" variant="primary">
            Ir para a Home
          </Button>
        </Link>
      </div>
    </Main>
  );
};

export default Page404;
