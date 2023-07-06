package com.zhao.salechicken.controller;

import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.CartdetailDto;
import com.zhao.salechicken.pojo.Cart;
import com.zhao.salechicken.pojo.Cartdetail;
import com.zhao.salechicken.service.CartService;
import com.zhao.salechicken.service.CartdetailService;
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
        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();


        List<CartdetailDto> cartdetailDtos = cartService.selectMyCart(loginUser);
        return R.success(cartdetailDtos);
    }

    /**
     * 商品加入购物车
     *
     * @param productId
     * @return
     */
    @PostMapping("/addCart")
    public R<String> addCart(Integer productId) {

        System.out.println("productId = " + productId);

        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        //获取当前用户购物车
        Cart cart = cartService.selectCart(loginUser);

        //查找购物车是否已包含改商品
        Cartdetail cartdetail = cartdetailService.selectCartdetailById(cart.getCartId(), productId);
        if (cartdetail != null) {
            return R.error("购物车已包含该商品!!!");
        }

        //商品加入购物车
        cartService.addCart(loginUser, productId);

        return R.success("添加成功");
    }

    /**
     * 购物车商品数量增加或减少
     *
     * @param cartdetail
     * @return
     */
    @PutMapping("/updateCartdetail")
    public R<String> updateCartdetail(@RequestBody Cartdetail cartdetail) {
        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        //获取我的购物车
//        Cart cart = cartService.selectCart(loginUser);

        //查看购物车中改商品的详情
//        Cartdetail myCartdetail = cartdetailService.selectCartdetailById(cart.getCartId(), cartdetail.getProductId());

        //若不存在该商品，则将该商品加入购物车
//        if (myCartdetail == null) {
//            Product product = productService.getProductById(cartdetail.getProductId());
//            cartdetailService.addProductIntoCart(product, cart.getCartId());
//            return R.success("加入购物车成功");
//        }
        //若存在，则修改购物车详情
        cartdetailService.updateCartdetail(loginUser, cartdetail);

        return R.success("商品数量已修改");
    }

//    /**
//     * 减少购物车中商品数量
//     *
//     * @param request
//     * @param cartdetail
//     * @return
//     */
//    @DeleteMapping("/reduceCartProductNum")
//    public R<String> reduceCartProductNum(HttpServletRequest request, @RequestBody Cartdetail cartdetail) {
//        //获取当前登录用户的id
////        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
//        Integer loginUser = BaseContext.getCurrentId();
//
//        cartService.reduceCartProductNum(loginUser, cartdetail.getCartdetailId(), cartdetail.getProductId());
//
//        return R.success("减少成功");
//    }

    /**
     * 清空购物车
     *
     * @param request
     * @return
     */
    @DeleteMapping("/emptyCart")
    public R<String> emptyCart(HttpServletRequest request) {
        //获取当前登录用户的id
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
        Integer loginUser = BaseContext.getCurrentId();

        //查找用户的购物车
        Cart cart = cartService.selectCart(loginUser);

        //删除相应的购物车详情
        cartdetailService.emptyCartdetail(cart.getCartId());

        //更新购物车信息
        cartService.emptyCart(loginUser);

        return R.success("购物车已清空");
    }
}
