package com.zhao.salechicken.service.impl;

import com.zhao.salechicken.dto.CartdetailDto;
import com.zhao.salechicken.mapper.CartMapper;
import com.zhao.salechicken.pojo.Cart;
import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.service.CartService;
import com.zhao.salechicken.service.CartdetailService;
import com.zhao.salechicken.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 86180
* @description 针对表【cart】的数据库操作Service实现
* @createDate 2023-03-17 16:32:33
*/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartdetailService cartdetailService;

    @Autowired
    private ProductService productService;

    @Override
    public Cart selectCart(Integer userId) {
        return cartMapper.selectCart(userId);
    }

    public void createCart(Integer userId){
        cartMapper.createCart(userId);
    }

    @Override
    public void updateCart(Cart cart) {
        cartMapper.updateCart(cart);
    }

    @Override
    public void deleteCart(Integer userId) {
        cartMapper.deleteCart(userId);
    }

    @Override
    public void emptyCart(Integer userId) {
        cartMapper.emptyCart(userId);
    }


    @Override
    public List<CartdetailDto> selectMyCart(Integer loginUser) {

        //1、查找用户的购物车
        Cart cart = cartMapper.selectCart(loginUser);

        //2、查询用户购物车中的所有商品
        List<Cartdetail> cartdetails = cartdetailService.selectAllCartdetial(cart.getCartId());

        //3、为cartdetailDto的属性赋值
        List<CartdetailDto> cartdetailDtos = cartdetails.stream().map((item) -> {
            //创建要返回的对象
            CartdetailDto cartdetailDto = new CartdetailDto();

            //复制cartdetails的属性到cartdetailDto中
            BeanUtils.copyProperties(item, cartdetailDto);

            //查询商品信息
            Product product = productService.getProductById(item.getProductId());
            //为cartdetailDto的productName赋值
            cartdetailDto.setProductName(product.getProductName());
            //为cartdetailDto的price赋值
            cartdetailDto.setPrice(product.getPrice());
            //为cartdetailDto的inventory赋值
            cartdetailDto.setInventory(product.getInventory());
            //为cartdetailDto的image赋值
            cartdetailDto.setImage(product.getImage());

            return cartdetailDto;
        }).collect(Collectors.toList());

        return cartdetailDtos;
    }

    @Override
    public void addCart(Integer loginUser, Integer productId) {
        //查找用户的购物车
        Cart cart = cartMapper.selectCart(loginUser);

        //查询改商品信息
        Product productInfo = productService.getProductById(productId);

        cartdetailService.addProductIntoCart(productInfo, cart.getCartId());

        //更新购物车商品数量
        cart.setNum(cart.getNum() + 1);
        //更新购物车商品总价
        cart.setAllprice(cart.getAllprice() + productInfo.getPrice());
        //更新数据库
        cartMapper.updateCart(cart);
    }
}




