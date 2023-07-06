import request from "../utils/request";

function get(page = 1, pageSize = 5) {
  return request({
    method: "get",
    url: "/cart/selectMyCart",
    // url: '/data/cart.json',
    params: {
      page,
      pageSize,
    },
  });
}

function add(productId) {
  return request({
    method: "post",
    url: "/cart/addCart",
    params: {
      productId,
    },
  });
}

// allprice: 0,
// cartId: 0,
// cartdetailId: 0,
// productId: 0,
// quantity: 0
function updateCartdetail(data) {
  return request({
    method: "put",
    url: "/cart/updateCartdetail",
    data,
  });
}

function reduceNum(data) {
  return request({
    method: "put",
    url: "/cart/reduceCartProductNum",
    data,
  });
}

function removeAll() {
  return request({
    method: "delete",
    url: "/cart/emptyCart",
  });
}

export default { get, add, updateCartdetail, reduceNum, removeAll };
