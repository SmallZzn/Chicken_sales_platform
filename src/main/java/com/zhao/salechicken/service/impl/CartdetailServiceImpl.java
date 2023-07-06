package com.zhao.salechicken.service.impl;


import com.zhao.salechicken.mapper.CartMapper;
import com.zhao.salechicken.mapper.CartdetailMapper;
import com.zhao.salechicken.pojo.Cart;
import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.service.CartService;
import com.zhao.salechicken.service.CartdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【cartdetial】的数据库操作Service实现
 * @createDate 2023-03-17 16:32:39
 */
@Service
public class CartdetailServiceImpl implements CartdetailService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartdetailMapper cartdetailMapper;

    @Override
    public Cartdetail seleceCartdetail(Integer cartDetailId) {
        return cartdetailMapper.seleceCartdetail(cartDetailId);
    }

    public void updateCartdetail(Integer loginUSer, Cartdetail cartdetail) {

        //获取用户的购物车
        Cart cart = cartMapper.selectCart(loginUSer);

        //查询原购物车项
        Cartdetail myCartdetail = cartdetailMapper.seleceCartdetail(cartdetail.getCartdetailId());

        //更新购物车商品数量
        cart.setNum(cart.getNum() - myCartdetail.getQuantity() + cartdetail.getQuantity());
        //更新购物车商品总价
        cart.setAllprice(cart.getAllprice() - myCartdetail.getAllprice() + cartdetail.getAllprice());
        //更新数据库
        cartMapper.updateCart(cart);

        //若商品数量为0，则从购物车中删除该商品
        if (cartdetail.getQuantity() == 0) {
            cartdetailMapper.deleteProductFromCart(cartdetail.getProductId(),cartdetail.getCartId());
        }

        //更新购物车项
        cartdetailMapper.updateCartdetail(cartdetail);
    }

    @Override
    public void addProductIntoCart(Product product, Integer cartId) {
        cartdetailMapper.addProductIntoCart(product, cartId);
    }

    @Override
    public List<Cartdetail> selectAllCartdetial(Integer cartId) {
        return cartdetailMapper.selectAllCartdetial(cartId);
    }

    @Override
    public void emptyCartdetail(Integer cartId) {
        cartdetailMapper.emptyCartdetail(cartId);
    }

    @Override
    public void deleteProductFromCart(Integer productId, Integer cartId) {
        cartdetailMapper.deleteProductFromCart(productId, cartId);
    }

    @Override
    public List<Cartdetail> selectPayCartdetail(Integer cartId, List<Integer> ids) {
        return cartdetailMapper.selectPayCartdetail(cartId, ids);
    }

    @Override
    public Cartdetail selectCartdetailById(Integer cartId, Integer productId) {
        return cartdetailMapper.selectCartdetailById(cartId, productId);
    }
}




