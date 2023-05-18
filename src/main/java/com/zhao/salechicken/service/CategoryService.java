package com.zhao.salechicken.service;


import com.zhao.salechicken.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86180
* @description 针对表【category】的数据库操作Service
* @createDate 2023-03-17 17:27:56
*/
public interface CategoryService  {
    /**
     * 查看所有商品种类
     * @return
     */
    List<Category> selectAllCategory();

    /**
     * 查询总类总数
     * @return
     */
    Integer selectCategoryCount();

    /**
     * 添加产品种类
     * @param category
     */
    void addCategory(Category category);

    /**
     * 添加产品种类
     * @param categoryId
     */
    void deleteCategory(Integer categoryId);
}
