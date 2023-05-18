package com.zhao.salechicken.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.mapper.ProductMapper;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【product】的数据库操作Service实现
 * @createDate 2023-03-13 15:26:44
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProduct(Product product) {
        productMapper.addProduct(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productMapper.deleteProduct(productId);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public PageInfo selectProduct(int page, int pageSize, String productName, Integer category,String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectProduct(productName,category,origin);

        PageInfo<Product> pageInfo = new PageInfo<>(productList);

        return pageInfo;
    }

    @Override
    public PageInfo selectAllProduct(int page, int pageSize, String productName, Integer category, String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectAllProduct(productName, category,origin);

        PageInfo<Product> pageInfo = new PageInfo<>(productList);

        return pageInfo;
    }

    @Override
    public PageInfo selectProductBySales(int page,int pageSize, String productName, Integer category, String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectProductBySales(productName, category, origin);

        PageInfo<Product> pageInfo = new PageInfo<>(productList);

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
        return productMapper.getProductById(productId);
    }

    @Override
    public String getProductNameById(Integer productId) {
        return productMapper.getProductNameById(productId);
    }

    @Override
    public PageInfo sortProductByPriceDESC(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page,pageSize);

        //根据要求查询产品
        List<Product> products = productMapper.sortProductByPriceDESC(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(products);

        return productPageInfo;
    }

    @Override
    public PageInfo sortProductByPriceASC(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page,pageSize);

        //根据要求查询产品
        List<Product> products = productMapper.sortProductByPriceASC(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(products);

        return productPageInfo;
    }

    @Override
    public PageInfo selectShortSupplyProduct(int page,int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page,pageSize);

        //根据要求查询产品
        List<Product> products = productMapper.selectShortSupplyProduct(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(products);

        return productPageInfo;
    }
}




