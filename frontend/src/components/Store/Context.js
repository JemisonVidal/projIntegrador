import { createContext } from "react";

const StoreContext = createContext({
  apptoken: null,
  setToken: () => {},
  user: null,
});

export default StoreContext;
