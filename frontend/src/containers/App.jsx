import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'font-awesome/css/font-awesome.min.css'
import {
  BrowserRouter,
  Switch,
  Route,
} from 'react-router-dom';
import StoreProvider from '../components/Store/Provider';
import RoutesPrivate from '../Routes/PrivateRoute';
import Home from '../pages/Home/Home';
import Register from '../pages/Register/Register';
import Login from '../pages/Login/Login';
import Profile from '../pages/Profile/Profile'

const PagesRoot = () => (
  <BrowserRouter>
    <StoreProvider>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <RoutesPrivate path="/" component={Home} exact />
        <RoutesPrivate path="/profile/:type/:id" component={Profile} />
      </Switch>
    </StoreProvider>
  </BrowserRouter>
)


export default PagesRoot;
