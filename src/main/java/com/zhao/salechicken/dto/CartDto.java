package com.zhao.salechicken.dto;

import com.zhao.salechicken.pojo.Cart;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通过cart表中的userId找到用户的所有cartdetialId，放到集合中
 * 然后在cartdetial表找到对应的信息，封装到CartDto中,返回给前端显示在页面上
 */

@Data
@Repository
public class CartDto extends Cart {

    /**
     * 购物车详情列表
     */
    List<CartdetailDto> cartdetials;
}
