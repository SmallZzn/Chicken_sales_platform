package com.zhao.salechicken;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.200.131:9200")
        ));
    }
}
