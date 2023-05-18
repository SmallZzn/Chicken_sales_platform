package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2023-03-13 22:06:28
 * @Entity com.zhao.salechicken.pojo.User
 */
@Mapper
public interface UserMapper {

    /**
     * 根据id查询用户
     *
     * @return
     */
    User getUserById(@Param("userId") Integer userId);

    /**
     * 根据用户名查询用户
     *
     * @return
     */
    User getUserByName(@Param("userName") String userName);

    /**
     * 添加用户
     *
     * @param user
     */
    Integer addUser(@Param("user") User user);

    /**
     * 修改用户信息
     *
     * @param user
     */
    void updateUser(@Param("user") User user);

    /**
     * 根据id删除用户
     *
     * @param userId
     */
    void deleteUser(@Param("userId") Integer userId);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> selectUser(@Param("type") Integer type, @Param("keywords") String keywords);

    /**
     * 通过uesrId获取productName
     *
     * @param userId
     * @return
     */
    String getUserNameById(@Param("userId") Integer userId);
}




