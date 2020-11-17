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
import Applicant from "../pages/Applicant/Applicant";
import AboutUs from "../pages/AboutUs/AboutUs";
import Contacts from "../pages/Contacts/Contacts";
import Terms from "../pages/Terms/Terms";
import "./App.css";

const PagesRoot = () => (
  <div className="app">
    <BrowserRouter>
      <StoreProvider>
        <Switch>
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} />
          <RoutesPrivate path="/" component={Home} exact />
          <RoutesPrivate path="/profile/:type/:id" component={Profile} />
          <RoutesPrivate path="/listOpportunity" component={listOpportunity} />
          <RoutesPrivate path="/opportunity/:id" component={Opportunity} />
          <RoutesPrivate path="/company" component={Company} />
          <RoutesPrivate path="/applicant" component={Applicant} />
          <RoutesPrivate path="/aboutus" component={AboutUs} />
          <RoutesPrivate path="/contacts" component={Contacts} />
        </Switch>
      </StoreProvider>
    </BrowserRouter>
  </div>
);

export default PagesRoot;
