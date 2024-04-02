package com.zhao.salechicken.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.pojo.Orderdetail;

/**
 * @author 86180
 * @description 针对表【orderdetail】的数据库操作Service
 * @createDate 2023-03-15 22:10:57
 */
public interface OrderdetailService extends IService<Orderdetail> {
    /**
     * 添加订单详情
     *
     * @param orderdetail
     */
    void addOrdertail(Orderdetail orderdetail);

    /**
     * 查看订单详情(分页)
     *
     * @param page
     * @param pageSize
     * @param orderId
     * @return
     */
    PageInfo selectOrderDetail(int page, int pageSize, Long orderId);
}
