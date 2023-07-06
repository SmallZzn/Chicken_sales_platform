package com.zhao.salechicken.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.dto.ProductDto;
import com.zhao.salechicken.dto.UserDto;
import com.zhao.salechicken.mapper.CategoryMapper;
import com.zhao.salechicken.mapper.ProductMapper;
import com.zhao.salechicken.pojo.Address;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public PageInfo selectProduct(int page, int pageSize, String productName, Integer category, String origin) {
        //1、开启分页插件
        PageHelper.startPage(page, pageSize);

        //2、查询产品信息
        List<Product> productList = productMapper.selectProduct(productName, category, origin);

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

        return productDtoPageInfo;
    }

    @Override
    public PageInfo selectProductBySales(int page, int pageSize, String productName, Integer category, String origin) {
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
        PageHelper.startPage(page, pageSize);

        //根据要求查询产品
        List<Product> products = productMapper.sortProductByPriceDESC(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(products);

        return productPageInfo;
    }

    @Override
    public PageInfo sortProductByPriceASC(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page, pageSize);

        //根据要求查询产品
        List<Product> products = productMapper.sortProductByPriceASC(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(products);

        return productPageInfo;
    }

    @Override
    public PageInfo selectShortSupplyProduct(int page, int pageSize, String productName, Integer category, String origin) {
        //开启分页插件
        PageHelper.startPage(page, pageSize);

        //根据要求查询产品
        List<Product> products = productMapper.selectShortSupplyProduct(productName, category, origin);

        PageInfo<Product> productPageInfo = new PageInfo<>(products);

        return productPageInfo;
    }
}




