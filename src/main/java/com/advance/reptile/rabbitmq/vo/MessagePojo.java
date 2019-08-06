package com.advance.reptile.rabbitmq.vo;

import lombok.Data;

import java.util.Map;

@Data
public class MessagePojo {

    //定时过期时间（单位：秒）马上消费,设置为0
    private int delay;

    //处理类名（必填项）
    private String className;

    //消息参数
    private Map<String, Object> params;

    private String createTime;

    private String messageId;

    public MessagePojo() {

    }

    public MessagePojo(int delay, String className,Map<String, Object> params) {
        this.delay = delay;
        this.className = className;
        this.params = params;
    }

}
