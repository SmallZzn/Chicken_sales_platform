package com.zhao.salechicken.service.impl;


import com.zhao.salechicken.mapper.CartdetailMapper;
import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Product;
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
    private CartdetailMapper cartdetailMapper;

    @Override
    public Cartdetail seleceCartdetail(Integer cartDetailId) {
        return cartdetailMapper.seleceCartdetail(cartDetailId);
    }

    public void updateCartdetail(Cartdetail cartdetail) {
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
        return cartdetailMapper.selectCartdetailById(cartId,productId);
    }
}




