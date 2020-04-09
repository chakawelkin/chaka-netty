package com.chaka.netty.study;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                    }
                });
        bootstrap.connect("127.0.0.1", 8080).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
                byteBuf.writeBytes("hello world".getBytes(Charset.forName("UTF-8")));
                ((ChannelFuture)future).channel().writeAndFlush(byteBuf);
            }
        });

    }
}