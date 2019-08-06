package com.advance.reptile.rabbitmq.config;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Logger;
import com.advance.reptile.rabbitmq.vo.MessagePojo;
import com.advance.reptile.rabbitmq.constant.QueueEnum;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MessageProvider implements RabbitTemplate.ConfirmCallback {

    static Logger logger = Logger.getLogger(MessageProvider.class);

    /**
     * RabbitMQ 模版消息实现类
     */
    protected RabbitTemplate rabbitTemplate;

    public MessageProvider(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this);
    }

    private String msgPojoStr;

    /**
     * 发送延迟消息
     * @param messageContent
     */
    public void sendMessage(MessagePojo messageContent) {
        if (messageContent != null){
            //这里用于消费者消费消息的时候处理具体业务
            if (StringUtils.isEmpty(messageContent.getClassName())){
                logger.error("处理业务的类名不能为空");
                return;
            }

            messageContent.setMessageId(UUID.randomUUID().toString());
            messageContent.setCreateTime(CommonUtils.handTimestamp(Timestamp.valueOf(LocalDateTime.now())));
            String msg = JSON.toJSONString(messageContent);
            msgPojoStr = msg;
            logger.info("延迟："+messageContent.getDelay()+"秒写入消息队列："+ QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey()+"，消息内容："+msg);
            // 执行发送消息到指定队列
            CorrelationData correlationData = new CorrelationData(messageContent.getMessageId());
            rabbitTemplate.convertAndSend(QueueEnum.MESSAGE_TTL_QUEUE.getExchange(), QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(), msg, message -> {
                // 设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(messageContent.getDelay()*1000));
                return message;
            },correlationData);
        }else {
            logger.warn("消息内容为空！！！！！");
        }

    }

    /**
     * 发送确认
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(" 回调id:" + correlationData);
        if (b) {
            System.out.println(msgPojoStr+":消息发送成功");
        } else {
            logger.warn(msgPojoStr+":消息发送失败:" + s);
        }
    }

}
