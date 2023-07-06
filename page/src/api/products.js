import request from "../utils/request";

// { page: 1, pageSize: 1, productName: null, origin: null,category: null }
function selectAllProduct(params) {
  return request({
    method: "get",
    url: "/product/selectAllProduct",
    // url: 'http://localhost:8080/data/test.json',
    params,
  });
}

// 按销量由高到低查询所有商品
function selectProductBySales(params) {
  return request({
    method: "get",
    url: "/product/selectProductBySales",
    params,
  });
}

// 按价格升序显示产品
function sortProductByPriceASC(params) {
  return request({
    method: "get",
    url: "/product/sortProductByPriceASC",
    params,
  });
}

// 按价格降序显示产品
function sortProductByPriceDESC(params) {
  return request({
    method: "get",
    url: "/product/sortProductByPriceDESC",
    params,
  });
}

// 查看缺货产品
function selectShoreSupplyProduct(params) {
  return request({
    method: "get",
    url: "/product/selectShoreSupplyProduct",
    params,
  });
}

// 查看商品详情
function getProductInfo(productId) {
  return request({
    method: "get",
    url: "/product/selectProductInfo",
    // url: '/data/test.json',
    params: {
      productId,
    },
  });
}

// {
//     "category": 0,
//     "description": "",
//     "image": "",
//     "inventory": 0,
//     "origin": "",
//     "price": 0,
//     "productId": 0,
//     "productName": "",
//     "sales": 0,
//     "weight": 0
//   }
function addProduct(data) {
  return request({
    method: "post",
    url: "/product/addProduct",
    data,
  });
}

// {
//     "category": 0,
//     "description": "",
//     "image": "",
//     "inventory": 0,
//     "origin": "",
//     "price": 0,
//     "productId": 0,
//     "productName": "",
//     "sales": 0,
//     "weight": 0
//   }
function updateProduct(data) {
  return request({
    method: "put",
    url: "/product/updateProduct",
    data,
  });
}

function deleteProduct(productId) {
  return request({
    method: "delete",
    url: "/product/deleteProduct",
    data: {
      productId,
    },
  });
}
export default {
  selectAllProduct,
  selectProductBySales,
  sortProductByPriceASC,
  sortProductByPriceDESC,
  selectShoreSupplyProduct,
  getProductInfo,
  addProduct,
  updateProduct,
  deleteProduct,
};
