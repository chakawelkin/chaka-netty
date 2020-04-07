package com.chaka.netty.study.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 服务端处理器
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + " 收到客户端连接!");

        ctx.channel().writeAndFlush(getByteBuf(ctx,"服务端收到客户端连接"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        ByteBuf outBuffer = getByteBuf(ctx,"欢迎来到chaka的博客");

        ctx.channel().writeAndFlush(outBuffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx,String msg){
        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(msg.getBytes(Charset.forName("UTF-8")));

        return buffer;
    }

}