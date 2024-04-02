package com.zhao.salechicken;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.salechicken.pojo.Orderdetail;
import com.zhao.salechicken.service.impl.OrderdetailServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Author: 小赵
 * @DateTime: 2024/1/10 15:42
 */
public class test {

    private static final Random random = new Random();
    private static int sequenceNumber = 0;


    @Test
    public void testStrUtil() {
        boolean notBlank = StrUtil.isNotBlank("");
        System.out.println(notBlank);
    }

    @Test
    public void testObjectMapper() {
        Object o = "string";
        String s = new ObjectMapper().convertValue(o, String.class);
        System.out.println(s);
    }

    @Test
    public void testGenerateOrderId() {
        // 获取当前的年月日时分秒微秒
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        // 生成两位的随机码
        int randomCode1 = random.nextInt(90) + 10; // 生成一个10到99之间的随机数

        // 获取流水号
        sequenceNumber = (sequenceNumber + 1) % 1000; // 流水号在0到999之间循环

        // 生成三位的随机码
        int randomCode2 = random.nextInt(900) + 100; // 生成一个100到999之间的随机数

        // 将所有部分拼接成一个字符串
        System.out.println(timestamp + randomCode1 + sequenceNumber + randomCode2);
//        System.out.println(Long.parseLong(timestamp + randomCode1 + sequenceNumber + randomCode2));
    }

    @Test
    public void testGetOrderdetailById() {
        OrderdetailServiceImpl orderdetailServicel = new OrderdetailServiceImpl();
        Orderdetail orderdetail = orderdetailServicel.getById(8);
    }
}
