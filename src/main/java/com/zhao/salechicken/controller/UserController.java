package com.zhao.salechicken.controller;

import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.UserDto;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.CartService;
import com.zhao.salechicken.service.PermissiondetailService;
import com.zhao.salechicken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/logon")
    public R<String> logon(@RequestBody User user) {

        //类型设置为用户
        user.setType(0);
        //添加用户到数据库
        Integer judge = userService.addUser(user);
        //若用户添加成功，则为用户创建购物车
        if (judge != 0) {
            cartService.createCart(user.getUserId());
        }
        return R.success("注册用户成功!!!");
    }

    /**
     * 用户注销
     *
     * @param request
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteUser")
    public R<String> deleteUser(HttpServletRequest request, Integer userId) {
        //获取当前登录用户
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        //若是管理员进行删除
        if (!loginUser.equals(userId)) {
            //判断是否就有删除用户的权限
            if (!permissiondetailService.judgePermission(loginUser, 13)) {
                return R.error("您没有该权限!!!");
            }
        }

        //从session中删除该用户并返回登录界面
        request.getSession().removeAttribute("loginUser");
        //将用户的购物车删除
        cartService.deleteCart(userId);
        //从数据库中将该用户删除
        userService.deleteUser(userId);
        return R.success("用户注销成功");
    }

    /**
     * 用户修改信息
     *
     * @param request
     * @param user
     * @return
     */
    @PutMapping("/updateUserInfo")
    public R<String> updateUserInfo(HttpServletRequest request, @RequestBody User user) {
        //获取当前登录用户
        Integer loginUser = (Integer) request.getSession().getAttribute("loginUser");

        //TODO 注释掉loginUser
        //若是管理员进行删除
//        if (!loginUser.equals(user.getUserId())) {
//            //判断是否就有删除用户的权限
//            if (!permissiondetailService.judgePermission(loginUser, 13)) {
//                return R.error("您没有该权限!!!");
//            }
//        }

        System.out.println(user.getImage());
        userService.updateUser(user);
        return R.success("修改成功");
    }

    @GetMapping("/selectUserInfo")
    public R<UserDto> selectUserInfo(Integer userId){
        UserDto userDto = userService.selectUserInfo(userId);

        return R.success(userDto);
    }
}
