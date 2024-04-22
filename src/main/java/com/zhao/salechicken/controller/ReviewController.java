package com.zhao.salechicken.controller;

import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.annotation.MyLog;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Review;
import com.zhao.salechicken.service.ReviewService;
import com.zhao.salechicken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;
    
    /**
     * 新增评论
     *
     * @param review
     * @return
     */
    @PostMapping("/addReview")
    @MyLog(title = "评价模块", content = "新增评论")
    public R<String> addReview(@RequestBody Review review) {
        //1、获取当前用户id
        Integer loginUser = BaseContext.getCurrentId();

        //2、设置要添加的review
        review.setUserId(loginUser);
        review.setCreateTime(new Date());

        //3、添加评论
        reviewService.addReview(review);

        return R.success("添加成功!!!");
    }

    /**
     * 删除产品评价
     * 用户只能删自己的，管理员只有admin能删除用户评价
     *
     * @param reviewId
     * @return
     */
    @DeleteMapping("/deleteReview")
    @MyLog(title = "评价模块", content = "删除产品评价")
    public R<String> deleteReview(Integer reviewId) {
        //1、获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        //2、查看登录者的名称
        String loginUserName = userService.getUserNameById(loginUser);

        //3、查看评论的名称
        Integer userId = reviewService.getReviewUserById(reviewId);
        String userName = userService.getUserNameById(userId);

        //3、判断操作者是不是作者本人
//        if (!(loginUserName.equals(userName))) {
//            return R.error("您无法删除该评论");
//        }

        //4、删除评论
        reviewService.deleteReview(reviewId);
        return R.success("该评论已删除");
    }

    /**
     * 查看产品评价(分页)
     *
     * @param productId
     * @return
     */
    @GetMapping("/selectReview")
    @MyLog(title = "评价模块", content = "查看产品评价(分页)")
    public R<PageInfo> selectProductReview(int page, int pageSize, Integer productId) {
        PageInfo pageInfo = reviewService.selectProductReview(page, pageSize, productId);
        return R.success(pageInfo);
    }

    /**
     * 查看单个用户的所有评价（分页）
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/selectMyReview")
    @MyLog(title = "评价模块", content = "查看单个用户的所有评价（分页）")
    public R<PageInfo> selectMyReview(int page, int pageSize,Integer userId) {

        PageInfo pageInfo = reviewService.selectMyReview(userId, page, pageSize);

        return R.success(pageInfo);
    }
}
