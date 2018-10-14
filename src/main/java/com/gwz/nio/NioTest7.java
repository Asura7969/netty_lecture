package com.gwz.nio;

import java.nio.ByteBuffer;

/**
 * 只读 buffer ,我们可以随时将一个普通 buffer调用 asReadOnlyBuffer方法返回一个只读 Buffer
 * 但不能将一个只读 buffer 转化为读写 buffer
 */
public class NioTest7 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        readOnlyBuffer.position(0);
//        readOnlyBuffer.put((byte)2);
    }
}
