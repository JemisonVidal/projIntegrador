import { requestOptions } from "./configAPI";

export function GET_COMPANYS() {
  return requestOptions({
    url: `/profile/company/`,
  });
}
