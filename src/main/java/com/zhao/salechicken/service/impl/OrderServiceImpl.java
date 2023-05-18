package com.zhao.salechicken.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.mapper.OrderMapper;
import com.zhao.salechicken.pojo.*;
import com.zhao.salechicken.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 86180
 * @description 针对表【order】的数据库操作Service实现
 * @createDate 2023-03-13 15:25:02
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartdetailService cartdetailService;

    @Autowired
    private OrderdetailService orderdetailService;

    @Autowired
    private ProductService productService;

    @Override
    public PageInfo selectAllOrder(int page, int pageSize, Integer userId) {
        //开启分页功能
        PageHelper.startPage(page, pageSize);

        List<Order> orders = orderMapper.selectAllOrder(userId);

        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);

        return orderPageInfo;
    }

    @Override
    public void deleteOrder(List<Integer> ids) {
        orderMapper.deleteOrder(ids);
    }

    @Override
    public PageInfo selectOrder(Integer userId, int page, int pageSize, Integer orderId, String status) {

        //开启分页功能
        PageHelper.startPage(page, pageSize);

        //查询产品信息
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
    public void pay(Integer loginUser, List<Integer> ids, Integer addressId) {
        //查找用户的购物车
        Cart cart = cartService.selectCart(loginUser);

        //订单总价
        double totalprice = 0.0;
        //订单中商品总数
        int num = 0;

        //查找要结算的购物车商品
        List<Cartdetail> cartdetails = cartdetailService.selectPayCartdetail(cart.getCartId(), ids);

        //添加订单并获取id,便于下面为订单详情设置orderId
        Order order = new Order();
        orderMapper.addOrder(order);

        //向订单详情表添加数据 同时 删除购物车详情
        for (Cartdetail cartdetail : cartdetails) {
            //要添加的订单详情
            Orderdetail orderdetail = new Orderdetail();

            //为要添加的订单详情赋值
            orderdetail.setQuantity(cartdetail.getQuantity());
            orderdetail.setTotalMoney(cartdetail.getAllprice());
            orderdetail.setOrderId(order.getOrderId());
            orderdetail.setProductId(cartdetail.getProductId());

            //产品销量增加，库存减少
            Product product = productService.getProductById(orderdetail.getProductId());
            product.setSales(product.getSales() + cartdetail.getQuantity());
            product.setInventory(product.getInventory() - cartdetail.getQuantity());
            productService.updateProduct(product);

            //添加订单详情
            orderdetailService.addOrdertail(orderdetail);

            //删除购物车详情
            cartdetailService.deleteProductFromCart(cartdetail.getProductId(), cart.getCartId());

            //计算订单总价和订单商品数量
            totalprice += cartdetail.getAllprice();
            num += cartdetail.getQuantity();
        }

        //向订单表添加数据
        order.setUserId(loginUser);
        order.setOrderdate(new Date());
        order.setNum(num);
        order.setTotalprice(totalprice);
        order.setStatus("已付款");
        order.setAddressId(addressId);
        //添加订单信息
        orderMapper.addOrder(order);

        //修改购物车信息
        Cart newCart = new Cart();
        newCart.setCartId(cart.getCartId());
        newCart.setUserId(cart.getUserId());
        newCart.setNum(cart.getNum() - num);
        newCart.setAllprice(cart.getAllprice() - totalprice);
        cartService.updateCart(newCart);
    }
}
