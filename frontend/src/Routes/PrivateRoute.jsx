import React, { useContext } from 'react';
import { Route, Redirect } from 'react-router-dom';
import StoreContext from '../components/Store/Context';

const RoutesPrivate = ({ component: Component, ...rest }) => {
  const { apptoken } = useContext(StoreContext);

  return (
    <Route
      {...rest}
      render={() => apptoken
        ? <Component {...rest} />
        : <Redirect to="/login" />
      }
    />
  )
}

export default RoutesPrivate;
