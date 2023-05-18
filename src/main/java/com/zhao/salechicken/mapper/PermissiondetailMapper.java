package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.Permissiondetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86180
* @description 针对表【permissiondetail】的数据库操作Mapper
* @createDate 2023-04-12 17:07:27
* @Entity com.zhao.salechicken.pojo.Permissiondetail
*/
@Mapper
public interface PermissiondetailMapper {

    /**
     * 判断用户是否具有某个权限
     * @param userId
     * @param permissionId
     * @return
     */
    boolean judgePermission(@Param("userId") Integer userId,@Param("permissionId") Integer permissionId);

    /**
     * 增加用户权限
     * @param userId
     * @param ids
     */
    void addPermission(@Param("userId") Integer userId,@Param("ids") List<Integer> ids);

    /**
     * 删除用户权限
     * @param userId
     * @param permissionId
     */
    void deletePermission(@Param("userId") Integer userId,@Param("permissionId") Integer permissionId);

    /**
     * 查询用户有的权限
     * @param userId
     * @return
     */
    List<Integer> selectProcessPermission(@Param("userId") Integer userId);

    /**
     * 查询用户没有的权限
     * @param userId
     * @return
     */
    List<Integer> selectUnProcessPermission(@Param("userId") Integer userId);

    /**
     * 清空所有权限
     * @return
     */
    void clearAllPermissiondetail(@Param("userId") Integer userId);
}




