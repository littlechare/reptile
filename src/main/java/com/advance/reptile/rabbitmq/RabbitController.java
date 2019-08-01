package com.advance.reptile.rabbitmq;

import com.advance.reptile.common.Response;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class RabbitController {

    @Autowired
    private MqSender mqSender;

    @RequestMapping("/send")
    public Response sendMessage(String msg){
        ScrpyNoticMessage scrpyNoticMessage = new ScrpyNoticMessage();
        scrpyNoticMessage.setBaseUrl(msg);
        scrpyNoticMessage.setBookName("测试");
        scrpyNoticMessage.setStartUrl("545152151");
        mqSender.send(JSON.toJSONString(scrpyNoticMessage));
        return Response.success();
    }

}
