package com.chaka.netty.study.handler;

import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Boolean> HAS_LOGIN  = AttributeKey.newInstance("hasLogin");

}
