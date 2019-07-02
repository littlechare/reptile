package com.advance.reptile.common;

/**
 * 定义一个消息码枚举，系统错误在此处定义错误码
 * @author advance
 */
public enum CodeMsg {

    //成功
    SUCCCESS(0,"成功！"),
    //失败
    FAILED(-1,"失败！"),
    //为登陆或者登录失效
    NOT_LOGIN(50001,"未登录！"),

    PARAM_NULL(50002,"参数为空！"),

    ACCOUNT_NOX_EXIT(50003,"账号不存在!"),

    PWD_NOT_CORRECT(50004,"密码错误!"),

    //以下是webSocket消息返回码
    MSG_IN_CHAT(10001,"进入聊天室！"),

    MSG_OUT_CHAT(20002,"离开聊天室！"),

    MSG_GET_USER_LIST(30003,"正在加载上线用户列表！"),

    MSG_GET_MSG(40004,"收到信息！");

    private Integer code;

    private String msg;

    CodeMsg(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
