export const baseURL = "http://localhost:8080/";

export function USER_CREATE(body) {
  return {
    url: baseURL + '/v1/api/register',
    options: {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    },
  };
}
