package com.gwz.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest9 {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        buffer.put(0,(byte)'a');
        buffer.put(3,(byte)'b');

        randomAccessFile.close();
    }
}
