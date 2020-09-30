import React from 'react';
import ProfileCard from '../ProfileCard/ProfileCard';

const CompanyProfile = ({ data }) => {

  const generalInformation = {
    'Local': data.city,
  }

  return (
    <div>
      <ProfileCard title='Informações Gerais' data={generalInformation} />
    </div>
  )
}

export default CompanyProfile;