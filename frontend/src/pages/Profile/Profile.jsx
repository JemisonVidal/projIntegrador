import React from 'react';
import Profile from '../../components/Profile/Profile';
import './Profile.css';

const PagesProfile = ({ computedMatch }) => {
  return (
    <Profile id={computedMatch.params.id} type={computedMatch.params.type} />
  );
};

export default PagesProfile;
