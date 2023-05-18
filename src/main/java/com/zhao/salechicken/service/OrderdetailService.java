package com.zhao.salechicken.service;


import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.pojo.Orderdetail;
import org.apache.ibatis.annotations.Param;

/**
* @author 86180
* @description 针对表【orderdetail】的数据库操作Service
* @createDate 2023-03-15 22:10:57
*/
public interface OrderdetailService{
    /**
     * 添加订单详情
     * @param orderdetail
     */
    void addOrdertail(Orderdetail orderdetail);

    /**
     * 查看订单详情(分页)
     * @param page
     * @param pageSize
     * @param orderId
     * @return
     */
    PageInfo selectOrderDetail(int page, int pageSize, Integer orderId);
}
