package com.zhao.salechicken.controller;

import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.CartdetailDto;
import com.zhao.salechicken.pojo.Cart;
import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.service.CartService;
import com.zhao.salechicken.service.CartdetailService;
import com.zhao.salechicken.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartdetailService cartdetailService;

    /**
     * 查看购物车
     *
     * @param request
     * @return
     */
    @GetMapping("/selectMyCart")
    public R<List<CartdetailDto>> selectMyCart(HttpServletRequest request) {

        Long loginUser = BaseContext.getCurrentId();
        //获取当前登录用户的id
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        List<CartdetailDto> cartdetailDtos = cartService.selectMyCart(loginUser);
        return R.success(cartdetailDtos);
    }

    /**
     * 商品加入购物车/购物车商品数量+1
     *
     * @param request
     * @param cartdetail
     * @return
     */
//    @PostMapping("/addCart")
//    public R<String> addCart(HttpServletRequest request, @RequestBody Cartdetail cartdetail) {
//        //获取当前登录用户的id
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
//
//        //判断购物车是否已经包含该商品
//        Cart cart = cartService.selectCart(loginUser);
//        Cartdetail myCartdetail = cartdetailService.selectCartdetailById(cart.getCartId(), cartdetail.getProductId());
//        if (myCartdetail == null) {
//            return R.error("购物车已存在该商品!!!");
//        }
//        cartService.addCart(loginUser, cartdetail.getCartdetailId(), cartdetail.getProductId());
//
//        return R.success("添加成功");
//    }

    /**
     * 商品加入购物车/购物车商品数量增加或减少
     *
     * @param request
     * @param productId
     * @param quantity
     * @return
     */
    @PostMapping("/updateCartdetail")
    public R<String> updateCartdetail(HttpServletRequest request, Integer productId, Integer quantity) {
        //获取当前登录用户的id
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        //获取我的购物车
        Cart cart = cartService.selectCart(loginUser);

        //查看购物车中改商品的详情
        Cartdetail cartdetail = cartdetailService.selectCartdetailById(cart.getCartId(), productId);

        //若不存在该商品，则将该商品加入购物车
        if (cartdetail == null) {
            Product product = productService.getProductById(productId);
            cartdetailService.addProductIntoCart(product,cart.getCartId());
            return R.success("加入购物车成功");
        }
        //若存在，则改变数量
        cartdetail.setQuantity(quantity);
        cartdetailService.updateCartdetail(cartdetail);

        return R.success("商品数量已修改");
    }

    /**
     * 减少购物车中商品数量
     *
     * @param request
     * @param cartdetail
     * @return
     */
    @DeleteMapping("/reduceCartProductNum")
    public R<String> reduceCartProductNum(HttpServletRequest request, @RequestBody Cartdetail cartdetail) {
        //获取当前登录用户的id
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        cartService.reduceCartProductNum(loginUser, cartdetail.getCartdetailId(), cartdetail.getProductId());

        return R.success("减少成功");
    }

    /**
     * 清空购物车
     *
     * @param request
     * @return
     */
    @DeleteMapping("/emptyCart")
    public R<String> emptyCart(HttpServletRequest request) {
        //获取当前登录用户的id
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        //查找用户的购物车
        Cart cart = cartService.selectCart(loginUser);

        //删除相应的购物车详情
        cartdetailService.emptyCartdetail(cart.getCartId());

        //更新购物车信息
        cartService.emptyCart(loginUser);

        return R.success("购物车已清空");
    }
}
