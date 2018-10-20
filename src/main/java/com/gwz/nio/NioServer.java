package com.gwz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    public static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);

                            String key = "[" + UUID.randomUUID().toString() + "]";
                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){
                            client = (SocketChannel)selectionKey.channel();

                            ByteBuffer readBuffer = ByteBuffer.allocate(512);
                            readBuffer.clear();

                            int read = client.read(readBuffer);

                            if(read > 0){
                                readBuffer.flip();

                                Charset charset = Charset.forName("utf-8");
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":" + receiveMessage);

                                String sendKey = null;

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if(entry.getValue() == client){
                                        sendKey = entry.getKey();
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    buffer.put((sendKey + ":" + receiveMessage).getBytes());
                                    buffer.flip();
                                    value.write(buffer);
                                }
                            }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                });

                selectionKeys.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
