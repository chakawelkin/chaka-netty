package com.chaka.netty.study;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的IO Server
 *
 * 带来的三个主要问题：
 *      1、带来大量的线程连接
 *      2、线程资源过多导致线程切换效率低下
 *      3、数据的读写都已字节为单位
 */
public class IOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            //1.阻塞方式获取新的连接
            Socket socket = serverSocket.accept();//这里会一直阻塞
            System.out.println("接收到一个连接!");
            //2.每个连接创建一个线程来进行处理请求
            new Thread(() -> {
                byte[] data = new byte[1024];//1kb
                int len;
                try {
                    InputStream inputStream = socket.getInputStream();
                    while ((len = inputStream.read(data)) != -1){
                        System.out.println(new String(data,0,len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }

    }
    
}
