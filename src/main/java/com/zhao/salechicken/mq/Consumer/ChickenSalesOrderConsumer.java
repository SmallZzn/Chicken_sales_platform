package com.zhao.salechicken.mq.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.salechicken.dto.PayDto;
import com.zhao.salechicken.mapper.OrderMapper;
import com.zhao.salechicken.pojo.*;
import com.zhao.salechicken.service.CartService;
import com.zhao.salechicken.service.CartdetailService;
import com.zhao.salechicken.service.OrderdetailService;
import com.zhao.salechicken.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.zhao.salechicken.util.MqConstants.PAY_ORDER_GROUP;
import static com.zhao.salechicken.util.MqConstants.PAY_ORDER_TOPIC;
import static com.zhao.salechicken.util.OrderIdUtil.randomOrderCode;
import static com.zhao.salechicken.util.RedisConstants.LOCK_ORDER_KEY;

/**
 * 支付订单消息消费者
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = PAY_ORDER_TOPIC,
        consumerGroup = PAY_ORDER_GROUP
)
public class ChickenSalesOrderConsumer implements RocketMQListener<Map<String, Object>> {

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
    @Transactional
    public void onMessage(Map<String, Object> producerMap) {
        //取出订单信息
        Object objectPayDto = producerMap.get("payDto");
        PayDto payDto = new ObjectMapper().convertValue(objectPayDto, PayDto.class);

        //取出订单信息
        Object objectLoginUser = producerMap.get("loginUser");
        Integer loginUser = new ObjectMapper().convertValue(objectLoginUser, Integer.class);

        //查找用户的购物车
        Cart cart = cartService.selectCart(loginUser);

        //订单总价
        double totalprice = 0.0;
        //订单中商品总数
        int num = 0;

        //查找要结算的购物车商品
        List<Integer> ids = payDto.getCartdetailIds();
        List<Cartdetail> cartdetails = cartdetailService.selectPayCartdetail(cart.getCartId(), ids);

        //使用读写锁
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(LOCK_ORDER_KEY + loginUser);
        RLock rLock = readWriteLock.readLock();
        rLock.lock();
        try {
//            //判断商品数量是否充足
//            for (Cartdetail cartdetail : cartdetails) {
//                Integer productId = cartdetail.getProductId();
//                Product product = productService.getProductById(productId);
//                if (product.getInventory() <= 0) {
//                    String productName = productService.getProductNameById(productId);
//                    throw new CustomException(productName + "数量不足!!!");
//                }
//            }

            //添加订单并获取id,便于下面为订单详情设置orderId
            Order order = new Order();
            order.setOrderId(randomOrderCode());

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
//                product.setInventory(product.getInventory() - cartdetail.getQuantity());
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
            Integer addressId = payDto.getAddressId();
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
        } catch (Exception e) {
            log.info("订单提交异常");
        } finally {
            rLock.unlock();
        }

        //取出keys
        Object objectKeys = producerMap.get("keys");
        String keys = new ObjectMapper().convertValue(objectKeys, String.class);
        log.info("消息{}消费成功", keys);
    }
}
