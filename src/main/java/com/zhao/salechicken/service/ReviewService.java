package com.zhao.salechicken.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.pojo.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86180
* @description 针对表【review】的数据库操作Service
* @createDate 2023-03-18 00:34:44
*/
public interface ReviewService {

    /**
     * 添加评论
     * @param review
     */
    void addReview(@Param("review") Review review);

    /**
     * 通过reviewId查找评论的userId
     * @param reviewId
     * @return
     */
    Integer getReviewUserById(Integer reviewId);

    /**
     * 通过reviewId删除评论
     * @param reviewId
     */
    void deleteReview(Integer reviewId);

    /**
     * 通过productId查找产品评论
     * @param productId
     * @return
     */
    PageInfo selectProductReview(int page, int pageSize, Integer productId);

    /**
     * 查找单个用户的所有评价
     * @param loginUser
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo selectMyReview(Integer loginUser, int page, int pageSize);
}
