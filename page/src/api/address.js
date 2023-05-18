import request from "../utils/request";

function selectAllAddress() {
  return request({
    method: "get",
    url: "/address/selectAllAddress",
    // url: '/data/userdatalist.json'
  });
}

function addAddress(data) {
  return request({
    method: "post",
    url: "/address/addAddress",
    data,
  });
}

function updateAddress(data) {
  return request({
    method: "post",
    url: "/address/updateAddress",
    data,
  });
}

function defaultAddress(data) {
  return request({
    method: "post",
    url: "/address/defaultAddress",
    data,
  });
}

function deleteAddress(addressId) {
  return request({
    method: "delete",
    url: "/address/deleteAddress",
    data: {
      addressId,
    },
  });
}

export default {
  selectAllAddress,
  addAddress,
  updateAddress,
  defaultAddress,
  deleteAddress,
};
