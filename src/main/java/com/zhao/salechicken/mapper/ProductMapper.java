package com.zhao.salechicken.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.salechicken.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【product】的数据库操作Mapper
 * @createDate 2023-03-13 15:26:44
 * @Entity com.zhao.salechicken.pojo.Product
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 添加产品信息
     *
     * @param product
     */
    void addProduct(@Param("product") Product product);

    /**
     * 根据id删除产品
     *
     * @param productId
     */
    void deleteProduct(@Param("productId") Integer productId);

    /**
     * 修改商品信息
     *
     * @param product
     */
    void updateProduct(@Param("product") Product product);

    /**
     * 查询所有产品信息(有库存)(可根据不同要求查询)
     */
    List<Product> selectProduct(@Param("productName") String productName, @Param("category") Integer category,@Param("origin") String origin);

    /**
     * 查询所有产品信息(所有)
     */
    List<Product> selectAllProduct(@Param("productName") String productName, @Param("category") Integer category, @Param("origin") String origin);

    /**
     * 按销量由高到低查询所有商品
     * @return
     */
    List<Product> selectProductBySales(@Param("productName") String productName, @Param("category") Integer category,@Param("origin") String origin);

    /**
     * 通过产品名称搜索产品
     *
     * @param productName
     * @return
     */
    Product getProductByName(@Param("productName") String productName);

    /**
     * 通过种类搜索产品
     *
     * @param category
     * @return
     */
    List<Product> getProductByCategory(@Param("category") String category);

    /**
     * 通过id搜索产品
     *
     * @param productId
     * @return
     */
    Product getProductById(@Param("productId") Integer productId);

    /**
     * 通过产品id获取产品名称
     * @param productId
     * @return
     */
    String getProductNameById(@Param("productId") Integer productId);

    /**
     * 按价格降序展示产品
     * @param productName
     * @return
     */
    List<Product> sortProductByPriceDESC(@Param("productName") String productName, @Param("category") Integer category,@Param("origin") String origin);

    /**
     * 按价格升序展示产品
     * @param productName
     * @return
     */
    List<Product> sortProductByPriceASC(@Param("productName") String productName, @Param("category") Integer category,@Param("origin") String origin);

    /**
     * 查看缺货产品
     * @return
     */
    List<Product> selectShortSupplyProduct(@Param("productName") String productName, @Param("category") Integer category,@Param("origin") String origin);
}




