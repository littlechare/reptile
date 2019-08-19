package com.advance.reptile.socket;

import com.advance.reptile.common.SysConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author advance
 *  netty服务端启动程序
 */
public class NettyService {

    Logger logger = LoggerFactory.getLogger(NettyService.class);

    public NettyService(){
        System.out.println("系统正在启动Netty---------------");
        Long start = System.currentTimeMillis();
        /**
         * 注意：4.x版本的netty开始，引入EventLoopGroup定义，此处需要两个，用于管理Channel
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**
             * 引导类，具体不清楚有待考究
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 2048)//最大连接数 2048
                    .childHandler(new SocketChannelInitializer());
            //创建连接
            //经过查询资料，发现此处需要绑定一个地址，否则的话系统启动netty并不知道指向哪个IP
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", SysConstant.NETTY_PORT);
            ChannelFuture cf = serverBootstrap.bind(address).syncUninterruptibly();
            System.out.println(NettyService.class + " 启动正在监听： " + cf.channel().localAddress());
            if(cf.isSuccess()){
                Long end = System.currentTimeMillis();
                System.err.print("----------------->共耗时"+ (end - start) +"毫秒 ");
                cf.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            //关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
