// export const baseURL = 'http://localhost:8080';
// TODO arrumar o CORS no back, por enquanto ta usando o proxy no ambiente dev
export const baseURL = '';

const requestOptions = ({url, method = 'GET', body, isAuthenticated = true}) => {
  const jwt = localStorage.getItem('apptoken');
  const request = {
    url: baseURL + url,
    options: {
      method: method,
      headers: {
        'Content-Type': 'application/json',
      },
      body: body ? JSON.stringify(body) : undefined
    }
  }
  if (isAuthenticated) {
    request.options.headers['Authorization'] = 'Bearer ' + JSON.parse(jwt);
  }
  return request;
}

export function USER_REGISTER(body) {
  return requestOptions({
    url: '/v1/api/register',
    method: 'POST',
    body,
    isAuthenticated: false
  })
}

export function USER_LOGIN(body) {
  return requestOptions({
    url: '/v1/api/authenticate',
    method: 'POST',
    body,
    isAuthenticated: false
  })
}

export function GET_AVATAR(type, id) {
  return requestOptions({
    url: `/v1/api/profile/${type}/${id}/avatar`,
    isAuthenticated: false
  })
}

export function GET_PROFILE(type, id) {
  return requestOptions({
    url: `/v1/api/profile/${type}/${id}`
  })
}

export function PATCH_PROFILE(type, id, body) {
  return requestOptions({
    url: `/v1/api/profile/${type}/${id}`,
    method: 'PATCH',
    body
  })
}