package com.zhao.salechicken.service;


import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.pojo.Product;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【product】的数据库操作Service
 * @createDate 2023-03-13 15:26:44
 */
public interface ProductService {
    /**
     * 添加产品
     *
     * @param product
     */
    void addProduct(Product product);

    /**
     * 根据id删除产品
     *
     * @param productId
     */
    void deleteProduct(Integer productId);

    /**
     * 修改商品信息
     *
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 查询所有产品信息（分页）（有库存）
     *
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    PageInfo selectProduct(int page, int pageSize, String productName, Integer category, String origin);

    /**
     * 查询所有产品信息（分页）（所有）
     *
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    PageInfo selectAllProduct(int page, int pageSize, String productName, Integer category, String origin);

    /**
     * 按销量由高到低查询所有商品
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    PageInfo selectProductBySales(int page, int pageSize, String productName, Integer category, String origin);

    /**
     * 根据名称查询相应的产品
     *
     * @param productName
     * @return
     */
    Product getProductByName(String productName);

    /**
     * 根据种类查询相应的产品
     *
     * @param category
     * @return
     */
    List<Product> getProductByCategory(String category);

    /**
     * 通过id获取产品的所有信息
     *
     * @param productId
     * @return
     */
    Product getProductById(Integer productId);

    /**
     * 通过产品id获取产品名称
     *
     * @param productId
     * @return
     */
    String getProductNameById(Integer productId);

    /**
     * 按价格降序展示产品
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    PageInfo sortProductByPriceDESC(int page, int pageSize, String productName, Integer category, String origin);

    /**
     * 按价格升序展示产品
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    PageInfo sortProductByPriceASC(int page, int pageSize, String productName, Integer category, String origin);

    /**
     * 查看缺货产品
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    PageInfo selectShortSupplyProduct(int page, int pageSize, String productName, Integer category, String origin);
}
