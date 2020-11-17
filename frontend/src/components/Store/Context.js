import { createContext } from "react";

const StoreContext = createContext({
  apptoken: null,
  setToken: () => {},
  user: {
    type: null,
    sub: null,
    iat: null,
    exp: null
  },
  avatar: null
});

export default StoreContext;
