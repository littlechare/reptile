package com.advance.reptile.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author advance
 */
public class SocketChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());

        /***   心跳检测 10秒检测一次       **/
        pipeline.addLast(new IdleStateHandler(0,10,0));

        //粘包拆包处理
        ByteBuf delimiter = Unpooled.copiedBuffer("&&&".getBytes());
        //解码的帧的最大长度为：2048；解码时是否去掉分隔符：false；解码分隔符：&&&
        pipeline.addLast(new DelimiterBasedFrameDecoder(2048,false,delimiter));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));// WebSocket通信支持
        pipeline.addLast("handler", new SocketHandler());//指定消息处理器
    }
}
