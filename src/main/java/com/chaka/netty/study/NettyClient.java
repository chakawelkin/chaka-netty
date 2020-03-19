package com.chaka.netty.study;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
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

        ChannelFuture cf = bootstrap.connect("127.0.0.1", 8080).sync();

        cf.channel().writeAndFlush(new Date() + ": hello world!");
        /*while (true){
            channel.writeAndFlush();
            TimeUnit.SECONDS.sleep(2);
        }*/

        cf.channel().closeFuture().sync();
    }
}