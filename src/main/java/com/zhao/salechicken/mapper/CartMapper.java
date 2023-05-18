package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 86180
 * @description 针对表【cart】的数据库操作Mapper
 * @createDate 2023-03-17 16:32:33
 * @Entity com.zhao.salechicken.pojo.Cart
 */
@Mapper
public interface CartMapper {
    /**
     * 查找用户购物车
     * @param userId
     * @return
     */
    Cart selectCart(@Param("userId") Integer userId);

    /**
     * 创建购物车
     * @param userId
     */
    void createCart(@Param("userId") Integer userId);

    /**
     * 修改购物车
     * @param cart
     */
    void updateCart(@Param("cart") Cart cart);

    /**
     * 删除购物车
     * @param userId
     */
    void deleteCart(@Param("userId") Integer userId);

    /**
     * 清空购物车
     * @param userId
     */
    void emptyCart(@Param("userId") Integer userId);
}




