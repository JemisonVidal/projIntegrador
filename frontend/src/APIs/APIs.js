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

export function GET_LIST_OPPORTUNITY(page = 0, name = "") {
  let uri = `/opportunity/?page=${page}`;
  if (name) {
    uri += `&name=${name}`;
  }

  return requestOptions({
    url: uri,
  });
}

export function POST_APPLY(id) {
  return requestOptions({
    url: `/opportunity/${id}/apply`,
    method: "POST"
  });
};
