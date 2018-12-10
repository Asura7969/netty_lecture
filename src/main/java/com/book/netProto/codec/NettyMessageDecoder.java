package com.book.netProto.codec;

import com.book.netProto.struct.Header;
import com.book.netProto.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * LengthFieldBasedFrameDecoder 是为了解决 拆包粘包等问题的
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        this.marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //1 调用父类(LengthFieldBasedFrameDecoder)方法:
        ByteBuf frame  = (ByteBuf)super.decode(ctx, in);

        if(frame == null){
            return null;
        }

        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());		//crcCode ----> 添加通信标记认证逻辑
        header.setLength(frame.readInt());		//length
        header.setSessionID(frame.readLong());	//sessionID
        header.setType(frame.readByte());		//type
        header.setProiority(frame.readByte());	//priority

        int size = frame.readInt();
        //附件个数大于0，则需要解码操作
        if (size > 0) {
            Map<String, Object> attch = new HashMap<>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                attch.put(key, marshallingDecoder.decode(frame));
            }
            keyArray = null;
            key = null;
            //解码完成放入attachment
            header.setAttachment(attch);
        }

        message.setHeader(header);
        //对于ByteBuf来说，读一个数据，就会少一个数据，所以读完header，剩下的应该就是body了
        if(frame.readableBytes() > 4) { //大于4个字节，肯定就有数据了（4个字节是内容长度的占位）
            message.setBody(marshallingDecoder.decode(frame));
        }
        return message;
    }
}
