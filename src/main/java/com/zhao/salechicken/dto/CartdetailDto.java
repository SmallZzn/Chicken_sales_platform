package com.zhao.salechicken.dto;

import com.zhao.salechicken.pojo.Cartdetail;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class CartdetailDto extends Cartdetail {

    /**
     * 产品单价
     */
    private Double price;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品库存
     */
    private Integer inventory;

    /**
     * 产品图片
     */
    private String image;
}
