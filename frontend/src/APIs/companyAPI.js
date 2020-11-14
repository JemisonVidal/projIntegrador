import { requestOptions } from "./configAPI";

export function GET_COMPANYS(page = 0, name = "") {
  let uri = `/profile/company/?page=${page}`;
  if (name) {
    uri += `&name=${name}`;
  }

  return requestOptions({
    url: uri,
  });
}
