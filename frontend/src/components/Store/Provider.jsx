import React from 'react';
import Context from './Context';
import useStorage from '../../utils/useStorage';

const StoreProvider = ({ children }) => {
  const [apptoken, setToken] = useStorage('apptoken');

  return (
    <Context.Provider
      value={{
        apptoken,
        setToken,
      }}
    >
      {children}
    </Context.Provider>
  )
}


export default StoreProvider;
