package com.gwz.netty.handle3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerhandler extends SimpleChannelInboundHandler<PersonProtocol>{

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端接受到的数据：");
        System.out.println("长度:"+ length);
        System.out.println("内容:" + new String(content,Charset.forName("utf-8")));
        System.out.println("服务端接收到的消息数量:" + (this.count++));

        String responesMessage = UUID.randomUUID().toString();
        byte[] bytes = responesMessage.getBytes();
        int reLength = bytes.length;

        PersonProtocol p = new PersonProtocol();
        p.setLength(reLength);
        p.setContent(bytes);

        ctx.channel().writeAndFlush(p);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
