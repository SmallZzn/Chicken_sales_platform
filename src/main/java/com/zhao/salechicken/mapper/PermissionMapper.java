package com.zhao.salechicken.mapper;


import com.zhao.salechicken.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86180
* @description 针对表【permission】的数据库操作Mapper
* @createDate 2023-04-12 23:47:53
* @Entity com.zhao.salechicken.pojo.Permission
*/
@Mapper
public interface PermissionMapper {

    /**
     * 根据permissionIds查询permission
     * @param permissionIds
     * @return
     */
    List<Permission> selectPermission(@Param("permissionIds") List<Integer> permissionIds);

    /**
     * 查看所有权限
     * @return
     */
    List<Permission> selectAllPermission();
}




