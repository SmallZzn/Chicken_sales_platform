package com.zhao.salechicken.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.Doc.ProductDoc;
import com.zhao.salechicken.dto.ProductDto;
import com.zhao.salechicken.mapper.CategoryMapper;
import com.zhao.salechicken.mapper.ProductMapper;
import com.zhao.salechicken.mq.Producer.ChickenSalesUpdateEsProducer;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.service.ProductService;
import com.zhao.salechicken.util.CacheClient;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zhao.salechicken.util.RedisConstants.CACHE_SHOPINFO_KEY;
import static com.zhao.salechicken.util.RedisConstants.CACHE_SHOPINFO_TTL;

/**
 * @author 86180
 * @description 针对表【product】的数据库操作Service实现
 * @createDate 2023-03-13 15:26:44
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CacheClient cacheClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RestHighLevelClient client;

    @Autowired
    private ChickenSalesUpdateEsProducer chickenSalesUpdateEsProducer;

    @Override
    public void addProduct(Product product) {
        productMapper.addProduct(product);
        //获取新添加的产品的id
        Product newProduct = productMapper.getProductByName(product.getProductName());
        //将添加任务添加到RocketMQ中
        Map<String, String> producerMap = new HashMap<>();
        producerMap.put("productId", newProduct.getProductId() + "");
        producerMap.put("operation", "INSERT");
        chickenSalesUpdateEsProducer.send(producerMap);

        //添加缓存
        stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY, JSONUtil.toJsonStr(newProduct), 30, TimeUnit.MINUTES);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productMapper.deleteProduct(productId);
        //删除缓存
        stringRedisTemplate.delete(CACHE_SHOPINFO_KEY + productId);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
        //删除缓存
        stringRedisTemplate.delete(CACHE_SHOPINFO_KEY + product.getProductId());
    }

    @Override
    public PageInfo selectProduct(int page, int pageSize, String productName, Integer category, String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectProduct(productName, category, origin);

        //添加缓存
        for (Product product : productList) {
            stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY, JSONUtil.toJsonStr(product), 30, TimeUnit.MINUTES);
        }

        PageInfo<Product> pageInfo = new PageInfo<>(productList);

        return pageInfo;
    }

    @Override
    public PageInfo selectAllProduct(int page, int pageSize, String productName, Integer category, String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectAllProduct(productName, category, origin);

        //3、设置分页插件
        PageInfo<Product> productPageInfo = new PageInfo<>(productList);

        //4、设置要返回的分页插件
        PageInfo<ProductDto> productDtoPageInfo = new PageInfo<>();

        //5、赋值信息到要返回的分页插件
        BeanUtils.copyProperties(productPageInfo, productDtoPageInfo);

        //6、创建集合productDtoList,并为其属性赋值
        List<ProductDto> productDtoList = productList.stream().map((item) -> {
            ProductDto productDto = new ProductDto();

            //复制item到productDto
            BeanUtils.copyProperties(item, productDto);

            //为productDto的categoryName赋值
            String categoryName = categoryMapper.selectNameByType(productDto.getCategory());
            productDto.setCategoryName(categoryName);

            return productDto;

        }).collect(Collectors.toList());

        //7、为productDtoPageInfo赋值
        productDtoPageInfo.setList(productDtoList);

        //添加缓存
        for (Product product : productList) {
            stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY + product.getProductId(), JSONUtil.toJsonStr(product), 30, TimeUnit.MINUTES);
        }

        return productDtoPageInfo;
    }

    @Override
    public PageInfo selectProductBySales(int page, int pageSize, String productName, Integer category, String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectProductBySales(productName, category, origin);

        PageInfo<Product> pageInfo = new PageInfo<>(productList);

        //添加缓存
        for (Product product : productList) {
            stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY + product.getProductId(), JSONUtil.toJsonStr(product), 30, TimeUnit.MINUTES);
        }

        return pageInfo;
    }

    @Override
    public Product getProductByName(String productName) {
        return productMapper.getProductByName(productName);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productMapper.getProductByCategory(category);
    }

    @Override
    public Product getProductById(Integer productId) {
        // 查询缓存，缓存穿透
        Product product = cacheClient.queryWithPassThrough(CACHE_SHOPINFO_KEY, productId, Product.class, id -> productMapper.getProductById(id), CACHE_SHOPINFO_TTL, TimeUnit.MINUTES);
        return product;
    }

    @Override
    public String getProductNameById(Integer productId) {
        // 查询缓存，缓存穿透
        Product product = cacheClient.queryWithPassThrough(CACHE_SHOPINFO_KEY, productId, Product.class, id -> productMapper.getProductById(id), CACHE_SHOPINFO_TTL, TimeUnit.MINUTES);
        if (product != null) {
            return product.getProductName();
        } else {
            return null;
        }
//        return productMapper.getProductNameById(productId);
    }

    @Override
    public PageInfo sortProductByPriceDESC(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page, pageSize);

        //根据要求查询产品
        List<Product> productList = productMapper.sortProductByPriceDESC(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(productList);

        //添加缓存
        for (Product product : productList) {
            stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY + product.getProductId(), JSONUtil.toJsonStr(product), 30, TimeUnit.MINUTES);
        }

        return productPageInfo;
    }

    @Override
    public PageInfo sortProductByPriceASC(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page, pageSize);

        //根据要求查询产品
        List<Product> productList = productMapper.sortProductByPriceASC(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(productList);

        //添加缓存
        for (Product product : productList) {
            stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY + product.getProductId(), JSONUtil.toJsonStr(product), 30, TimeUnit.MINUTES);
        }

        return productPageInfo;
    }

    @Override
    public PageInfo selectShortSupplyProduct(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page, pageSize);

        //根据要求查询产品
        List<Product> productList = productMapper.selectShortSupplyProduct(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(productList);

        //添加缓存
        for (Product product : productList) {
            stringRedisTemplate.opsForValue().set(CACHE_SHOPINFO_KEY + product.getProductId(), JSONUtil.toJsonStr(product), 30, TimeUnit.MINUTES);
        }

        return productPageInfo;
    }

    @Override
    public List<String> getSuggestion(String prefix) {
        try {
            //1、准备request
            SearchRequest request = new SearchRequest("chicken_product");

            //2、准备DSL
            request.source().suggest(new SuggestBuilder().addSuggestion(
                    "suggestions",
                    SuggestBuilders.completionSuggestion("suggestion")
                            .prefix(prefix)
                            .skipDuplicates(true)
                            .size(10)
            ));

            //3、发起请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            //4、解析结果
            Suggest suggest = response.getSuggest();
            //4.1 根据补全查询名称，获取补全结果
            CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
            //4.2 获取options
            List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
            //4.3 遍历
            List<String> list = new ArrayList<>();
            for (CompletionSuggestion.Entry.Option option : options) {
                String text = option.getText().toString();
                list.add(text);
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertById(Integer id) {
        try {
            // 1.根据id查询产品数据
            Product product = productMapper.getProductById(id);
            // 2.转换为文档类型
            ProductDoc productDoc = new ProductDoc(product);
            // 3.将ProductDoc转json
            String json = JSON.toJSONString(productDoc);

            IndexRequest request = new IndexRequest("chicken_product").id(productDoc.getProductId().toString());
            request.source(json, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        DeleteRequest request = new DeleteRequest("chicken_product", id.toString());
        try {
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public Map<String, List<String>> filters(RequestParams params) {
//        try {
//            //1、准备request
//            SearchRequest request = new SearchRequest("chicken_product");
//
//            //2、准备DSL
//            //2.1 query
//            buildBasicQuery(params, request);
//            //2.2 设置size
//            request.source().size(0);
//            //2.3 聚合
//            buildAggregation(request);
//
//            //3、发出请求
//            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//            //4、解析结果
//            Map<String, List<String>> result = new HashMap<>();
//            Aggregations aggregations = response.getAggregations();
//            //根据种类名称，获取种类结果
//            List<String> categoryList = getAggByName(aggregations, "categoryAgg");
//            result.put("category", categoryList);
//            //根据产地名称，获取产地结果
//            List<String> originList = getAggByName(aggregations, "originAgg");
//            result.put("origin", originList);
//            return result;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 根据输入的信息找到对应的产品
//     * @param params
//     * @param request
//     */
//    public void buildBasicQuery(RequestParams params, SearchRequest request) {
//        //构建BooleanQuery
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        //关键字搜索
//        String key = params.getKey();
//        if (key == null || "" .equals(key)) {
//            boolQuery.must(QueryBuilders.matchAllQuery());
//        } else {
//            boolQuery.must(QueryBuilders.matchQuery("all", key));
//        }
//        //种类条件
//        if (params.getCategory() != null) {
//            boolQuery.filter(QueryBuilders.termQuery("category", params.getCategory()));
//        }
//        //产地条件
//        if (params.getOrigin() != null && !"" .equals(params.getOrigin())) {
//            boolQuery.filter(QueryBuilders.termQuery("origin", params.getOrigin()));
//        }
//        //价格条件
//        if (params.getMaxPrice() != null && params.getMinPrice() != null) {
//            boolQuery.filter(QueryBuilders.rangeQuery("price")
//                    .gte(params.getMinPrice()).lte(params.getMaxPrice()));
//        }
//        request.source().query(boolQuery);
//    }
//
//    /**
//     * 聚合
//     * @param request
//     */
//    private void buildAggregation(SearchRequest request) {
//        request.source().size(0);
//        request.source().aggregation(AggregationBuilders
//                .terms("categoryAgg")
//                .field("category")
//                .size(100));
//        request.source().aggregation(AggregationBuilders
//                .terms("originAgg")
//                .field("origin")
//                .size(100));
//    }
//
//    /**
//     * 解析聚合结果
//     * @param aggregations
//     * @param aggName
//     * @return
//     */
//    private List<String> getAggByName(Aggregations aggregations, String aggName) {
//        //4.1 根据聚合名称获取聚合结果
//        Terms terms = aggregations.get(aggName);
//        //4.2 获取buckets
//        List<? extends Terms.Bucket> buckets = terms.getBuckets();
//        //4.3 遍历
//        List<String> list = new ArrayList<>();
//        for (Terms.Bucket bucket : buckets) {
//            String key = bucket.getKeyAsString();
//            list.add(key);
//        }
//        return list;
//    }
}




