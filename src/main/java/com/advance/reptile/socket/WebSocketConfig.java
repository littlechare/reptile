package com.advance.reptile.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketConfig
 * @Description TODO 使用websocket进行消息处理的回复，这里是websocket的配置,主要为开启WebSocket支持
 * @Author zhouh
 * @Date 2019/8/19 10:33
 * @Version V1.0
 **/
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
