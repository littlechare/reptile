package com.advance.reptile.socket;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String userId;

    private String code;

    private Map<String, Object> param;

    private LocalDateTime time;

    private String remark;

    public String toJson(){
        return JSON.toJSONString(this);
    }

}
