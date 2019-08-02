package com.advance.reptile.rabbitmq;

import com.advance.reptile.common.Logger;
import com.advance.reptile.redis.RedisService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@RabbitListener(queues = QueueContent.MESSAGE_QUEUE_NAME)
public class MessageConsumer {

    static Logger logger = Logger.getLogger(MessageConsumer.class);

    @Autowired
    private RedisService redisService;

    @RabbitHandler
    public void handler(String msg, Channel channel, Message message) throws IOException {
        if (!StringUtils.isEmpty(msg)) {
            MessagePojo messagePojo = redisService.stringToBean(msg, MessagePojo.class);
            Action action = Action.RETRY;
            try {
                //这里使用策略模式和springboot的结合使用，
                Strategy s =  (Strategy)SpringContextUtil.getBean(messagePojo.getClassName());
                s.doJob(messagePojo.getParams());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                action = Action.ACCEPT;
                logger.info("收到消息"+messagePojo.toString());
            } catch (Exception e) {
                logger.error("确认消费异常",e);
                //记录下这条消息
                redisService.set("failedMsg"+messagePojo.getMessageId(),msg, 3600*24);
                action = Action.RETRY;
                e.printStackTrace();
            }finally {
                // 通过finally块来保证Ack/Nack会且只会执行一次
                if (action == Action.ACCEPT) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                } else if (action == Action.RETRY) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } else {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                }

            }

        }
    }

}