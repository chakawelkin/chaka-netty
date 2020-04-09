package com.chaka.netty.study;

import com.chaka.netty.study.codec.PacketCodec;
import com.chaka.netty.study.handler.ClientHandler;
import com.chaka.netty.study.protocol.MessageRequestPacket;
import com.chaka.netty.study.utils.LoginUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args){
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

        // 客户端开始建立连接
        bootstrap.connect("127.0.0.1", 8000).addListener(future -> {
            if (future.isSuccess()){
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            }else {
                System.out.println("客户端建立连接失败!");
            }
        });
    }

    /**
     * 启动控制台线程
     * @param channel
     */
    private static void startConsoleThread(Channel channel){
        new Thread(() -> {
            // 线程是否被终止
            while (!Thread.interrupted()){
                if (LoginUtils.isLogin(channel)) {
                    System.out.println("输入消息发送至服务端:");
                    Scanner scanner = new Scanner(System.in);
                    String message = scanner.nextLine();
                    //发送消息
                    MessageRequestPacket requestPacket = new MessageRequestPacket();
                    requestPacket.setMessage(message);
                    ByteBuf encode = PacketCodec.INSTANCE.encode(channel.alloc(), requestPacket);
                    channel.writeAndFlush(encode);
                }
            }
        }).start();
    }

}