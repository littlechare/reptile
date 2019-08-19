package com.advance.reptile.socket;

/**
 * 消息枚举类型，定义不同的消息类型
 */
public enum MessageEnum {

    SIMPLE_SCRPY(10001,"简单爬取"),
    MASTER_SCRPY(10001,"高级爬取");

    private Integer code;

    private String msg;

    MessageEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer  getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

}
