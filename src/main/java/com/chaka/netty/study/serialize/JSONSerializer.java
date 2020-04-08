package com.chaka.netty.study.serialize;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

/**
 * 这里使用FastJSON作为序列化
 */
public class JSONSerializer implements Serializer{

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONString(object).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(new String(bytes, Charset.forName("UTF-8")),clazz);
    }

}