package com.zhao.salechicken.controller;

import com.zhao.salechicken.annotation.MyLog;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Permission;
import com.zhao.salechicken.pojo.Permissiondetail;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.PermissionService;
import com.zhao.salechicken.service.PermissiondetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissiondetailController {

    @Autowired
    private PermissiondetailService permissiondetailService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 增加管理员权限
     *
     * @param request
     * @param user
     * @param id
     * @return
     */
    @PostMapping("/addPermission")
    @MyLog(title = "权限模块", content = "增加管理员权限")
    public R<String> addPermission(HttpServletRequest request, @RequestBody User user, Integer id) {
        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判读是否有管理权限
        if (!permissiondetailService.judgePermission(loginUser, 1)) {
            return R.error("您没有该权限!!!");
        }

        permissiondetailService.addPermission(user.getUserId(), id);

        return R.success("权限添加成功");
    }

    /**
     * 删除管理员权限
     *
     * @param request
     * @param permissionId
     * @return
     */
    @PostMapping("/deletePermission")
    @MyLog(title = "权限模块", content = "删除管理员权限")
    public R<String> deletePermission(HttpServletRequest request, @RequestBody User user, Integer permissionId) {
        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判读是否有管理权限
        if (!permissiondetailService.judgePermission(loginUser, 1)) {
            return R.error("您没有该权限!!!");
        }

        permissiondetailService.deletePermission(user.getUserId(), permissionId);

        return R.success("权限删除成功");
    }


    /**
     * 查看管理员有的权限
     *
     * @param request
     * @return
     */
    @GetMapping("/selectProcessPermission")
    @MyLog(title = "权限模块", content = "查看管理员有的权限")
    public R<List> selectProcessPermission(HttpServletRequest request, Integer userId) {
        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判读是否有管理权限
        if (!permissiondetailService.judgePermission(loginUser, 1)) {
            return R.error("您没有该权限!!!");
        }

        List<Integer> permissionIds = permissiondetailService.selectProcessPermission(userId);

        return R.success(permissionIds);
    }

    /**
     * 查看管理员没有的权限
     *
     * @param request
     * @return
     */
    @GetMapping("/selectUnProcessPermission")
    @MyLog(title = "权限模块", content = "查看管理员没有的权限")
    public R<List> selectUnProcessPermission(HttpServletRequest request, Integer userId) {
        //获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //判读是否有管理权限
        if (!permissiondetailService.judgePermission(loginUser, 1)) {
            return R.error("您没有该权限!!!");
        }

        List<Permission> permissions = permissiondetailService.selectUnProcessPermission(userId);

        return R.success(permissions);
    }

    /**
     * 查询所有权限
     *
     * @return
     */
    @GetMapping("/selectAllPermission")
    @MyLog(title = "权限模块", content = "查询所有权限")
    public R<List> selectAllPermission() {
        return R.success(permissionService.selectAllPermission());
    }

    /**
     * 修改管理员权限
     *
     * @return
     */
    @PostMapping("/updatePermission")
    @MyLog(title = "权限模块", content = "修改管理员权限")
    public R<String> updatePermission(@RequestBody List<Permissiondetail> permissiondetails) {
        Integer userId = permissiondetails.get(0).getUserId();
        //清空用户权限
        permissiondetailService.clearAllPermissiondetail(userId);
        permissiondetails.stream().map(item -> {
            permissiondetailService.addPermission(userId, item.getPermissionId());
            return item;
        }).collect(Collectors.toList());
        return R.success("权限修改成功");
    }
}
