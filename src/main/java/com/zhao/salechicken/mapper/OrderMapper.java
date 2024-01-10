package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Order;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author 86180
* @description 针对表【order】的数据库操作Mapper
* @createDate 2023-03-13 15:25:02
* @Entity com.zhao.salechicken.pojo.Order
*/

@Mapper
public interface OrderMapper {
    /**
     * 查询所有订单
     * @param userId
     * @return
     */
    List<Order> selectAllOrder(@Param("userId") Integer userId);

    /**
     * 删除订单信息（可批量）
     * @param ids
     */
    void deleteOrder(@Param("ids") List<Long> ids);

    /**
     * 查询订单(根据订单状态/关键字)
     * @param userId
     * @param status
     * @return
     */
    List<Order> selectOrder(@Param("userId") Integer userId, @Param("orderId") Long orderId, @Param("status") String status);

    /**
     * 新增订单
     * @param order
     */
    void addOrder(@Param("order") Order order);

    /**
     * 修改订单信息
     * @param order
     */
    void updateOrder(@Param("order") Order order);

    /**
     * 查询个个月份的销售额
     * @return
     */
    List<Double> selectTotalSales();
}




