import React from "react";
import Context from "./Context";
import useStorage from "../../utils/useStorage";
import jwt_decode from "jwt-decode";

const StoreProvider = ({ children }) => {
  const [apptoken, setToken] = useStorage("apptoken");
  let user = {};
  if (apptoken) user = jwt_decode(apptoken);

  return (
    <Context.Provider
      value={{
        apptoken,
        setToken,
        user,
      }}
    >
      {children}
    </Context.Provider>
  );
};

export default StoreProvider;
