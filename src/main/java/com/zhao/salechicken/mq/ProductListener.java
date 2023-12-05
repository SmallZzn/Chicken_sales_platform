package com.zhao.salechicken.mq;

import com.zhao.salechicken.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.zhao.salechicken.util.MqConstants.CHICKEN_PRODUCT_DELETE_QUEUE;
import static com.zhao.salechicken.util.MqConstants.CHICKEN_PRODUCT_INSERT_QUEUE;

/**
 * 将数据库的修改同步到es中
 */
@Component
public class ProductListener {

    @Autowired
    private ProductService productService;

    /**
     * 监听产品新增或者修改业务
     *
     * @param id
     */
    @RabbitListener(queues = CHICKEN_PRODUCT_INSERT_QUEUE)
    public void listenProductInsertOrUpdate(Integer id) {
        productService.insertById(id);
    }

    /**
     * 监听产品删除业务
     *
     * @param id
     */
    @RabbitListener(queues = CHICKEN_PRODUCT_DELETE_QUEUE)
    public void listenHotelDelete(Integer id) {
        productService.deleteById(id);
    }
}
