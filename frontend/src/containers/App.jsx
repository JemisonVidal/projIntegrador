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
import listOpportunity from "../pages/ListOpportunity/ListOpportunity";
import Opportunity from "../pages/Opportunity/Opportunity";
import Company from "../pages/Company/Company";

const PagesRoot = () => (
  <BrowserRouter>
    <StoreProvider>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <Route path="/" component={Home} exact />
        <Route path="/profile/:type/:id" component={Profile} />
        <Route path="/listOpportunity" component={listOpportunity} />
        <Route path="/opportunity/:id" component={Opportunity} />
        <Route path="/company" component={Company} />
      </Switch>
    </StoreProvider>
  </BrowserRouter>
);

export default PagesRoot;
