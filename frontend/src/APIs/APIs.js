import { requestOptions } from "./configAPI";

export function GET_OPPORTUNITY(id) {
  return requestOptions({
    url: `/opportunity/${id}`
  });
}

export function GET_APPLICANTS_OPPORTUNITY(id) {
  return requestOptions({
    url: `/opportunity/${id}/applied`
  });
}

export function GET_LIST_OPPORTUNITY(page = 0, name = "") {
  let uri = `/opportunity/?page=${page}&size=6`;
  if (name) {
    uri += `&name=${name}`;
  }

  return requestOptions({
    url: uri
  });
}

export function GET_MY_OPPORTUNITY(id) {
  let uri = `/opportunity/applied?id=${id}`;
  return requestOptions({
    url: uri
  });
}

export function POST_APPLY(id) {
  return requestOptions({
    url: `/opportunity/${id}/apply`,
    method: "POST"
  });
}

export function CREATE_OPPORTUNITY(body) {
  return requestOptions({
    url: `/opportunity`,
    method: "POST",
    body
  });
}

export function PATCH_OPPORTUNITY(id, body) {
  return requestOptions({
    url: `/opportunity/${id}`,
    method: "PATCH",
    body
  });
}
