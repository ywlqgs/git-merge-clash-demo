package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.controller.ex.*;
import com.atwp.service.ex.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sun.security.util.Password;

import javax.servlet.http.HttpSession;

/**
 * 控制层类的基类 主要用于异常的捕获处理
 */
@Slf4j
public class BaseController {

    //操作成功的状态码
    public static final int OK=200;

    //请求处理方法，这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当前项目中产生了异常，被统一拦截到此方法中，此方法此时就充当的是请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler({ServiceException.class,FileUploadException.class}) //异常拦截器 此注解一般是用来自定义异常
    public JsonResult<Void> handleException(Throwable e){

        log.info("handleException方法开始调用~");

        JsonResult<Void> result = new JsonResult<>();
        //instanceof作用是测试它左边的对象是否是它右边的类的实例，返回 boolean 的数据类型
        if (e instanceof UserNameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已被占用~");
        }else if (e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在！");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("密码输入错误！");
        }else if (e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("用户收货地址超出上限！！");
        }else if (e instanceof AddressNotFoundException){
            result.setState(4004);
            result.setMessage("用户收货地址不存在！！");
        }else if (e instanceof AccessDeniedException){
            result.setState(4005);
            result.setMessage("收货地址数据非法访问异常！！");
        } else if (e instanceof ProductNotFoundException){
            result.setState(4006);
            result.setMessage("尝试访问的商品数据不存在！");
        }else if (e instanceof CartNotFoundException){
            result.setState(4007);
            result.setMessage("购物车数据不存在异常！");
        }else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常！");
        } else if (e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("更新数据时产生未知的异常！");
        }else if (e instanceof DeleteException){
            result.setState(5002);
            result.setMessage("删除数据时产生未知的异常！");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }

        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session
     * @return
     */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid")
                                        .toString());

    }

    /**
     * 获取当前对象的username
     * @param session
     * @return
     */
    protected final String getUserNameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
