import request from "@/utils/request";

// 查看某个用户的所有订单
function selectAllOrder(params) {
    return request({
        method: "get",
        url: "/order/selectAllOrder",
        params,
    });
}

// 查询指定订单(根据订单状态/id)
function selectOrder(params) {
    return request({
        method: "get",
        url: "/order/selectOrder",
        params,
    });
}

// 查看订单详情
function selectOrderDetail(params) {
    return request({
        method: "get",
        url: "/order/selectOrderDetail",
        params,
    });
}

// 支付
function pay(ids, addressId) {
    return request({
        method: "post",
        url: "/order/pay",
        data: {
                cartdetailIds: ids,
                addressId: addressId,

        },
    });
}

// 删除订单信息，可批量
function deleteOrder(ids) {
    return request({
        method: "delete",
        url: "/order/deleteOrder",
        data: {
            ids,
        },
    });
}

export default {
    selectAllOrder,
    selectOrder,
    selectOrderDetail,
    pay,
    deleteOrder,
};
