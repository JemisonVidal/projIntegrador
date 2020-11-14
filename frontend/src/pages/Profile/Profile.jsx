import React from "react";
import Profile from "../../components/Profile/Profile";
import Main from "../../components/Template/main/Main";
import "./Profile.css";

const PagesProfile = (props) => {
  return (
    <Main>
      <Profile
        id={props.computedMatch.params.id}
        type={props.computedMatch.params.type}
      />
    </Main>
  );
};

export default PagesProfile;
