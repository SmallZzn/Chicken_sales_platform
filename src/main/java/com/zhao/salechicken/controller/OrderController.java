package com.zhao.salechicken.controller;

import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.PayDto;
import com.zhao.salechicken.pojo.Order;
import com.zhao.salechicken.service.OrderService;
import com.zhao.salechicken.service.OrderdetailService;
import com.zhao.salechicken.service.PermissiondetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderdetailService orderdetailService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    /**
     * 查询某个用户的订单
     *
     * @param page
     * @param pageSize
     * @param userId
     * @return
     */
    @GetMapping("/selectAllOrder")
    public R<PageInfo> selectAllOrder(HttpServletRequest request, int page, int pageSize, Integer userId) {
        //如果查看所有订单,要判断是否有查看订单的权限
        if (userId == null) {
            //获取当前登录用户的id
            Integer loginUser = BaseContext.getCurrentId();

            //判断是否有查看订单的权限
            if (!permissiondetailService.judgePermission(loginUser, 10)) {
                return R.error("您没有该权限!!!");
            }
        }
        PageInfo pageInfo = orderService.selectAllOrder(page, pageSize, userId);
        return R.success(pageInfo);
    }

    /**
     * 删除订单信息（可批量）
     *
     * @param request
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteOrder")
    public R<String> deleteOrder(HttpServletRequest request, @RequestParam List<Long> ids) {

        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有删除订单的权限
        if (!permissiondetailService.judgePermission(loginUser, 11)) {
            return R.error("您没有该权限!!!");
        }

        //判断是否选中了订单
        if (ids == null) {
            return R.error("您当前为选中任何订单");
        }

        orderService.deleteOrder(ids);
        return R.success("删除成功");
    }

    /**
     * 查询指定订单(根据订单状态/id)(分页)
     */
    @GetMapping("/selectOrder")
    public R<PageInfo> selectOrder(int page, int pageSize, Integer userId, Long orderId, String status) {
        //查询信息
        PageInfo pageInfo = orderService.selectOrder(userId, page, pageSize, orderId, status);
        return R.success(pageInfo);
    }

    /**
     * 查看订单详情
     *
     * @param page
     * @param pageSize
     * @param orderId
     * @return
     */
    @GetMapping("/selectOrderDetail")
    public R<PageInfo> selectOrderDetail(int page, int pageSize, Long orderId) {
        PageInfo pageInfo = orderdetailService.selectOrderDetail(page, pageSize, orderId);
        return R.success(pageInfo);
    }

    /**
     * 修改订单信息
     *
     * @param order
     * @return
     */
    @PutMapping("/updateOrder")
    public R<String> updateOrder(@RequestBody Order order) {

        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有修改订单的权限
        if (!order.getStatus().equals("申请退款")) {
            if (!permissiondetailService.judgePermission(loginUser, 11)) {
                return R.error("您没有该权限!!!");
            }
        }

        orderService.updateOrder(order);

        return R.success("修改成功");
    }

    /**
     * 支付订单
     *
     * @param payDto
     * @return
     */
    @PostMapping("/pay")
    public R<String> pay(@RequestBody PayDto payDto) {
        return orderService.pay(payDto);
    }
}
