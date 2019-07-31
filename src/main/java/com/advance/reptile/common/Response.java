package com.advance.reptile.common;

import lombok.Data;

/**
 * 统一消息响应返回类
 * @author advance
 */
@Data
public class Response<T> {

    private int code;   //返回码 非0即失败
    private String msg; //消息提示
    private T data; //返回的数据

    public Response(){};

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //不返回数据的成功方法
    public static Response success() {
        return new Response(CodeMsg.SUCCCESS.getCode(), CodeMsg.SUCCCESS.getMsg(), null);
    }

    //返回数据的成功方法
    public static Response success(Object data ) {
        return new Response(CodeMsg.SUCCCESS.getCode(), CodeMsg.SUCCCESS.getMsg(), data);
    }

    //不返回错误信息的失败方法
    public static Response failed() {
        return failed(CodeMsg.FAILED.getMsg());
    }

    //返回错误信息的失败方法
    public static Response failed(String msg) {
        return failed(-1, msg);
    }

    //返回错误信息以及错误码的失败方法
    public static Response failed(int code, String msg) {
        return new Response(code, msg, null);
    }

}
