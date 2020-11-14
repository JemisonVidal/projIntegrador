import React, { useEffect } from "react";
import { Container } from "react-bootstrap";
import User from "./User/User";
import CompanyProfile from "./CompanyProfile/CompanyProfile";
import UserProfile from "./UserProfile/UserProfile";
import logo from "../../assets/images/Logo2RecruIT.png";
import { Link } from "react-router-dom";

import "./Profile.css";
// import useFetch from '../../Hooks/useFetch';
// import { GET_PROFILE } from '../../APIs/APIs';

const Profile = ({ type, id }) => {
  
  // const {data, loading, request} = useFetch();
  // const {url, options} = GET_PROFILE(type, id)

  const user = {
    imgSrc: `http://placekitten.com/300/300`,
    name: "Rebeca Souza",
    title: "Desenvolvedora Full-Stack",
  };

  const userData = {
    canEdit: true,
    city: "São Paulo, SP",
    experience: "3 anos",
    desiredCity: "São Paulo, SP",
    skills: [
      "Python",
      "Javascript",
      "Java",
      "C#",
      ".NET",
      "HTML5",
      "MySQL",
      "SAS",
      "C++",
      "Angular",
      "Java",
      "React",
    ],
    links: {
      github: "https://github.com/rebecasouza",
      linkedin: "https://linkedin.com/in/rebecasouza",
    },
    companies: [
      {
        name: "Santander",
        position: "Estagiária Front-End",
        acting: true,
        initialDate: "01/2019",
      },
      {
        name: "Microsoft",
        position: "Estagiária Back-End",
        acting: false,
        initialDate: "01/2018",
        finalDate: "12/2018",
      },
    ],
    courses: [
      {
        name: "Ciência da Computação",
        institution: "USP",
        conclusionYear: "12/2019",
      },
    ],
  };

  const companyDataMock = {
    imgSrc: `http://placekitten.com/300/300`,
    name: "Flash System",
    about:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer mi massa, tincidunt in odio tincidunt, lobortis dignissim nisl. Quisque metus erat, bibendum eget fermentum at, tempus sit amet diam. Nunc vel tempor elit, vitae scelerisque massa. Cras semper velit fermentum magna placerat placerat. Phasellus at felis et lorem molestie luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Sed pulvinar massa ut mauris pharetra tempus. Phasellus facilisis, nisl at hendrerit sagittis, augue nisi mollis augue, eu fermentum lacus dolor eu nisi. Praesent felis enim, facilisis at lacinia eu, ornare ut ipsum. Nunc quis nunc ut nunc aliquam tempus. Integer ornare ut purus at vestibulum. Nulla massa nunc, elementum nec massa nec, efficitur laoreet ligula. Vivamus id felis placerat magna commodo gravida.",
    main: "IT service and support",
    address: "São Paulo, SP",
    lifetime: "3 years",
    branch: "system development and support",
    site: "www.loremIpsum.com.br",
    opportunitys: [
      {
        id: 1,
        title: "Programadora Full Stack",
        description: "Desenvolver novas features",
        activities: "Lorem ipsum dolor sit amet",
      },
      {
        id: 2,
        title: "Programadora Full Stack",
        description: "Desenvolver novas features",
        activities: "Lorem ipsum dolor sit amet",
      },
      {
        id: 3,
        title: "Programadora Full Stack",
        description: "Desenvolver novas features",
        activities: "Lorem ipsum dolor sit amet",
      },
    ],
  };
  // useEffect(() => {
  //   request(url, options)
  // }, [])
  return (
    <Container fluid className="container-profile p-0">
      {type === "company" ? (
        <>
          <User
            imgSrc={companyDataMock.imgSrc}
            name={companyDataMock.name}
            title={companyDataMock.main}
          />
          <CompanyProfile data={companyDataMock} />
        </>
      ) : (
        <>
          <User imgSrc={user.imgSrc} name={user.name} title={user.title} />
          <UserProfile data={userData} />
        </>
      )}
    </Container>
  );
};

export default Profile;
