package com.chaka.netty.study.protocol;

import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private Boolean success;

    private String msg;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}