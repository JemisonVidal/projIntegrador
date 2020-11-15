/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect, useState } from "react";
import Context from "./Context";
import useStorage from "../../utils/useStorage";
import jwt_decode from "jwt-decode";
import logo from "../../assets/images/Logo2RecruIT.svg";
import { GET_AVATAR } from "../../APIs/APIs";
import useFetch from "../../Hooks/useFetch";

const StoreProvider = ({ children }) => {
  const [apptoken, setToken] = useStorage("apptoken");
  const [avatar, setAvatar] = useState(logo);
  const { request } = useFetch();

  let user = {};
  if (apptoken) user = jwt_decode(apptoken);

  const { type, sub, exp } = user;
  if (!type || !sub || !exp || new Date() > new Date(exp * 1000)) {
    localStorage.removeItem(apptoken);
  }

  useEffect(() => {
    (async () => {
      if (!user.type && !user.pid) return;
      const { url, options } = GET_AVATAR(user.type, user.pid);
      const { response, json } = await request(url, options);
      if (response?.ok && json.imgSrc) {
        setAvatar(json.imgSrc);
      }
    })();
  }, [user.type, user.pid]);

  return (
    <Context.Provider
      value={{
        apptoken,
        setToken,
        user,
        avatar,
      }}
    >
      {children}
    </Context.Provider>
  );
};

export default StoreProvider;
