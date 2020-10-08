export const baseURL = 'http://localhost:8080/';

export function USER_REGISTER(body) {
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

export function USER_LOGIN(body) {
  return {
    url: baseURL + '/v1/api/login',
    options: {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    },
  };
}
