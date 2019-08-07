package com.advance.reptile.rabbitmq.service;

import java.util.Map;

/**
 * @ClassName IScrpySimple
 * @Description TODO 简单应用爬取小说数据
 * @Author zhouh
 * @Date 2019/8/6 11:15
 * @Version V1.0
 **/
public interface IScrpySimple {

    /**
     * 小书内容爬取方法
     * @param params 参数
     */
    void scrpyBook(Map<String, Object> params) throws Exception;

    /**
     * 根据填写的基础路径，获取其实路径
     * @param baseUrl 基础路径
     * @return
     */
    String getStartUrl(String baseUrl) throws Exception;

}
