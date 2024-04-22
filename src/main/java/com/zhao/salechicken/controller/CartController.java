package com.zhao.salechicken.controller;

import com.zhao.salechicken.annotation.MyLog;
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
    private CartService cartService;

    @Autowired
    private CartdetailService cartdetailService;

    @Autowired
    private ProductService productService;

    /**
     * 查看购物车
     *
     * @param request
     * @return
     */
    @GetMapping("/selectMyCart")
    @MyLog(title = "购物车模块", content = "查看购物车")
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
    @MyLog(title = "购物车模块", content = "商品加入购物车")
    public R<String> addCart(Integer productId) {
        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        //获取当前用户购物车
        Cart cart = cartService.selectCart(loginUser);

        Product product = productService.getProductById(productId);
        if (product.getInventory() <= 0) {
            return R.error("商品数量不足!!!");
        }

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
    @MyLog(title = "购物车模块", content = "购物车商品数量增加或减少")
    public R<String> updateCartdetail(@RequestBody Cartdetail cartdetail) {
        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        Integer productId = cartdetail.getProductId();
        Product product = productService.getProductById(productId);
        if (product.getInventory() <= 0) {
            return R.error("商品数量不足");
        }

        //若存在，则修改购物车详情
        cartdetailService.updateCartdetail(loginUser, cartdetail);

        return R.success("商品数量已修改");
    }

    /**
     * 清空购物车
     *
     * @param request
     * @return
     */
    @DeleteMapping("/emptyCart")
    @MyLog(title = "购物车模块", content = "清空购物车")
    public R<String> emptyCart(HttpServletRequest request) {
        //获取当前登录用户的id
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
