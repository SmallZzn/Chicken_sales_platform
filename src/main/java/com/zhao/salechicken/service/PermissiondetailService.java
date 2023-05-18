package com.zhao.salechicken.service;


import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【permissiondetail】的数据库操作Service
 * @createDate 2023-04-12 17:07:27
 */
public interface PermissiondetailService {
    /**
     * 判断用户是否具有某个权限
     *
     * @param userId
     * @param permissionId
     * @return
     */
    boolean judgePermission(Integer userId, Integer permissionId);

    /**
     * 增加用户权限
     *
     * @param userId
     * @param ids
     */
    void addPermission(Integer userId, List<Integer> ids);

    /**
     * 删除用户权限
     *
     * @param userId
     * @param permissionId
     */
    void deletePermission(Integer userId, Integer permissionId);

    /**
     * 查询用户有的权限
     *
     * @param userId
     * @return
     */
    List<Permission> selectProcessPermission(Integer userId);

    /**
     * 查询用户没有的权限
     *
     * @param userId
     * @return
     */
    List<Permission> selectUnProcessPermission(Integer userId);

    /**
     * 修改管理员权限
     *
     * @param userId
     * @param ids
     */
    void updatePermission(Integer userId, List<Integer> ids);
}
