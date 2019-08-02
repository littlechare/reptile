package com.advance.reptile.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于处理消息的方法A
 */
@Component("StrategyA")
public class StrategyA  implements Strategy{

    /**
     *
     * @param params 接口所需参数
     */
    @Override
    public void doJob(Map<String, Object> params) {
        System.out.println("用A方法处理");
    }

}
