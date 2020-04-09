package com.chaka.netty.study.protocol;

import lombok.Data;

/**
 * 消息传输体
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

}