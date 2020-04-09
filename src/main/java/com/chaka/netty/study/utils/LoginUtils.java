package com.chaka.netty.study.utils;

import com.chaka.netty.study.handler.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;


/**
 * 登录工具类
 */
public class LoginUtils {

    /**
     * 标记为已登录
     * @param channel
     */
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.HAS_LOGIN).set(true);
    }

    /**
     * 判断是否已经登录
     * @param channel
     * @return
     */
    public static boolean isLogin(Channel channel){
        Attribute<Boolean> attr = channel.attr(Attributes.HAS_LOGIN);
        if (attr != null && attr.get() != null){
           return attr.get();
        }
        return false;
    }

}
