package com.zhao.salechicken.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.dto.OrderdetailDto;
import com.zhao.salechicken.mapper.OrderdetailMapper;
import com.zhao.salechicken.mapper.ReviewMapper;
import com.zhao.salechicken.pojo.Orderdetail;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.pojo.Review;
import com.zhao.salechicken.service.OrderdetailService;
import com.zhao.salechicken.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86180
 * @description 针对表【orderdetail】的数据库操作Service实现
 * @createDate 2023-03-15 22:10:57
 */
@Service
public class OrderdetailServiceImpl extends ServiceImpl<OrderdetailMapper, Orderdetail> implements OrderdetailService {

    @Autowired
    private OrderdetailMapper orderdetailMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public void addOrdertail(Orderdetail orderdetail) {
        orderdetailMapper.addOrdertail(orderdetail);
    }

    @Override
    public PageInfo selectOrderDetail(int page, int pageSize, Long orderId) {
        //1、开启分页功能
        PageHelper.startPage(page, pageSize);

        //2、根据orderId查询订单详情
        List<Orderdetail> orderdetails = orderdetailMapper.selectOrderDetailByOrderId(orderId);

        //3、设置分页插件
        PageInfo<Orderdetail> orderdetailPageInfo = new PageInfo<>(orderdetails);

        //4、设置要返回的分页插件
        PageInfo<OrderdetailDto> orderdetailDtoPageInfo = new PageInfo<>();

        //5、赋值信息到要返回的分页插件
        BeanUtils.copyProperties(orderdetailPageInfo, orderdetailDtoPageInfo);

        //6、创建集合orderdetailDtos,并为其属性赋值
        List<OrderdetailDto> orderdetailDtos = orderdetails.stream().map((item) -> {
            OrderdetailDto orderdetailDto = new OrderdetailDto();

            //复制item到orderdetailDto
            BeanUtils.copyProperties(item, orderdetailDto);

            Product product = productService.getProductById(item.getProductId());

            //为orderdetailDto的productName赋值
            orderdetailDto.setProductName(product.getProductName());

            //为orderdetailDto的image赋值
            orderdetailDto.setImage(product.getImage());

            //判断商品是否已经评价过
            Review review = reviewMapper.selectReviewByProductIdAndOrderId(orderId, product.getProductId());
            if (review == null) {
                orderdetailDto.setIsReview(false);
            } else {
                orderdetailDto.setIsReview(true);
            }

            return orderdetailDto;
        }).collect(Collectors.toList());

        //7、为orderdetailDtoPageInfo赋值
        orderdetailDtoPageInfo.setList(orderdetailDtos);

        return orderdetailDtoPageInfo;
    }
}




