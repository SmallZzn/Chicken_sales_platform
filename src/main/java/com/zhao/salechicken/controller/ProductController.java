package com.zhao.salechicken.controller;

import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.pojo.Review;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.PermissiondetailService;
import com.zhao.salechicken.service.ProductService;
import com.zhao.salechicken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    /**
     * 添加产品
     *
     * @param request
     * @param product
     * @return
     */
    @PostMapping("/addProduct")
    public R<String> addProduct(HttpServletRequest request, @RequestBody Product product) {

        //获取当前登录用户
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有添加产品的权限
        if (!permissiondetailService.judgePermission(loginUser,6)) {
            return R.error("您没有该权限!!!");
        }

        productService.addProduct(product);
        return R.success("添加成功!!!");
    }

    /**
     * 删除产品
     *
     * @param request
     * @param productId
     * @return
     */
    @DeleteMapping("/deleteProduct")
    public R<String> deleteProduct(HttpServletRequest request, Integer productId) {
        //获取当前登录用户
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有删除产品的权限
        if (!permissiondetailService.judgePermission(loginUser,7)) {
            return R.error("您没有该权限!!!");
        }

        productService.deleteProduct(productId);
        return R.success("删除成功!!!");
    }

    /**
     * 修改产品信息
     *
     * @param request
     * @param product
     * @return
     */
    @PutMapping("/updateProduct")
    public R<String> updateProduct(HttpServletRequest request, @RequestBody Product product) {

        //获取当前登录用户
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有更新产品的权限
        if (!permissiondetailService.judgePermission(loginUser,8)) {
            return R.error("您没有该权限!!!");
        }

        productService.updateProduct(product);
        return R.success("修改成功!!!");
    }

    /**
     * 查询所有产品信息（分页）（有库存）已删除
     *
     * @param page
     * @param pageSize
     * @param productName
     * @param category
     * @param origin
     * @return
     */
    @GetMapping("/selectProduct")
    public R<PageInfo> selectProduct(int page, int pageSize, String productName, Integer category, String origin) {
        PageInfo pageInfo = productService.selectProduct(page, pageSize, productName, category, origin);
        return R.success(pageInfo);
    }

    /**
     * 查询所有产品信息（分页）（所有）
     *
     * @return
     */
    @GetMapping("/selectAllProduct")
    public R<PageInfo> selectAllProduct(HttpServletRequest request, int page, int pageSize, String productName, Integer category, String origin) {
        //获取当前登录用户
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
        Integer loginUser = BaseContext.getCurrentId();

//        //判断是否有查看产品的权限
//        if (!permissiondetailService.judgePermission(loginUser,9)) {
//            return R.error("您没有该权限!!!");
//        }

        PageInfo pageInfo = productService.selectAllProduct(page, pageSize, productName, category, origin);
        return R.success(pageInfo);
    }

    /**
     * 按销量由高到低查询所有商品
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/selectProductBySales")
    public R<PageInfo> selectProductBySales(int page, int pageSize,String productName, Integer category, String origin) {
        PageInfo pageInfo = productService.selectProductBySales(page, pageSize,productName, category, origin);
        return R.success(pageInfo);
    }

    /**
     * 查看产品全部信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/selectProductInfo")
    public R<Product> selectProductInfo(Integer productId) {
        Product product = productService.getProductById(productId);
        return R.success(product);
    }

    /**
     * 按价格降序
     *
     * @return
     */
    @GetMapping("/sortProductByPriceDESC")
    public R<PageInfo> sortProductByPriceDESC(int page, int pageSize, String productName, Integer category, String origin) {
        PageInfo pageInfo = productService.sortProductByPriceDESC(page, pageSize, productName, category, origin);
        return R.success(pageInfo);
    }

    /**
     * 按价格升序
     *
     * @return
     */
    @GetMapping("/sortProductByPriceASC")
    public R<PageInfo> sortProductByPriceASC(int page, int pageSize, String productName, Integer category, String origin) {
        PageInfo pageInfo = productService.sortProductByPriceASC(page, pageSize, productName, category, origin);
        return R.success(pageInfo);
    }

    /**
     * 查看缺货产品
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/selectShortSupplyProduct")
    public R<PageInfo> selectShortSupplyProduct(HttpServletRequest request,int page, int pageSize, String productName, Integer category, String origin) {
        //获取当前登录用户
//        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有查看产品的权限
        if (!permissiondetailService.judgePermission(loginUser,9)) {
            return R.error("您没有该权限!!!");
        }

        PageInfo pageInfo = productService.selectShortSupplyProduct(page, pageSize,productName, category, origin);
        return R.success(pageInfo);
    }
}
