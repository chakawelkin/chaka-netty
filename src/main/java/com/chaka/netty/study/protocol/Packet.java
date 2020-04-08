package com.chaka.netty.study.protocol;

import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议的版本号
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();

}