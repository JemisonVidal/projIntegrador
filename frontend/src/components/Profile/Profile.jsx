/* eslint-disable react-hooks/exhaustive-deps */
import React, { useContext, useEffect } from "react";
import { Container, Spinner } from "react-bootstrap";
import User from "./User/User";
import CompanyProfile from "./CompanyProfile/CompanyProfile";
import ApplicantProfile from "./ApplicantProfile/ApplicantProfile";

import "./Profile.css";
import useFetch from "../../Hooks/useFetch";
import { GET_PROFILE } from "../../APIs/profileAPI";
import { useState } from "react";
import StoreContext from "../Store/Context";

const Profile = ({ type, id }) => {
  const { request, loading } = useFetch();
  const { url, options } = GET_PROFILE(type, id);
  const [profileData, setProfileData] = useState({});
  const { user } = useContext(StoreContext);
  const [canEdit, setCanEdit] = useState(false);

  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
    (async () => {
      const { response, json } = await request(url, options);
      if (response?.ok) {
        setProfileData(json);
        setCanEdit(type === user.type && Number(id) === user.pid);
      }
    })();
  }, [url]);

  function renderLoading() {
    return (
      <div className="spinner-load">
        <Spinner animation="border" />
      </div>
    );
  }

  const renderProfile = () => {
    if (type === "company") {
      return (
        <CompanyProfile
          profileId={id}
          data={profileData}
          setData={setProfileData}
          canEdit={canEdit}
        />
      );
    }
    return (
      <ApplicantProfile
        profileId={id}
        data={profileData}
        setData={setProfileData}
        canEdit={canEdit}
      />
    );
  };

  return (
    <Container fluid className="container-profile p-0">
      {loading ? (
        renderLoading()
      ) : (
        <>
          <User
            type={type}
            id={id}
            imgSrc={profileData.imgSrc}
            name={profileData.name}
            title={profileData.title}
            canEdit={canEdit}
          />
          {renderProfile()}
        </>
      )}
    </Container>
  );
};

export default Profile;
