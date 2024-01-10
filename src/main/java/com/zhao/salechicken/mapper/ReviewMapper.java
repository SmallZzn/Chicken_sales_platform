package com.zhao.salechicken.mapper;


import com.github.pagehelper.Page;
import com.zhao.salechicken.pojo.Review;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【review】的数据库操作Mapper
 * @createDate 2023-03-18 00:34:44
 * @Entity com.zhao.salechicken.pojo.Review
 */

@Mapper
public interface ReviewMapper {
    /**
     * 通过productId获取产品评论
     *
     * @param productId
     * @return
     */
    List<Review> selectReviewById(@Param("productId") Integer productId);

    /**
     * 添加评论
     *
     * @param review
     */
    void addReview(@Param("review") Review review);

    /**
     * 通过reviewId查询评论的作者的userId
     *
     * @param reviewId
     * @return
     */
    Integer getReviewUserIdById(@Param("reviewId") Integer reviewId);

    /**
     * 通过reviewId删除评论
     *
     * @param reviewId
     */
    void deleteReview(@Param("reviewId") Integer reviewId);

    /**
     * 通过userId查找用户的所有评价
     * @param userId
     * @return
     */
    List<Review> selectMyReview(@Param("userId") Integer userId);

    /**
     * 通过产品id和订单id查找评价
     * @param orderId
     * @param productId
     * @return
     */
    Review selectReviewByProductIdAndOrderId(@Param("orderId") Long orderId, @Param("productId") Integer productId);
}




