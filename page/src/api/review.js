import request from "@/utils/request";

// 查看某个商品的所有评论
function selectProductReview(params) {
  return request({
    method: "get",
    url: "/review/selectReview",
    params,
  });
}

//查看用户评价
function selectMyReview(params) {
  return request({
    method: "get",
    url: "/review/selectMyReview",
    params,
  });
}

// 发表评论
// {
//     "comment": "",
//     "createTime": "",
//     "productId": 0,
//     "rating": 0,
//     "reviewId": 0,
//     "userId": 0
//   }
function addReview(data) {
  return request({
    method: "post",
    url: "/review/addReview",
    data,
  });
}

// 修改评论
// {
//     "reviewId": "2", //不可修改
//     "userId": "10",  //不可修改
//     "productId": "8",//不可修改
//     "rating": 10,
//     "comment": "This is the second commnet",
//     "image": "34534645645",
//     "anonymous": 1,
//     "publishTime": "2023-04-08 14:40:50", //不可修改
// }
function update(data) {
  return request({
    method: "put",
    url: "/review/edit",
    data,
  });
}

// 删除评论
function deleteReview(reviewId) {
  return request({
    method: "delete",
    url: `/review/deleteReview`,
    data: {
      reviewId,
    },
  });
}
export default {
  selectProductReview,
  selectMyReview,
  addReview,
  update,
  deleteReview,
};
