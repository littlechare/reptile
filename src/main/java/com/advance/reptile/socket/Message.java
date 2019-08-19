package com.advance.reptile.socket;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName Message
 * @Description TODO webSocket发送消息实体
 * @Author zhouh
 * @Date 2019/8/19 11:38
 * @Version V1.0
 **/
@Data
public class Message {

    private String id;

    private String code;

    private Map<String, Object> param;

    private LocalDateTime time;

    private String remark;

}
