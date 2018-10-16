package com.gwz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioTest11 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8899));

        int messageLenth = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel accept = serverSocketChannel.accept();

        while (true){
            int bytesRead = 0;
            while (bytesRead < messageLenth){
                long read = accept.read(buffers);
                bytesRead += read;

                System.out.println("bytesRead:" + bytesRead);

                Arrays.stream(buffers)
                        .map(buffer -> "position:" + buffer.position() + "," + "capacity" + buffer.capacity())
                        .forEach(System.out::println);
            }

            Arrays.stream(buffers).forEach(Buffer::flip);

            int bytesWritten = 0;
            while (bytesWritten < messageLenth){
                long write = accept.write(buffers);
                bytesWritten += write;
            }
            Arrays.stream(buffers).forEach(Buffer::clear);

            System.out.println("byteRead:" + bytesRead + ",bytesWritten:" + bytesWritten + ",messageLenth:" + messageLenth);

        }
    }
}
