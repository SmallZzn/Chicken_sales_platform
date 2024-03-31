//package com.zhao.salechicken.config;
//
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import static com.zhao.salechicken.util.MqConstants.*;
//
///**
// * @Author: 小赵
// * @DateTime: 2023/11/23 11:09
// */
//@Configuration
//public class MqConfig {
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(CHICKEN_PRODUCT_EXCHANGE, true, false);
//    }
//
//    @Bean
//    public Queue insertQueue() {
//        return new Queue(CHICKEN_PRODUCT_INSERT_QUEUE, true);
//    }
//
//    @Bean
//    public Queue deleteQueue() {
//        return new Queue(CHICKEN_PRODUCT_DELETE_QUEUE, true);
//    }
//
//    @Bean
//    public Binding insertQueueBinding() {
//        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(CHICKEN_PRODUCT_INSERT_KEY);
//    }
//
//    @Bean
//    public Binding deleteQueueBinding() {
//        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(CHICKEN_PRODUCT_DELETE_KEY);
//    }
//}
