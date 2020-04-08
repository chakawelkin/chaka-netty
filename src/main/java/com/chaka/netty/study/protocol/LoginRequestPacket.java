package com.chaka.netty.study.protocol;

import lombok.Data;

/**
 * 登录对象
 */

@Data
public class LoginRequestPacket extends Packet{

    private String userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() { // 标识这个对象是用于登录请求
        return Command.LOGIN_REQUEST;
    }

}