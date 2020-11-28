import { removeWhitespace } from "../utils/formatters";
import { requestOptions } from "./configAPI";

export function GET_PROFILE(type, id) {
  return requestOptions({
    url: `/profile/${type}/${id}`
  });
}

export function PATCH_PROFILE(type, id, body) {
  return requestOptions({
    url: `/profile/${type}/${id}`,
    method: "PATCH",
    body
  });
}

export function GET_AVATAR(type, id) {
  return requestOptions({
    url: `/profile/${type}/${id}/avatar`,
    isAuthenticated: false
  });
}

export function PATCH_AVATAR(type, id, body) {
  return requestOptions({
    url: `/profile/${type}/${id}/avatar`,
    method: "PATCH",
    body
  });
}

export function GET_COMPANY_OPPORTUNITIES(id) {
  return requestOptions({
    url: `/opportunity/company/${id}`
  });
}

export function GET_ALL_APPLICANT(page = 0, name, skills) {
  let uri = `/profile/applicant/?page=${page}&size=6`;
  if (name) {
    uri += `&name=${name}`;
  }
  if (skills) {
    uri += `&skills=${removeWhitespace(skills)}`;
  }
  return requestOptions({
    url: uri
  });
}
