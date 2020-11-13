import { requestOptions } from "./configAPI";

export function GET_AVATAR(type, id) {
  return requestOptions({
    url: `/profile/${type}/${id}/avhttpSecurity.csrf().disable();
        httpSecurity.cors();atar`,
    isAuthenticated: false,
  });
}

export function GET_PROFILE(type, id) {
  return requestOptions({
    url: `/profile/${type}/${id}`,
  });
}

export function PATCH_PROFILE(type, id, body) {
  return requestOptions({
    url: `/profile/${type}/${id}`,
    method: "PATCH",
    body,
  });
}
