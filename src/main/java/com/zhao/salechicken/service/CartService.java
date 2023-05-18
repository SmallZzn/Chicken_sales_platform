package com.zhao.salechicken.service;


import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.dto.CartdetailDto;
import com.zhao.salechicken.pojo.Cart;
import com.zhao.salechicken.pojo.Cartdetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86180
* @description 针对表【cart】的数据库操作Service
* @createDate 2023-03-17 16:32:33
*/
public interface CartService {
    /**
     * 查找用户购物车
     * @param userId
     * @return
     */
    Cart selectCart(Integer userId);

    /**
     * 创建购物车
     * @param userId
     */
    void createCart(Integer userId);

    /**
     * 修改购物车信息
     * @param cart
     */
    void updateCart(Cart cart);

    /**
     * 删除购物车
     * @param userId
     */
    void deleteCart(Integer userId);

    /**
     * 清空购物车
     * @param userId
     */
    void emptyCart(Integer userId);

    /**
     * 查看购物车
     * @param loginUser
     * @return
     */
    List<CartdetailDto> selectMyCart(Integer loginUser);

    /**
     * 商品加入购物车/购物车商品数量+1
     * @param loginUser
     * @param cartdetailId
     * @param productId
     */
    void addCart(Integer loginUser, Integer cartdetailId, Integer productId);

    /**
     * 减少购物车中商品数量
     * @param loginUser
     * @param cartdetailId
     * @param productId
     */
    void reduceCartProductNum(Integer loginUser,Integer cartdetailId,Integer productId);
}
