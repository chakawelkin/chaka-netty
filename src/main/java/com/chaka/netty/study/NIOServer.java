package com.chaka.netty.study;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于JDK实现的NIO服务
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        //注册连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){

            if (selector.select(1) > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    //1.监测一个新连接
                    if (key.isAcceptable()){
                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }

                    //2.监测到可读事件
                    if (key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer)
                                .toString());
                        key.interestOps(SelectionKey.OP_READ);
                    }
                    iterator.remove();
                }
            };

        }

    }

}