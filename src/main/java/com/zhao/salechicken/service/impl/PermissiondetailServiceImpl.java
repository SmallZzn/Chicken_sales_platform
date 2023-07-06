package com.zhao.salechicken.service.impl;


import com.zhao.salechicken.mapper.PermissiondetailMapper;
import com.zhao.salechicken.pojo.Permission;
import com.zhao.salechicken.service.PermissionService;
import com.zhao.salechicken.service.PermissiondetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 86180
 * @description 针对表【permissiondetail】的数据库操作Service实现
 * @createDate 2023-04-12 17:07:27
 */
@Service
public class PermissiondetailServiceImpl implements PermissiondetailService {

    @Autowired
    private PermissiondetailMapper permissiondetailMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean judgePermission(Integer userId, Integer permissionId) {
        return permissiondetailMapper.judgePermission(userId, permissionId);
    }

    @Override
    public void addPermission(Integer userId, Integer id) {
        permissiondetailMapper.addPermission(userId, id);
    }

    @Override
    public void deletePermission(Integer userId, Integer permissionId) {
        permissiondetailMapper.deletePermission(userId, permissionId);
    }

    @Override
    public List<Integer> selectProcessPermission(Integer userId) {
        //查找用户具有的权限的id
        List<Integer> permissionIds = permissiondetailMapper.selectProcessPermission(userId);

        return permissionIds;
    }

    @Override
    public List<Permission> selectUnProcessPermission(Integer userId) {
        //查找用户不具有的权限的id
        List<Integer> permissionIds = permissiondetailMapper.selectUnProcessPermission(userId);

        if (permissionIds.size() == 0) {
            return null;
        }

        List<Permission> permissions = permissionService.selectPermission(permissionIds);

        return permissions;
    }

    @Override
    public void clearAllPermissiondetail(Integer userId) {
        permissiondetailMapper.clearAllPermissiondetail(userId);
    }
}




