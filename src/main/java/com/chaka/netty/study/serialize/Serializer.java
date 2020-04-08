package com.chaka.netty.study.serialize;

/**
 * 既然涉及到通信传输，那么必然就需要如何将对象进行序列化
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 对象的序列化
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化为对象
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz,byte[] bytes);

}