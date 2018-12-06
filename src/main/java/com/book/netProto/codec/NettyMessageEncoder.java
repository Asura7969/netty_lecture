package com.book.netProto.codec;

import com.book.netProto.struct.Header;
import com.book.netProto.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

public final class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    private MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        if(null == msg || null == msg.getHeader()){
            throw new Exception("编码失效,没有数据信息!");
        }

        Header header = msg.getHeader();
        //校验码
        out.writeInt(header.getCrcCode());
        //总长度
        out.writeInt(header.getLength());
        //会话id
        out.writeLong(header.getSessionID());
        //消息类型
        out.writeByte(header.getType());
        //优先级
        out.writeByte(header.getProiority());

        //对附件信息进行编码
        //编码规则为：如果attachment的长度为0，表示没有可选附件，则将长度	编码设置为0
        //如果attachment长度大于0，则需要编码，规则：
        //首先对附件的个数进行编码
        //附件大小
        out.writeInt((header.getAttachment().size()));
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        //然后对key进行编码，先编码长度，然后再将它转化为byte数组之后编码内容
        for (Map.Entry<String, Object> param : header.getAttachment()
                .entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            //key的字符编码长度
            out.writeInt(keyArray.length);
            out.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(value, out);
        }
        key = null;
        keyArray = null;
        value = null;

        //Body:
        Object body = msg.getBody();
        //如果不为空 说明: 有数据
        if(body != null){
            //使用MarshallingEncoder
            this.marshallingEncoder.encode(body, out);
        } else {
            //如果没有数据 则进行补位 为了方便后续的 decoder操作
            out.writeInt(0);
        }

        //最后我们要获取整个数据包的总长度 也就是 header +  body 进行对 header length的设置

        // TODO:  解释： 在这里必须要-8个字节 ，是因为要把CRC和长度本身占的减掉了
        //（官方中给出的是：LengthFieldBasedFrameDecoder中的lengthFieldOffset+lengthFieldLength）
        //总长度是在header协议的第二个标记字段中
        //第一个参数是长度属性的索引位置
        out.setInt(4, out.readableBytes() - 8);
    }
}
