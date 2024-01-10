package com.zhao.salechicken.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.mapper.OrderMapper;
import com.zhao.salechicken.pojo.*;
import com.zhao.salechicken.service.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedissonClient redissonClient;

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
    public R<String> pay(Integer loginUser, List<Integer> ids, Integer addressId) {
        //查找用户的购物车
        Cart cart = cartService.selectCart(loginUser);

        //订单总价
        double totalprice = 0.0;
        //订单中商品总数
        int num = 0;

        //查找要结算的购物车商品
        List<Cartdetail> cartdetails = cartdetailService.selectPayCartdetail(cart.getCartId(), ids);

        //获取锁(可重入)，指定锁的名称
        RLock lock = redissonClient.getLock("payOrder");
        //尝试获取锁，参数分别是：获取锁的最大等待时间(期间会重试)，锁自动释放时间，时间单位
        boolean isLock;
        try {
            isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
            if (isLock) {
                //判断商品数量是否充足
                for (Cartdetail cartdetail : cartdetails) {
                    Integer productId = cartdetail.getProductId();
                    Product product = productService.getProductById(productId);
                    if (product.getInventory() <= 0) {
                        String productName = productService.getProductNameById(productId);
                        return R.error(productName + "数量不足!!!");
                    }
                }

                //添加订单并获取id,便于下面为订单详情设置orderId
                Order order = new Order();
                order.setOrderId(randomOrderCode());
//        orderMapper.addOrder(order);

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
        return R.success("下单成功");
    }

    /**
     * 生成随机订单号
     *
     * @return
     */
    public static Long randomOrderCode() {
        SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String dateran = dmDate.format(date);
        return Long.parseLong(dateran);
    }

    /**
     * 查询个个月份的销售额
     *
     * @return
     */
    public List<Double> selectTotalSales() {
        return orderMapper.selectTotalSales();
    }

    ;
}
