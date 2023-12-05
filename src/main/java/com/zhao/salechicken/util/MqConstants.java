package com.zhao.salechicken.util;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/23 10:55
 */
public class MqConstants {
    /**
     * 交换机
     */
    public static final String CHICKEN_PRODUCT_EXCHANGE = "chicken.product.topic";

    /**
     * 监听新增和修改队列
     */
    public static final String CHICKEN_PRODUCT_INSERT_QUEUE = "chicken.product.insert.queue";

    /**
     * 监听删除队列
     */
    public static final String CHICKEN_PRODUCT_DELETE_QUEUE = "chicken.product.delete.queue";

    /**
     * 新增或修改RoutingKey
     */
    public static final String CHICKEN_PRODUCT_INSERT_KEY = "chicken.product.insert";

    /**
     * 删除RoutingKey
     */
    public static final String CHICKEN_PRODUCT_DELETE_KEY = "chicken.product.delete";
}
