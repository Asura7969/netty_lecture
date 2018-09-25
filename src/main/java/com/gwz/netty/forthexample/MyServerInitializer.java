package com.gwz.netty.forthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                //针对空闲处理的 Handler
                .addLast(new IdleStateHandler(5,7,3, TimeUnit.SECONDS))
                .addLast(new MyServerHandler());
    }
}
