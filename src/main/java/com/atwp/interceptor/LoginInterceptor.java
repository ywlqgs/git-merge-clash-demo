package com.atwp.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor{

    /**
     * 该方法在所有处理请求的方法之前被自动调用执行
     * 检测全局session对象中是否存在uid,如果有则放行，如果没有则重定向到登录页面
     * @param request
     * @param response
     * @param handler 处理器（url+controller：映射）
     * @return  如果为true则放行 如果为false表示拦截当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取本次请求的URI
        String uri = request.getRequestURI();
        log.info("拦截到请求：{}",uri);

        Object uid = request.getSession().getAttribute("uid");
        if (uid==null){
            log.info("用户未登录！不能放行！");
            response.sendRedirect("/web/login.html");
            //结束后续调用
            return false;
        }

        log.info("本次请求{}放行",uri);
        //请求放行
        return true;
    }
}
