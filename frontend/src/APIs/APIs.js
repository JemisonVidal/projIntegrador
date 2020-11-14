export const baseURL = 'http://localhost:8080';
// TODO arrumar o CORS no back, por enquanto ta usando o proxy no ambiente dev
//export const baseURL = '';

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

export function USER_REGISTER(body) {
  return requestOptions({
    url: '/v1/api/register',
    method: 'POST',
    body
  })
}

export function USER_LOGIN(body) {
  return requestOptions({
    url: '/v1/api/authenticate',
    method: 'POST',
    body
  })
}

export function GET_PROFILE(type, id, jwt) {
  const url = `/v1/api/profile/${type}/${id}`
  return requestOptions({
    url,
    jwt
  })
}

export function GET_SEARCH(search) {
  return requestOptions({
    url: `/v1/api/search/${search}`
  })
}