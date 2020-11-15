export const baseURL = "https://pi.ggwadera.xyz/v1/api";

export const requestOptions = ({
  url,
  method = "GET",
  body,
  isAuthenticated = true,
}) => {
  const jwt = localStorage.getItem("apptoken");
  const request = {
    url: baseURL + url,
    options: {
      method: method,
      headers: {
        "Content-Type": "application/json",
      },
      body: body ? JSON.stringify(body) : undefined,
    },
  };
  if (isAuthenticated) {
    request.options.headers["Authorization"] = "Bearer " + JSON.parse(jwt);
  }
  return request;
};
