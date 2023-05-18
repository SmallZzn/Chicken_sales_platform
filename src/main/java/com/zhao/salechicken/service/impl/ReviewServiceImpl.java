package com.zhao.salechicken.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.dto.ReviewDto;
import com.zhao.salechicken.mapper.ReviewMapper;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.pojo.Review;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.ProductService;
import com.zhao.salechicken.service.ReviewService;
import com.zhao.salechicken.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86180
 * @description 针对表【review】的数据库操作Service实现
 * @createDate 2023-03-18 00:34:44
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public void addReview(Review review) {
        reviewMapper.addReview(review);
    }

    @Override
    public Integer getReviewUserById(Integer reviewId) {
        return reviewMapper.getReviewUserIdById(reviewId);
    }

    @Override
    public void deleteReview(Integer reviewId) {
        reviewMapper.deleteReview(reviewId);
    }

    @Override
    public PageInfo selectProductReview(int page, int pageSize, Integer productId) {

        //1、开启分页功能
        PageHelper.startPage(page, pageSize);

        //2、根据id查询该产品的所有评价
        List<Review> reviews = reviewMapper.selectReviewById(productId);

        //3、设置分页插件
        PageInfo<Review> reviewPageInfo = new PageInfo<>(reviews);

        //4、设置要返回的分页插件
        PageInfo<ReviewDto> reviewDtoPageInfo = new PageInfo<>();

        //5、赋值信息到要返回的分页插件
        BeanUtils.copyProperties(reviewPageInfo,reviewDtoPageInfo);

        //6、创建集合reviewDtoList,并为其属性赋值
        List<ReviewDto> reviewDtoList = reviews.stream().map((item) -> {
            ReviewDto reviewDto = new ReviewDto();

            //复制item到reviewDto（item是遍历reviews得到的对象）
            BeanUtils.copyProperties(item, reviewDto);

            //为reviewDto的userName、productName和image赋值
            User user = userService.getUserById(reviewDto.getUserId());
            String productName = productService.getProductNameById(reviewDto.getProductId());
            reviewDto.setUserName(user.getUserName());
            reviewDto.setUserImage(user.getImage());
            reviewDto.setProductName(productName);

            return reviewDto;

        }).collect(Collectors.toList());

        //7、为reviewDtoPageInfo赋值
        reviewDtoPageInfo.setList(reviewDtoList);
        return reviewDtoPageInfo;
    }

    @Override
    public PageInfo selectMyReview(Integer loginUser, int page, int pageSize) {

        //1、查询该用户
        User user = userService.getUserById(loginUser);

        //2、开启分页功能
        PageHelper.startPage(page, pageSize);

        //3、查找该用户的所有评价
        List<Review> reviews = reviewMapper.selectMyReview(loginUser);

        //4、设置分页插件
        PageInfo<Review> reviewPageInfo = new PageInfo<>(reviews);

        //5、设置要返回的分页插件
        PageInfo<ReviewDto> reviewDtoPageInfo = new PageInfo<>();

        //6、赋值信息到要返回的分页插件
        BeanUtils.copyProperties(reviewPageInfo,reviewDtoPageInfo);

        //7、创建集合List<ReviewDto>,并为其属性赋值
        List<ReviewDto> reviewDtoList = reviews.stream().map((item) -> {
            ReviewDto reviewDto = new ReviewDto();

            //复制item到reviewDto（item是遍历reviews得到的对象）
            BeanUtils.copyProperties(item, reviewDto);

            //为reviewDto的productName、userImage、productImage赋值
            Integer productId = item.getProductId();
            Product product = productService.getProductById(productId);
            reviewDto.setProductName(product.getProductName());
            reviewDto.setProductImage(product.getImage());
            reviewDto.setUserImage(user.getImage());

            return reviewDto;
        }).collect(Collectors.toList());

        //8、为reviewDtoPageInfo赋值
        reviewDtoPageInfo.setList(reviewDtoList);
        return reviewDtoPageInfo;
    }
}




