package com.zhao.salechicken.controller;


import com.zhao.salechicken.annotation.MyLog;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Category;
import com.zhao.salechicken.service.CategoryService;
import com.zhao.salechicken.service.PermissiondetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    /**
     * 查看所有商品种类
     *
     * @return
     */
    @GetMapping("/selectAllCategory")
    @MyLog(title = "种类模块", content = "查看所有商品种类")
    public R<List> selectAllCategory() {
        List<Category> categorys = categoryService.selectAllCategory();
        return R.success(categorys);
    }

    /**
     * 新增产品种类
     *
     * @param request
     * @param category
     * @return
     */
    @PostMapping("/addCategory")
    @MyLog(title = "种类模块", content = "新增产品种类")
    public R<String> addCategory(HttpServletRequest request, @RequestBody Category category) {
        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有添加产品种类的权限
        if (!permissiondetailService.judgePermission(loginUser, 14)) {
            return R.error("您没有该权限!!!");
        }

        //为category设置type
        category.setType(categoryService.selectCategoryCount() + 1);

        categoryService.addCategory(category);

        return R.success("添加种类成功");
    }

    /**
     * 删除产品种类
     *
     * @param request
     * @param categoryId
     * @return
     */
    @DeleteMapping("/deleteCategory")
    @MyLog(title = "种类模块", content = "删除产品种类")
    public R<String> deleteCategory(HttpServletRequest request, Integer categoryId) {
        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判断是否有添加产品种类的权限
        if (!permissiondetailService.judgePermission(loginUser, 15)) {
            return R.error("您没有该权限!!!");
        }

        categoryService.deleteCategory(categoryId);

        return R.success("删除种类成功");
    }
}
