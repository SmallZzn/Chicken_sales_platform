package com.zhao.salechicken;

import com.alibaba.fastjson.JSON;
import com.zhao.salechicken.Doc.ProductDoc;
import com.zhao.salechicken.mapper.ProductMapper;
import com.zhao.salechicken.pojo.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/25 14:35
 */
@SpringBootTest
public class InsertDocument {

    private RestHighLevelClient client;

    @Autowired
    private ProductMapper productMapper;

    @Test
    void testBulk() throws IOException {
        List<Product> products = productMapper.selectAllProduct(null, null, null);
        BulkRequest request = new BulkRequest();
        for (Product product : products) {
            ProductDoc productDoc = new ProductDoc(product);
            request.add(new IndexRequest("chicken_product")
                    .id(productDoc.getProductId().toString())
                    .source(JSON.toJSONString(productDoc), XContentType.JSON));
        }
        client.bulk(request, RequestOptions.DEFAULT);
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
