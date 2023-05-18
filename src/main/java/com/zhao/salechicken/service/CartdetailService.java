package com.zhao.salechicken.service;


import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【cartdetial】的数据库操作Service
 * @createDate 2023-03-17 16:32:39
 */
public interface CartdetailService {

    /**
     * 根据购物车详情id查询购物车项
     * @param cartdrtailId
     * @return
     */
    Cartdetail seleceCartdetail(Integer cartdrtailId);

    /**
     * 购物车商品数量增加
     *
     * @param cartdetail
     */
    void updateCartdetail(Cartdetail cartdetail);

    /**
     * 向购物车添加商品
     *
     * @param product
     */
    void addProductIntoCart(Product product, Integer cartId);

    /**
     * 查询购物车中的所有商品
     *
     * @param cartId
     * @return
     */
    List<Cartdetail> selectAllCartdetial(Integer cartId);

    /**
     * 清空购物车
     *
     * @param cartId
     */
    void emptyCartdetail(Integer cartId);

    /**
     * 减少购物车中的商品数量
     *
     * @param productId
     * @param cartId
     */
    void deleteProductFromCart(Integer productId, Integer cartId);

    /**
     * 查找要结账的购物车商品
     *
     * @param cartId
     * @param ids
     * @return
     */
    List<Cartdetail> selectPayCartdetail(Integer cartId, List<Integer> ids);

    /**
     * 根据cartId和productId查询购物车详情
     * @param cartId
     * @param productId
     * @return
     */
    Cartdetail selectCartdetailById(Integer cartId,Integer productId);
}
