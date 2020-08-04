import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'font-awesome/css/font-awesome.min.css'
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from 'react-router-dom';
import StoreProvider from '../components/Store/Provider';
import RoutesPrivate from '../Routes/PrivateRoute';
import Home from '../pages/Home/Home';
import Pag2 from '../pages/Pagina2/Pag2';
import Login from '../pages/Login/Login';

const PagesRoot = () => (
  <Router>
    <StoreProvider>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/pag2" component={Pag2} />
        <RoutesPrivate path="/" component={Home} />

      </Switch>
    </StoreProvider>
  </Router>
)


export default PagesRoot;
