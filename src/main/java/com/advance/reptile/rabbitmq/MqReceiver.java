package com.advance.reptile.rabbitmq;

import com.advance.reptile.common.Logger;
import com.advance.reptile.redis.RedisService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MqConfig.QUEUE_A)
public class MqReceiver {

    Logger logger = Logger.getLogger(MqReceiver.class);

    @Autowired
    private RedisService redisService;

    @RabbitHandler
    public void processC(String msg) {
        try{
            logger.info("收到消息----------》"+msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
