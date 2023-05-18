package com.zhao.salechicken.service.impl;

import com.zhao.salechicken.mapper.PermissionMapper;
import com.zhao.salechicken.pojo.Permission;
import com.zhao.salechicken.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86180
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2023-04-12 23:47:53
*/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> selectPermission(List<Integer> permissionIds) {
        return permissionMapper.selectPermission(permissionIds);
    }

    @Override
    public List<Permission> selectAllPermission() {
        return permissionMapper.selectAllPermission();
    }
}




