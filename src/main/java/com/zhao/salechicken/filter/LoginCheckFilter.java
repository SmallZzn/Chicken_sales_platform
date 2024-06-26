package com.zhao.salechicken.filter;

import com.alibaba.fastjson.JSON;
import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.zhao.salechicken.util.RedisConstants.LOGIN_USER_KEY;
import static com.zhao.salechicken.util.RedisConstants.LOGIN_USER_TTL;


/**
 * 过滤器
 * 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //过滤器具体的处理逻辑如下:
        //1、获取本次请求的URI
        String requestUri = request.getRequestURI();

        log.info("拦截到请求：{}", requestUri);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/code",
                "/loginByCode",
                "/product/selectProductBySales",
                "/logout",
                "/file/upload",
                "/file/download",
                "/index.html",
                "/login",
                "/login.html",//登录页面
                "/user/logon",//用户注册
                "/logon.html",
//                "/user/home.html",//用户页面
                "/templates/**",//页面
                "/assets/**",//静态资源
                "/data/**",
                "/image/**",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestUri);

        //3、如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestUri);
            filterChain.doFilter(request, response);
            return;
        }

        //4、判断登录状态，如果已登录，则直接放行
//        Integer loginUser = (Integer) request.getSession().getServletContext().getAttribute("loginUser");
//        if (loginUser != null) {
//            log.info("用户已登录，用户id为：{}", loginUser);
//            BaseContext.setCurrentId(loginUser);
//            filterChain.doFilter(request, response);
//            return;
//        }

        //4、获取用户信息
        String token = "1";//由于不会修改前端代码，无法将token保存在前端，所以将token写死
        String user = stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY + token);

        //5、判断登录状态，如果已登录，则直接放行
        if (user != null) {
            Integer loginUser = Integer.parseInt(user);
            BaseContext.setCurrentId(loginUser);
            filterChain.doFilter(request, response); 
            //刷新token有效期
            stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
            return;
        }

        //5、如果未登录则返回未登录结果,通过输出流向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本请求是否需要放行
     *
     * @param urls
     * @param requestUrl
     * @return
     */
    public boolean check(String[] urls, String requestUrl) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
