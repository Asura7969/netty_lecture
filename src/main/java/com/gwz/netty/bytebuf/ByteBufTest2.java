package com.gwz.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest2 {
    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf headBuffer = Unpooled.buffer(10);
        ByteBuf direct = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(headBuffer,direct);
        compositeByteBuf.removeComponent(0);

        compositeByteBuf.iterator().forEachRemaining(System.out::println);

    }
}
