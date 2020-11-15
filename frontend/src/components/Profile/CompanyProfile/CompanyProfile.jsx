import React, { useState } from 'react';
import { useEffect } from 'react';
import ProfileInfo from '../ProfileInfo';
import useFetch from '../../../Hooks/useFetch';
import { PATCH_PROFILE } from '../../../APIs/APIs';
import { linkFormatter } from '../../../utils/formatters';

const CompanyProfile = ({ data, canEdit, profileId }) => {
  const { request } = useFetch();
  const [info, setInfo] = useState({});

  const handleSubmit = (body) => {
    const { url, options } = PATCH_PROFILE("company", profileId, body);
    return request(url, options);
  };

  useEffect(() => {
    setInfo({
      about: { text: "Sobre", value: data.about, type: "textarea" },
      location: { text: "Localização", value: data.location },
      category: {text: "Categoria", value: data.category, required: true},
      site: {
        text: "Site",
        value: data.site,
        formatter: linkFormatter,
        placeholder: "https://sitedaempresa.com.br",
      },
      linkedin: {
        text: "LinkedIn",
        value: data.linkedin,
        formatter: linkFormatter,
        placeholder: "https://linkedin.com/company/empresa",
      },
    });
  }, [data]);

  return (
    <div>
      <ProfileInfo
        data={info}
        setData={setInfo}
        canEdit={canEdit}
        handleSubmit={handleSubmit}
      />
    </div>
  );
};

export default CompanyProfile;
