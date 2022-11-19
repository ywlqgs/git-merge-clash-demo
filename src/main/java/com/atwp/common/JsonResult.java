package com.atwp.common;

import java.io.Serializable;

/**
 * Json格式的数据进行响应
 */
public class JsonResult<T> implements Serializable {

    private Integer state;

    private String message;

    private T data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    //声明关于异常的捕获
    public JsonResult(Throwable e) {
        //getMessage(): 方法的返回类型为String ，返回有关异常的描述性消息。
        this.message=e.getMessage();
    }

    public JsonResult(Integer state, T data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
