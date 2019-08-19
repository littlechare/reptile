package com.advance.reptile.socket;

/**
 * 定义一个消息枚举类型，包含聊天消息对应的代码以及种类
 */
public enum MessageEnum {

    SIMPLE_NETTY_HEADER("10001","simple"),
    MASTER_NETTY_HEADER("20002","master");

    MessageEnum(String code, String action){
        this.code = code;
        this.action = action;
    }

    private String code;

    private String action;

    public String getCode(){
        return code;
    }

    public String getAction(){
        return action;
    }

}
