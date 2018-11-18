package com.gwz.netty.handle;

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
//        pipeline.addLast("LongDecoder",new MyByteToMessageDecoder())
        pipeline.addLast("LongDecoder",new MyByteToMessageDecoder2())
                .addLast("LongEncoder",new MyLongToByteEncoder())
                .addLast("MyClientHandler",new MyClientHandler());
    }
}
