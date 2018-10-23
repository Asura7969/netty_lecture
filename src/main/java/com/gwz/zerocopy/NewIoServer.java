package com.gwz.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIoServer {

    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(8899);
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = socketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel channel = socketChannel.accept();
            channel.configureBlocking(true);

            int readCount = 0;
            while (-1 != readCount) {
                try {
                    readCount = channel.read(byteBuffer);
                }catch (Exception e) {
                    e.printStackTrace();
                }

                byteBuffer.rewind();
            }
        }
    }

}
