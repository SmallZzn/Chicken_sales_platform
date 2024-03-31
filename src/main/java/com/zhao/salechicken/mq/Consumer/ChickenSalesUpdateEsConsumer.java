package com.zhao.salechicken.mq.Consumer;

import com.zhao.salechicken.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.zhao.salechicken.util.MqConstants.ES_UPDATE_GROUP;
import static com.zhao.salechicken.util.MqConstants.ES_UPDATE_TOPIC;

/**
 * 同步es消息消费者
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = ES_UPDATE_TOPIC,
        consumerGroup = ES_UPDATE_GROUP
)
public class ChickenSalesUpdateEsConsumer implements RocketMQListener<Map<String, String>> {

    @Autowired
    private ProductService productService;

    @Override
    public void onMessage(Map<String, String> producerMap) {
        String keys = producerMap.get("keys");
        int productId = Integer.parseInt(producerMap.get("productId"));
        String operation = producerMap.get("operation");
        if("INSERT".equals(operation)){
            productService.insertById(productId);
        }else if("DELETE".equals(operation)){
            productService.deleteById(productId);
        }
        log.info("消息{}消费成功",keys);
    }
}
