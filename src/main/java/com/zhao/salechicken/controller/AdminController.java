package com.zhao.salechicken.controller;

import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.annotation.MyLog;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.UserDto;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.PermissiondetailService;
import com.zhao.salechicken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    /**
     * 添加管理员
     *
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/addAdmin")
    @MyLog(title = "管理员模块", content = "添加管理员")
    public R<String> addAdmin(HttpServletRequest request, @RequestBody User user) {

        //1、获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //2、判断是否具有添加管理员的权限
        if (!permissiondetailService.judgePermission(loginUser,2)) {
            return R.error("您没有该权限!!!");
        }

        //3、判断要添加的名称是否已经存在
        User add = userService.getUserByName(user.getUserName());
        //存在则添加失败
        if (add != null) {
            return R.error("名称已存在!!!");
        }

        //不存在则添加
        //类型设置为管理员
        user.setType(1);
        userService.addUser(user);
        return R.success("添加成功!!!");
    }

    /**
     * 修改管理员信息
     *
     * @param user
     * @return
     */
    @PutMapping("/updateAdmin")
    @MyLog(title = "管理员模块", content = "修改管理员信息")
    public R<String> updateAdmin(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 删除管理员
     *
     * @return
     * @Paeam user
     */
    @DeleteMapping("/deleteAdmin")
    @MyLog(title = "管理员模块", content = "删除管理员")
    public R<String> deleteAdmin(Integer userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 查看所有用户/管理员
     *
     * @param request
     * @param page
     * @param pageSize
     * @param type
     * @param keywords
     * @return
     */
    @GetMapping("/selectUser")
    @MyLog(title = "管理员模块", content = "查看所有用户/管理员")
    public R<PageInfo> selectUser(HttpServletRequest request, int page, int pageSize, Integer type, String keywords) {
        //1、获取当前登录用户
        Integer loginUser = BaseContext.getCurrentId();

        //若查询的是管理员列表，则判断是否具有查看管理员列表的权限
        if (type == 1) {
            if (!permissiondetailService.judgePermission(loginUser,5)) {
                return R.error("您没有该权限!!!");
            }
        }
        //若查询的是用户列表，则判断是否具有查看用户列表的权限
        if (type == 0) {
            if (!permissiondetailService.judgePermission(loginUser,12)) {
                return R.error("您没有该权限!!!");
            }
        }
        PageInfo<UserDto> pageInfo = userService.selectUser(page, pageSize, type, keywords);
        return R.success(pageInfo);
    }
}
