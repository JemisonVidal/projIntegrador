import { requestOptions } from "./configAPI";

export const USER_REGISTER = (body) => {
  return requestOptions({
    url: "/register",
    method: "POST",
    body,
    isAuthenticated: false,
  });
};

export const USER_LOGIN = (body) => {
  return requestOptions({
    url: "/authenticate",
    method: "POST",
    body,
    isAuthenticated: false,
  });
};
