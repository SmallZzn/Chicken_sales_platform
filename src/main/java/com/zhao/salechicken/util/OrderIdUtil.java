package com.zhao.salechicken.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Author: 小赵
 * @DateTime: 2024/3/31 13:58
 */
public class OrderIdUtil {

    private static final Random random = new Random();
    private static int sequenceNumber = 0;

    /**
     * 生成随机订单号
     * 年月日时分秒微秒+随机码(2)+流水号+随机码(3)
     *
     * @return
     */
    public static String randomOrderCode() {
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
        return timestamp + randomCode1 + sequenceNumber + randomCode2;
    }
}
