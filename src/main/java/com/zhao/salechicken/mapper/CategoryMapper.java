package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86180
* @description 针对表【category】的数据库操作Mapper
* @createDate 2023-03-17 17:27:56
* @Entity com.zhao.salechicken.pojo.Category
*/
@Mapper
public interface CategoryMapper {
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
    void addCategory(@Param("category") Category category);

    /**
     * 添加产品种类
     * @param categoryId
     */
    void deleteCategory(@Param("categoryId") Integer categoryId);

    /**
     * 根据type查询种类名称
     * @param type
     * @return
     */
    String selectNameByType(@Param("type") Integer type);
}




