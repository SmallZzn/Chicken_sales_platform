package com.zhao.salechicken;

import cn.hutool.core.math.Calculator;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.salechicken.pojo.Log;
import com.zhao.salechicken.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 小赵
 * @DateTime: 2024/1/10 15:42
 */
@SpringBootTest(classes = {ChickenSalesPlatformApplication.class})
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

    @Autowired
    private LogService logService;

    public String requestParam="{\"password\":\"\",\"code\":\"674542\",\"phone\":\"18025579543\"}";
    public String responseResult="{\"code\":200,\"data\":{\"email\":\"zhao@example.com\",\"image\":\"cf663c84-ba85-4651-b1f8-6f7eb092025f.jpg\",\"password\":\"111111\",\"phone\":\"18025579543\",\"sex\":1,\"type\":1,\"userId\":1,\"userName\":\"admin\"},\"map\":{}}";

    @Test
    public void testInsertIntoLog() {
        Log log = new Log();
        log.setTitle("模块");
        log.setContent("测试");
        log.setMethod("test"); //设置请求方法
        log.setRequestMethod("POST");//设置请求方式
        log.setRequestParam(requestParam); // 请求参数
        log.setResponseResult(responseResult); // 返回结果
        log.setOperId(1); // 获取用户id
        log.setIp("0:0:0:0:0:0:0:1"); // IP地址
        log.setIpLocation("未知"); // IP归属地
        log.setRequestUrl("/loginByCode"); // 请求URI
        log.setOperTime(new Date()); // 时间
        log.setStatus(0);//操作状态（0正常 1异常）
        Long takeTime = 874l;//记录方法执行耗时时间（单位：毫秒）
        log.setTakeTime(takeTime);

        LocalDateTime startTime = LocalDateTime.now(); // 记录开始时间
        for (int i = 0; i < 100000; i++) {
            logService.save(log);
        }
        LocalDateTime endTime = LocalDateTime.now(); //记录结束时间
        long durationInSeconds = java.time.Duration.between(startTime, endTime).getSeconds();

        //171s  191s  215s
        System.out.println("持续时间（秒）：" + durationInSeconds);
    }

    //线程池参数
    private static final int CORE_POOL_SIZE = 16;//核心线程数，IO密集型设置为CPU核数*2
    private static final int MAX_POOL_SIZE = 32;//最大线程数
    private static final int QUEUE_CAPACITY = 100;//阻塞队列的长度
    private static final Long KEEP_ALIVE_TIME = 30L;//线程的空闲时间，超过空闲时间则回收

    //线程数
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),//阻塞队列
            new ThreadPoolExecutor.CallerRunsPolicy()//拒绝策略
    );

    @Test
    public void testInsertIntoLogByThreadPool() {
        Log log = new Log();
        log.setTitle("模块");
        log.setContent("测试");
        log.setMethod("test"); //设置请求方法
        log.setRequestMethod("POST");//设置请求方式
        log.setRequestParam(requestParam); // 请求参数
        log.setResponseResult(responseResult); // 返回结果
        log.setOperId(1); // 获取用户id
        log.setIp("0:0:0:0:0:0:0:1"); // IP地址
        log.setIpLocation("未知"); // IP归属地
        log.setRequestUrl("/loginByCode"); // 请求URI
        log.setOperTime(new Date()); // 时间
        log.setStatus(0);//操作状态（0正常 1异常）
        Long takeTime = 874l;//记录方法执行耗时时间（单位：毫秒）
        log.setTakeTime(takeTime);

        LocalDateTime startTime = LocalDateTime.now(); // 记录开始时间
        for (int i = 0; i < 100000; i++) {
            executor.execute(()->logService.save(log));
        }
        LocalDateTime endTime = LocalDateTime.now(); //记录结束时间
        long durationInSeconds = java.time.Duration.between(startTime, endTime).getSeconds();

        //13s  13s  14s
        System.out.println("持续时间（秒）：" + durationInSeconds);
    }

    public int calculate(String s) {
        // 去除空格
        s = s.replaceAll(" ", "");

        Stack<Integer> stack = new Stack<>();
        int num = 0; // 当前数字
        char sign = '+'; // 当前操作符，默认+，表示加法
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            // 处理当前数字
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0'); // 构建多位数
            }

            // 如果是运算符或者是最后一个字符，执行操作
            if ((!Character.isDigit(c) && c != ' ') || i == n - 1) {
                if (sign == '+') {
                    stack.push(num); // 正号就入栈
                } else if (sign == '-') {
                    stack.push(-num); // 负号就入栈
                } else if (sign == '*') {
                    stack.push(stack.pop() * num); // 乘法操作
                } else if (sign == '/') {
                    stack.push(stack.pop() / num); // 除法操作
                }

                // 更新操作符
                sign = c;
                num = 0; // 处理完一个数字，重置num
            }
        }

        // 计算栈中的结果
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop(); // 加法运算
        }

        return result;
    }

    @Test
    public void tt(){
        Calculator calculator = new Calculator();
        String expression = "3+5*2-8/4"; // 测试表达式
        System.out.println(calculator.calculate(expression)); // 输出结果
    }
}
