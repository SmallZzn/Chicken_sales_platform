package com.zhao.salechicken.service;


import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.UserDto;
import com.zhao.salechicken.pojo.Product;
import com.zhao.salechicken.pojo.User;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-03-13 22:06:28
 */
public interface UserService {

    /**
     * 根据id查询用户信息
     *
     * @return
     */
    User getUserById(Integer userId);

    /**
     * 根据用户名查询用户信息
     *
     * @return
     */
    User getUserByName(String userName);

    /**
     * 添加用户
     *
     * @param user
     */
    Integer addUser(User user);

    /**
     * 修改用户信息
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id删除用户
     *
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 查询所有用户
     *
     * @return
     */
    PageInfo selectUser(int page, int pageSize, Integer type, String keywords);

    /**
     * 通过userId获取userName
     *
     * @param userId
     * @return
     */
    String getUserNameById(Integer userId);

    /**
     * 查看用户详细信息
     * @param userId
     * @return
     */
    UserDto selectUserInfo(Integer userId);
}
