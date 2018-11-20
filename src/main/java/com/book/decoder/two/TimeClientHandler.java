package com.book.decoder.two;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimeClientHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[] req;

    private final ByteBuf firestMessage;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        firestMessage = Unpooled.buffer(req.length);
        firestMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;

        System.out.println("Now is:" + body + "; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream:" + cause.getMessage());
        ctx.close();
    }
}
