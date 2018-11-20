package com.gwz.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyDataInfo.MyMessage myMessage;

        int random = new Random().nextInt(3);
        switch (random){
            case 0:
                myMessage = MyDataInfo.MyMessage.newBuilder()
                        .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                        .setPerson(MyDataInfo.Person.newBuilder().setName("汪远-屎壳郎").setAge(3).setAddress("北京公厕").build())
                        .build();
                break;
            case 1:
                myMessage = MyDataInfo.MyMessage.newBuilder()
                        .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                        .setDog(MyDataInfo.Dog.newBuilder().setName("汪远-狗").setAge(3).setAddress("西藏公厕").build())
                        .build();
                break;
            default:
                myMessage = MyDataInfo.MyMessage.newBuilder()
                        .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                        .setCat(MyDataInfo.Cat.newBuilder().setName("汪远-猫").setCity("上海公厕").build())
                        .build();
                break;
        }

        ctx.writeAndFlush(myMessage).sync();
    }
}
