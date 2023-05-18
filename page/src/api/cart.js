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

// allprice: 0,
// cartId: 0,
// cartdetailId: 0,
// productId: 0,
// quantity: 0
function add(data) {
  return request({
    method: "post",
    url: "/cart/updateCartdetail",
    data,
  });
}

function reduceNum(data) {
  return request({
    method: "post",
    url: "/cart/updateCartdetail",
    data,
  });
}

function removeAll() {
  return request({
    method: "delete",
    url: "/cart/emptyCart",
  });
}

export default { get, add, reduceNum, removeAll };
