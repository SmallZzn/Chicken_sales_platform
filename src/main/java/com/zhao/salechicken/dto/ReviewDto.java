package com.zhao.salechicken.dto;

import com.zhao.salechicken.pojo.Review;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class ReviewDto extends Review {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 产品图片
     */
    private String productImage;
}
