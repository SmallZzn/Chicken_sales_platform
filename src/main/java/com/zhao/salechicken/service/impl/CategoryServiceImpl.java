package com.zhao.salechicken.service.impl;


import com.zhao.salechicken.mapper.CategoryMapper;
import com.zhao.salechicken.pojo.Category;
import com.zhao.salechicken.service.CategoryService;
import com.zhao.salechicken.util.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.zhao.salechicken.util.RedisConstants.*;

/**
 * @author 86180
 * @description 针对表【category】的数据库操作Service实现
 * @createDate 2023-03-17 17:27:56
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CacheClient cacheClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Category> selectAllCategory() {
        List<Category> categoryList = cacheClient.queryAllWithPassThrough(CACHE_CATEGORY_KEY, Category.class, () -> categoryMapper.selectAllCategory(), CACHE_CATEGORY_TTL, TimeUnit.MINUTES);
        return categoryList;
    }

    @Override
    public Integer selectCategoryCount() {
        return categoryMapper.selectCategoryCount();
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
        //删除缓存
        stringRedisTemplate.delete(CACHE_CATEGORY_KEY);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryMapper.deleteCategory(categoryId);
        //删除缓存
        stringRedisTemplate.delete(CACHE_CATEGORY_KEY);
    }
}




