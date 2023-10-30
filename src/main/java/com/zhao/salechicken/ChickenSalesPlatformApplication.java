package com.zhao.salechicken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan//扫描组件
@EnableTransactionManagement//开启事务注解
public class ChickenSalesPlatformApplication {

    public static void main(String[] args) {
        log.info("项目开始运行");
        SpringApplication.run(ChickenSalesPlatformApplication.class, args);
    }

}
