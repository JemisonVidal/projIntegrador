import React from 'react';
import Profile from '../../components/Profile/Profile';
import Main from '../../components/Template/main/Main';
import './Profile.css';

const PagesProfile = ({ computedMatch }) => {
  return (
    <Main>
      <Profile id={computedMatch.params.id} type={computedMatch.params.type} />
    </Main>
  );
};

export default PagesProfile;
