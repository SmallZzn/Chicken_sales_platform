package com.zhao.salechicken;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.zhao.salechicken.CeateIndexStatement.CREATE_PRODUCT_INDEX;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/25 13:54
 * 创建es索引库
 */
@SpringBootTest
public class CreateEsIndexDatabases {

    private RestHighLevelClient client;

    /**
     * 创建产品索引库
     * @throws IOException
     */
    @Test
    void createProductIndex() throws IOException {
        // 1.准备Request
        CreateIndexRequest request = new CreateIndexRequest("chicken_product");
        // 2.准备请求参数
        request.source(CREATE_PRODUCT_INDEX, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.200.131:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}
