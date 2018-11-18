package com.gwz.netty.handle3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerinitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline
                .addLast(new MyPersonDecoder())
                .addLast(new MyPersonEncoder())
                .addLast("MyServerhandler",new MyServerhandler());

    }
}
