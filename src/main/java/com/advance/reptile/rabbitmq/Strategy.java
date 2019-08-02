package com.advance.reptile.rabbitmq;

import java.util.Map;

public interface Strategy {

    public void doJob(Map<String, Object> params) throws Exception;

}
