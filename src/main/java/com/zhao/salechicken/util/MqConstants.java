package com.zhao.salechicken.util;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/23 10:55
 */
public class MqConstants {

    /**
     * 将增删改数据同步到ES的消息队列的group
     */
    public static final String ES_UPDATE_GROUP = "chicken-sales-es-service_group";


    /**
     * 下单消息队列的group
     */
    public static final String PAY_ORDER_GROUP = "chicken-sales-order-commit-service_group";

    /**
     * 将增删改数据同步到ES的消息队列的topic
     */
    public static final String ES_UPDATE_TOPIC = "chicken-sales-es-service_topic";


    /**
     * 下单消息队列的topic
     */
    public static final String PAY_ORDER_TOPIC = "chicken-sales-order-commit-service_topic";
}
