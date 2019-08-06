package com.advance.reptile.rabbitmq.worker;

import java.util.Map;

public interface Strategy {

    public void doJob(Map<String, Object> params) throws Exception;

}
