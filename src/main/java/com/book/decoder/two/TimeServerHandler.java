package com.book.decoder.two;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class TimeServerHandler extends SimpleChannelInboundHandler{

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("The Time Server receive order:" + body + "; the counter is : " + ++counter);
        String currentTime =
                "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
