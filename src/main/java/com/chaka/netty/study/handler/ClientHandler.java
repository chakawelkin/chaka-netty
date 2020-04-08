package com.chaka.netty.study.handler;

import com.alibaba.fastjson.JSON;
import com.chaka.netty.study.codec.PacketCodec;
import com.chaka.netty.study.protocol.LoginRequestPacket;
import com.chaka.netty.study.protocol.LoginResponsePacket;
import com.chaka.netty.study.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * 客户端
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--- 客户端建立连接成功，开始登录 ---");
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUserName("chaka");
        requestPacket.setPassword("123456");

        ByteBuf buffer = PacketCodec.INSTANCE.encode(ctx.alloc(),requestPacket);
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket responsePacket = (LoginResponsePacket)packet;
            System.out.println(JSON.toJSONString(responsePacket));
        }
    }
}