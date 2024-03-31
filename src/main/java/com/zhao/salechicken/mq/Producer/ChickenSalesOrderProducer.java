package com.zhao.salechicken.mq.Producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

import static com.zhao.salechicken.util.MqConstants.PAY_ORDER_TOPIC;

/**
 * 支付订单消息发送者
 */
@Slf4j
@Component
public class ChickenSalesOrderProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value(PAY_ORDER_TOPIC)
    private String statsSaveTopic;

    public void send(Map<String, Object> producerMap) {
        String keys = UUID.randomUUID().toString();
        Message<Map<String, Object>> build = MessageBuilder
                .withPayload(producerMap)
                .setHeader(MessageConst.PROPERTY_KEYS, keys)
                .build();
        SendResult sendResult;
        try {
            sendResult = rocketMQTemplate.syncSend(statsSaveTopic, build, 2000L);
            log.info("[消息访问统计监控] 消息发送结果：{}，消息ID：{}，消息Keys：{}", sendResult.getSendStatus(), sendResult.getMsgId(), keys);
        } catch (Throwable ex) {
            log.error("[消息访问统计监控] 消息发送失败，消息体：{}", JSON.toJSONString(producerMap), ex);
            // 自定义行为...
        }
    }
}
