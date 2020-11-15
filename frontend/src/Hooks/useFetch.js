/* eslint-disable react-hooks/exhaustive-deps */
import React from "react";
import { useHistory } from "react-router-dom";

const useFetch = () => {
  const [data, setData] = React.useState(null);
  const [error, setError] = React.useState(null);
  const [loading, setLoading] = React.useState(false);
  const history = useHistory();

  const request = React.useCallback(async (url, options) => {
    let response;
    let json;
    try {
      setError(null);
      setLoading(true);
      response = await fetch(url, options);
      if (response.status === 401) history.push("/login");
      json = await response.json();
      if (response.ok === false) setError(json.msg);
    } catch (err) {
      setError(err.message);
    } finally {
      setData(json);
      setLoading(false);
      return { response, json };
    }
  }, []);

  return {
    data,
    loading,
    error,
    request,
  };
};

export default useFetch;
