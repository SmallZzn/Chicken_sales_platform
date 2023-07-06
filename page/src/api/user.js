import request from "../utils/request";

function login(userName, password) {
  return request({
    method: "post",
    url: "/login",
    data: {
      userName,
      password,
    },
  });
}

function register(data) {
  return request({
    method: "post",
    url: "/user/logon",
    data,
  });
}

function logout() {
  return request({
    method: "post",
    url: "/logout",
  });
}

function remove(userId) {
  return request({
    method: "delete",
    url: "/user/remove",
    data: {
      userId,
    },
  });
}

export default { login, register, logout, remove };
