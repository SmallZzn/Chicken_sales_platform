package com.zhao.salechicken.controller;


import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        //1、根据页面提交的用户名来查数据库
        User loginUser = userService.getUserByName(user.getUserName());

        //2、如果没有查询到则返回失败结果
        if (loginUser == null) {
            return R.error("登录失败,用户不存在！！！");
        }

        //3、比对密码，如果不一致则返回失败结果
        if (!user.getPassword().equals(loginUser.getPassword())) {
            return R.error("密码有误！！！");
        }

        //4、登录成功，将用户id存入Session并返回成功结果
        HttpSession session = request.getSession();

        session.setAttribute("loginUser", loginUser.getUserId());
        log.info("登录用户的id:{}", loginUser.getUserId());
        return R.success(loginUser);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前用户登录的id
        request.getSession().removeAttribute("loginUser");
        return R.success("退出成功!!!");
    }
}
