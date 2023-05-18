package com.zhao.salechicken.service;


import com.zhao.salechicken.pojo.Permission;

import java.util.List;

/**
* @author 86180
* @description 针对表【permission】的数据库操作Service
* @createDate 2023-04-12 23:47:53
*/
public interface PermissionService {
    /**
     * 根据permissionId查询permissionName
     * @param permissionIds
     * @return
     */
    List<Permission> selectPermission(List<Integer> permissionIds);

    /**
     * 查看所有权限
     * @return
     */
    List<Permission> selectAllPermission();
}
