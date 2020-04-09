package com.chaka.netty.study.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议的版本号
     */
    @JSONField(serialize = false)
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}