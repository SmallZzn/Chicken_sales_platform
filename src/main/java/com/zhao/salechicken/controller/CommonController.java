package com.zhao.salechicken.controller;


import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.LoginFormDTO;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     */
    @PostMapping("/code")
    public R sendCode(@RequestParam("phone") String phone, HttpSession session) {
        //发送短信验证码并保存验证码
        return userService.sendCode(phone, session);
    }

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
        session.getServletContext().setAttribute("loginUser", loginUser.getUserId());

        log.info("登录用户的id:{}", loginUser.getUserId());
        return R.success(loginUser);
    }


    /**
     * 优化
     * 登录(通过手机号和验证码)
     *
     * @param request
     * @return
     */
    @PostMapping("/loginByCode")
    public R<User> loginByCode(HttpServletRequest request, @RequestBody LoginFormDTO loginFormDTO) {
        return userService.loginByCode(loginFormDTO);
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
