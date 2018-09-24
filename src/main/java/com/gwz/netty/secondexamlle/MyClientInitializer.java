package com.gwz.netty.secondexamlle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyClientInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("LengthFieldBasedFrameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
                .addLast("LengthFieldPrepender",new LengthFieldPrepender(4))
                .addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8))
                .addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8))
                .addLast("MyClientHandler",new MyClientHandler());
    }
}
