package com.gwz.netty.handle3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol>{

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        byte[] content = msg.getContent();
        int length = msg.getLength();

        System.out.println("客户端接收到的消息:");
        System.out.println("长度:" + length);
        System.out.println("内容:" + new String(content,Charset.forName("utf-8")));

        System.out.println("客户端接收到的消息数量:" + (this.count++));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String messageToBeSent = "send from client";
            byte[] content = messageToBeSent.getBytes(Charset.forName("utf-8"));
            int length = content.length;

            PersonProtocol p = new PersonProtocol();
            p.setContent(content);
            p.setLength(length);

            ctx.writeAndFlush(p);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
