import { requestOptions } from "./configAPI";

export function GET_AVATAR(type, id) {
  return requestOptions({
    url: `/profile/${type}/${id}/avatar`,
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

export function GET_OPPORTUNITY(id) {
  return requestOptions({
    url: `/opportunity/${id}`,
  });
}

export function GET_LIST_OPPORTUNITY() {
  return requestOptions({
    url: `/opportunity/`,
  });
}
