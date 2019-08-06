package com.advance.reptile.rabbitmq.controller;

import com.advance.reptile.common.Response;
import com.advance.reptile.rabbitmq.config.MessageProvider;
import com.advance.reptile.rabbitmq.vo.MessagePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mq")
public class RabbitController {

    @Autowired
    private MessageProvider messageProvider;

    @RequestMapping(value = "/send")
    public Response sendMessage(String msg){
        MessagePojo messagePojo = new MessagePojo();
        Map<String, Object> param = new HashMap<>();
        param.put("test","周韩");
        messagePojo.setParams(param);
        messagePojo.setClassName("StrategyA");
        messageProvider.sendMessage(messagePojo);
        return Response.success();
    }

    @RequestMapping(value = "/scrpy/simple", method = RequestMethod.POST)
    public Response scrpyBookSimple(@RequestBody String data){
        MessagePojo messagePojo = new MessagePojo();
        Map<String, Object> param = new HashMap<>();
        param.put("data",data);
        messagePojo.setParams(param);
        messagePojo.setClassName("scrpySimpleMethod");
        messageProvider.sendMessage(messagePojo);
        return Response.success();
    }

    @RequestMapping(value = "/scrpy/master", method = RequestMethod.POST)
    public Response scrpyBookMaster(@RequestBody String data){
        return Response.success();
    }

}
