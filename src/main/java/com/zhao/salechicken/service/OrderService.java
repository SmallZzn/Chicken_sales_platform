package com.zhao.salechicken.service;


import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Scanner;

/**
 * @author 86180
 * @description 针对表【order】的数据库操作Service
 * @createDate 2023-03-13 15:25:02
 */
public interface OrderService {
    /**
     * 根据id搜索用户的所有订单
     * @param page
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo selectAllOrder(int page,int pageSize, Integer userId);

    /**
     * 删除订单记录
     */
    void deleteOrder(List<Integer> ids);

    /**
     * 查询订单(根据订单状态/关键字)
     * @param userId
     * @param page
     * @param pageSize
     * @param orderId
     * @param status
     * @return
     */
    PageInfo selectOrder(Integer userId, int page, int pageSize, Integer orderId, String status);

    /**
     * 新增订单
     * @param order
     */
    void addOrder(Order order);

    /**
     * 修改订单信息
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 支付
     * @param loginUser
     * @param ids
     */
    void pay(Integer loginUser, List<Integer> ids,Integer addressId);
}