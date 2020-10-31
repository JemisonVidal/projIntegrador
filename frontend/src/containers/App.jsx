import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import StoreProvider from "../components/Store/Provider";
import RoutesPrivate from "../Routes/PrivateRoute";
import Home from "../pages/Home/Home";
import Register from "../pages/Register/Register";
import Login from "../pages/Login/Login";
import Profile from "../pages/Profile/Profile";
import Opportunity from "../pages/Opportunity/Opportunity";

const PagesRoot = () => (
  <BrowserRouter>
    <StoreProvider>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <Route path="/" component={Home} exact />
        <Route path="/profile/:type/:id" component={Profile} />
        <Route path="/opportunity" component={Opportunity} />
      </Switch>
    </StoreProvider>
  </BrowserRouter>
);

export default PagesRoot;
