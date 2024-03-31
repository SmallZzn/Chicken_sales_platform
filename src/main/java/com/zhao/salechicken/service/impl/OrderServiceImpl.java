package com.zhao.salechicken.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.PayDto;
import com.zhao.salechicken.mapper.OrderMapper;
import com.zhao.salechicken.mq.Producer.ChickenSalesOrderProducer;
import com.zhao.salechicken.pojo.Order;
import com.zhao.salechicken.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 86180
 * @description 针对表【order】的数据库操作Service实现
 * @createDate 2023-03-13 15:25:02
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Resource
    private ChickenSalesOrderProducer chickenSalesOrderProducer;

    @Override
    public PageInfo selectAllOrder(int page, int pageSize, Integer userId) {
        //开启分页功能
        PageHelper.startPage(page, pageSize);

        List<Order> orders = orderMapper.selectAllOrder(userId);

        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);

        return orderPageInfo;
    }

    @Override
    public void deleteOrder(List<Long> ids) {
        orderMapper.deleteOrder(ids);
    }

    @Override
    public PageInfo selectOrder(Integer userId, int page, int pageSize, Long orderId, String status) {

        //开启分页功能
        PageHelper.startPage(page, pageSize);

        //查询订单信息
        List<Order> orders = orderMapper.selectOrder(userId, orderId, status);

        PageInfo<Order> pageInfo = new PageInfo<>(orders);

        return pageInfo;
    }

    @Override
    public void addOrder(Order order) {
        orderMapper.addOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    @Override
    @Transactional
    public R<String> pay(PayDto payDto) {
        //判断是否选中了商品
        if (payDto.getCartdetailIds() == null) {
            return R.error("您当前为选中任何商品");
        }

        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        //将订单发送到RocketMQ
        Map<String, Object> producerMap = new HashMap<>();
        producerMap.put("payDto", payDto);
        producerMap.put("loginUser",loginUser);
        chickenSalesOrderProducer.send(producerMap);
        return R.success("下单成功");
    }

    /**
     * 查询个个月份的销售额
     *
     * @return
     */
    public List<Double> selectTotalSales() {
        return orderMapper.selectTotalSales();
    }
}
