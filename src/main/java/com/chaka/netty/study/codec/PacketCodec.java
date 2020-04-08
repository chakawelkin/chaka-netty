package com.chaka.netty.study.codec;

import com.chaka.netty.study.protocol.Command;
import com.chaka.netty.study.protocol.LoginRequestPacket;
import com.chaka.netty.study.protocol.LoginResponsePacket;
import com.chaka.netty.study.protocol.Packet;
import com.chaka.netty.study.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * 编解码工具
 */
public class PacketCodec {

    public static PacketCodec INSTANCE = new PacketCodec();

    private static final int MAGIC_NUMBER = 0x12345678;

    private Map<Byte,Class<? extends Packet>> packetTypeMap;

    public PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST,LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet){
        //1.创建byteBuf对象 (preferably a direct buffer which is suitable for I/O)
        ByteBuf buffer = byteBufAllocator.ioBuffer();

//        buffer = ByteBufAllocator.DEFAULT.ioBuffer(); 一样可行

        //2.序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        buffer.writeInt(MAGIC_NUMBER);
        buffer.writeByte(packet.getVersion());
        buffer.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buffer.writeByte(packet.getCommand());
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);

        return buffer;
    }

    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);//跳过magic number，实际是可以用来校验一下的
        byteBuf.skipBytes(1);//跳过版本号
        byte serializerAlgorithm = byteBuf.readByte();//读取序列化算法
        byte command = byteBuf.readByte();//指令
        int length = byteBuf.readInt();//数据包长度

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        return Serializer.DEFAULT.deserialize(packetTypeMap.get(command), bytes);
    }

}
