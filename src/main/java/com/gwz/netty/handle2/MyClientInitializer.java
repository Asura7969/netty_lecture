package com.gwz.netty.handle2;

import com.gwz.netty.handle.MyByteToMessageDecoder2;
import com.gwz.netty.handle.MyLongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyClientHandler",new MyClientHandler());
    }
}
