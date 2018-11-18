package com.book.two;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;


public class TimeClientHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private final ByteBuf firestMessage;

    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        firestMessage = Unpooled.buffer(req.length);
        firestMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firestMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        String body = new String(bytes, Charset.forName("utf-8"));
        System.out.println("Now is:" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream:" + cause.getMessage());
        ctx.close();
    }
}
