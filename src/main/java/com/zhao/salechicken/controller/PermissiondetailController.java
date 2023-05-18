package com.zhao.salechicken.controller;

import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Permission;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.PermissionService;
import com.zhao.salechicken.service.PermissiondetailService;
import com.zhao.salechicken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @param ids
     * @return
     */
    @PostMapping("/addPermission")
    public R<String> addPermission(HttpServletRequest request, @RequestBody User user, List<Integer> ids) {
        //获取当前登录用户
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        //判读是否有管理权限
        if (!permissiondetailService.judgePermission(loginUser, 1)) {
            return R.error("您没有该权限!!!");
        }

        permissiondetailService.addPermission(user.getUserId(), ids);

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
    public R<String> deletePermission(HttpServletRequest request, @RequestBody User user, Integer permissionId) {
        //获取当前登录用户
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

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
    public R<List> selectProcessPermission(HttpServletRequest request, Integer userId) {
        //获取当前登录用户
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        //判读是否有管理权限
        if (!permissiondetailService.judgePermission(loginUser, 1)) {
            return R.error("您没有该权限!!!");
        }

        List<Permission> permissions = permissiondetailService.selectProcessPermission(userId);

        return R.success(permissions);
    }

    /**
     * 查看管理员没有的权限
     *
     * @param request
     * @return
     */
    @GetMapping("/selectUnProcessPermission")
    public R<List> selectUnProcessPermission(HttpServletRequest request, Integer userId) {
        //获取当前登录用户
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

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
    public R<List> selectAllPermission() {
        return R.success(permissionService.selectAllPermission());
    }

    /**
     * 修改管理员权限
     *
     * @return
     */
    @PostMapping("/updatePermission")
    public R<String> updatePermission(Integer userId, @RequestParam List<Integer> ids) {
        permissiondetailService.updatePermission(userId, ids);
        return R.success("权限修改成功");
    }
}
