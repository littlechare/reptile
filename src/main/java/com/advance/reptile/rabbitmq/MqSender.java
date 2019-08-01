package com.advance.reptile.rabbitmq;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Logger;
import com.advance.reptile.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MqSender implements RabbitTemplate.ConfirmCallback {

    Logger logger = Logger.getLogger(MqSender.class);

    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private AmqpTemplate template;
    @Autowired
    private RedisService redisService;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    public void send(String mm) {
        logger.info("发送消息------------------》"+mm.toString());
        CorrelationData correlationId = new CorrelationData(CommonUtils.getUuid());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(MqConfig.EXCHANGE_A, MqConfig.ROUTINGKEY_A, mm, correlationId);
    }

    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }
}
