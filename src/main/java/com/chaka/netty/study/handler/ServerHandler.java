package com.chaka.netty.study.handler;

import com.chaka.netty.study.codec.PacketCodec;
import com.chaka.netty.study.protocol.LoginRequestPacket;
import com.chaka.netty.study.protocol.LoginResponsePacket;
import com.chaka.netty.study.protocol.MessageRequestPacket;
import com.chaka.netty.study.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理器
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket){
            System.out.println("--- 收到客户端登录请求 ---");
            LoginRequestPacket requestPacket = (LoginRequestPacket)packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            if (valid(requestPacket)){
                responsePacket.setSuccess(true);
                responsePacket.setMsg("密码验证通过");
                ctx.channel().attr(Attributes.HAS_LOGIN).set(true);
            }else {
                responsePacket.setSuccess(false);
                responsePacket.setMsg("账号或密码错误");
            }

            ByteBuf buf = PacketCodec.INSTANCE.encode(ctx.alloc(),responsePacket);
            ctx.channel().writeAndFlush(buf);
        }

        if (packet instanceof MessageRequestPacket){
            MessageRequestPacket requestPacket = (MessageRequestPacket)packet;
            System.out.printf("收到客户端消息:%s\n",requestPacket.getMessage());
        }

    }

    private boolean valid(LoginRequestPacket requestPacket){
        return true;
    }

}