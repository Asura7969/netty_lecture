package com.book.netProto.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * 根据编码规则去解码
 */
public class MarshallingDecoder {

    private Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        this.unmarshaller = MarshallingCodeCFactory.buildUnMarshalling();
    }

    public Object decode(ByteBuf in) throws Exception {
        try {
            //首先读取4个长度(实际body内容的长度)
            int bodySize = in.readInt();
            //获取实际body的缓冲内容
            int readerIndex = in.readerIndex();
            ByteBuf buf = in.slice(readerIndex, bodySize);
            //转化
            ChannelBufferByteInput input = new ChannelBufferByteInput(buf);
            //读取操作
            this.unmarshaller.start(input);
            Object rec = this.unmarshaller.readObject();
            this.unmarshaller.finish();
            //读取完毕以后,更新当前读取的起始位置
            //因为使用slice方法，原buf的位置还在readIndex上，故需要将位置重新设置一下
            in.readerIndex(in.readerIndex() + bodySize);

            return rec;
        } finally {
            this.unmarshaller.close();
        }
    }
}
