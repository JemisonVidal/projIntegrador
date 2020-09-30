export const baseURL = 'http://localhost:8080';

const requestOptions = ({url, method = 'GET', body, jwt}) => {
  return {
    url: baseURL + url,
    options: {
      method: method,
      headers: {
        'Content-Type': 'application/json',
        Authorization: jwt ? 'Bearer ' + jwt : undefined,
      },
      body: body ? JSON.stringify(body) : undefined
    }
  }
}

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

export function GET_PROFILE(type, id, jwt) {
  const url = `/v1/api/profile/${type}/${id}`
  return requestOptions({
    url,
    jwt
  })
}