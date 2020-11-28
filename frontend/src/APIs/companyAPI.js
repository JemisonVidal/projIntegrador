import { requestOptions } from "./configAPI";

export function GET_COMPANIES(page = 0, name, category) {
  let uri = `/profile/company/?page=${page}&size=6`;
  if (name) {
    uri += `&name=${name}`;
  }
  if (category) {
    uri += `&name=${category}`;
  }
  return requestOptions({
    url: uri
  });
}
