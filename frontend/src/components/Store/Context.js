import { createContext } from 'react';

const StoreContext = createContext({
  apptoken: null,
  setToken: () => { },
});

export default StoreContext;
