package com.advance.reptile.rabbitmq.worker;

import com.advance.reptile.common.Logger;
import com.advance.reptile.rabbitmq.service.IScrpySimple;
import com.advance.reptile.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName ScrpySimpleMethod
 * @Description TODO 简单爬取小说方法
 * @Author zhouh
 * @Date 2019/8/6 8:56
 * @Version V1.0
 **/
@Component(value = "scrpySimpleMethod")
public class ScrpySimpleMethod implements Strategy {

    Logger logger = Logger.getLogger(ScrpySimpleMethod.class);

    @Autowired
    private IScrpySimple scrpySimple;

    @Override
    public void doJob(Map<String, Object> params) throws Exception {
        scrpySimple.scrpyBook(params);
    }
}
