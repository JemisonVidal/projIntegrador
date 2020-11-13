import React from 'react';
import ProfileCard from '../ProfileCard/ProfileCard';
import './CompanyProfile.css'

const CompanyProfile = ({ data }) => {

  const about = {
    'Sobre': data.about,
  }

  const additional = {
    "Endereço": data.address,
    "Tempo de existência": data.lifetime,
    "Ramo": data.branch,
    "Site": data.site
  }
  return (
    <div>
      <ProfileCard title='Visão Geral' data={about} />
      <ProfileCard title='Informações Gerais' data={additional} />
      {
        data.opportunitys &&
        <div className="opportunitys">
          <div className="sidebar" >
            {renderOpportunitys(data.opportunitys)}
          </div>
        </div>
      }
    </div>
  )
}

const renderOpportunitys = (opportunitys) => {
  return (
    opportunitys.map(o => {
      const data = {
        description: o.description,
        activities: o.activities
      }
      return (
        <div key={o.id}>
          <ProfileCard title={o.title} data={data} />
        </div>
      )
    })
  )
}



export default CompanyProfile;