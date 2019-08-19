package com.advance.reptile.socket;

import com.advance.reptile.common.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WebSockerServer
 * @Description TODO 因为WebSocket是类似客户端服务端的形式(采用ws协议)，那么这里的WebSocketServer其实就相当于一个ws协议的Controller
 * @Description TODO 直接@ServerEndpoint("/websocket")@Component启用即可，然后在里面实现@OnOpen,@onClose,@onMessage等方法
 * @Author zhouh
 * @Date 2019/8/19 10:35
 * @Version V1.0
 **/
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    //日志
    Logger logger = Logger.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid="";

    /**
     * 当有ws管道建立连接时，触发的方法
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid){
        this.session = session;
        webSocketSet.add(this);
        this.sid=sid;
    }

    /**
     * 断开链接时，触发此方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
    }

    /**
     * 接收到消息时触发的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid==null) {
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

}
