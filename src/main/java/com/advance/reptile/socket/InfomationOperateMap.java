package com.advance.reptile.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName InfomationOperateMap
 * @Description TODO 数据操作类，存放用户对应的ChannelHandlerContext
 * @Author zhouh
 * @Date 2019/8/19 16:53
 * @Version V1.0
 **/
public class InfomationOperateMap {

    public static ConcurrentMap<String, ConcurrentMap<String, InfomationOperateMap>> map = new ConcurrentHashMap<>();

    private ChannelHandlerContext ctx;

    private Message mage;

    private InfomationOperateMap(ChannelHandlerContext ctx, Message mage) {
        this.ctx = ctx;
        this.mage = mage;
    }

    public Message getMage() {
        return mage;
    }

    /**
     * 添加用户信息
     * @param ctx
     * @param mage 消息实体
     */
    public static void add(ChannelHandlerContext ctx, Message mage) {
        InfomationOperateMap iom = new InfomationOperateMap(ctx, mage);
        ConcurrentMap<String, InfomationOperateMap> cmap = new ConcurrentHashMap<>();
        //群聊的消息中会存在聊天室名称，用户ID等信息
        String id = mage.getParam().get("id") == null ? "": mage.getParam().get("id").toString();
        String userId = mage.getParam().get("userId") == null ? "" : mage.getParam().get("userId").toString();
        if (map.containsKey(id)) {
            map.get(id).put(userId, iom);
        } else {
            cmap.put(userId, iom);
            map.put(id, cmap);
        }
    }

    /**
     * 给用户发送消息
     * @param mage
     * @throws Exception
     */
    public void send(Message mage) throws Exception{
        ctx.writeAndFlush(new TextWebSocketFrame(mage.toJson()));
    }

}
