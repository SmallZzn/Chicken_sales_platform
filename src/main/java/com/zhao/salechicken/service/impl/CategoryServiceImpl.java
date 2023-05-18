package com.zhao.salechicken.service.impl;


import com.zhao.salechicken.mapper.CategoryMapper;
import com.zhao.salechicken.pojo.Category;
import com.zhao.salechicken.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【category】的数据库操作Service实现
 * @createDate 2023-03-17 17:27:56
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectAllCategory() {
        return categoryMapper.selectAllCategory();
    }

    @Override
    public Integer selectCategoryCount() {
        return categoryMapper.selectCategoryCount();
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryMapper.deleteCategory(categoryId);
    }
}




