package com.chaka.netty.test;

import com.chaka.netty.study.codec.PacketCodec;
import com.chaka.netty.study.protocol.LoginRequestPacket;
import com.chaka.netty.study.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * 协议编解码测试
 */
public class PacketCodecTest {

    @Test
    public void testEncode(){
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserName("chaka");
        requestPacket.setPassword("pwd");
        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ByteBufAllocator.DEFAULT, requestPacket);
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        Assert.assertEquals(requestPacket,packet);
    }

}
