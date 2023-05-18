package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【cartdetial】的数据库操作Mapper
 * @createDate 2023-03-17 16:32:39
 * @Entity com.zhao.salechicken.pojo.Cartdetial
 */
@Mapper
public interface CartdetailMapper {

    Cartdetail seleceCartdetail(@Param("cartdetailId") Integer cartdetailId);

    /**
     * 购物车商品数量增加
     *
     * @param cartdetail
     */
    void updateCartdetail(@Param("cartdetail") Cartdetail cartdetail);

    /**
     * 向购物车添加商品
     *
     * @param product
     */
    void addProductIntoCart(@Param("product") Product product, @Param("cartId") Integer cartId);

    /**
     * 查询购物车中的所有商品
     *
     * @param cartId
     * @return
     */
    List<Cartdetail> selectAllCartdetial(@Param("cartId") Integer cartId);

    /**
     * 清空购物车
     *
     * @param cartId
     */
    void emptyCartdetail(@Param("cartId") Integer cartId);

    /**
     * 减少购物车中的商品
     *
     * @param productId
     */
    void deleteProductFromCart(@Param("productId") Integer productId, @Param("cartId") Integer cartId);

    /**
     * 查询要结账的购物车商品
     * @param cartId
     * @param ids
     * @return
     */
    List<Cartdetail> selectPayCartdetail(@Param("cartId") Integer cartId, @Param("ids") List<Integer> ids);

    /**
     * 根据cartId和productId查询购物车详情
     * @param cartId
     * @param productId
     * @return
     */
    Cartdetail selectCartdetailById(@Param("cartId") Integer cartId,@Param("productId") Integer productId);
}




