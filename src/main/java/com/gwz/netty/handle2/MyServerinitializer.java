package com.gwz.netty.handle2;

import com.gwz.netty.handle.MyByteToMessageDecoder2;
import com.gwz.netty.handle.MyLongToByteEncoder;
import com.gwz.netty.handle.MyLongToStringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerinitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyServerhandler",new MyServerhandler());

    }
}
