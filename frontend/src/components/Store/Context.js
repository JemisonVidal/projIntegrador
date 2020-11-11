import { createContext } from "react";

const StoreContext = createContext({
  apptoken: null,
  setToken: () => {},
  user: {
    name: null,
    type: null,
    sub: null,
    iat: null,
    exp: null,
  },
});

export default StoreContext;
