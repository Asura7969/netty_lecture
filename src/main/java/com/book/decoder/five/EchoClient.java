package com.book.decoder.five;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(1024,delimiter))
                                    .addLast(new StringDecoder())
                                    .addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture future = b.connect("127.0.0.1", 8899).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static class EchoClientHandler extends SimpleChannelInboundHandler {

        private int counter;
        static final String ECHO_REQ = "Hi,Asura7969.Welcome to Netty.$_";
        public EchoClientHandler(){

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 10; i++) {
                ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
            }
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("This is " + ++counter + "time receive server : [" + msg + "]");
        }


        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
