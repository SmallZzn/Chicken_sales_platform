package com.zhao.salechicken.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.salechicken.pojo.Orderdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【orderdetail】的数据库操作Mapper
 * @createDate 2023-03-15 22:10:57
 * @Entity com.zhao.salechicken.pojo.Orderdetail
 */
@Mapper
public interface OrderdetailMapper extends BaseMapper<Orderdetail> {

    /**
     * 添加订单详情
     * @param orderdetail
     */
    void addOrdertail(@Param("orderdetail")Orderdetail orderdetail);

    /**
     * 根据订单id查询所有订单详情
     * @param orderId
     * @return
     */
    List<Orderdetail> selectOrderDetailByOrderId(@Param("orderId") Long orderId);
}




