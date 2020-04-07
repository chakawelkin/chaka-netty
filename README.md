## 客户端与服务端的双向通信

> 在整个客服端与服务端的交互通信中使用的是ByteBuf来作为二进制数据的传输载体

1、客户端建立连接后触发客户端的`channelActive`

2、服务端接收到消息后`channelRead`,处理对应的消息

3、服务端将需要发送的消息经过`writeAndFlush`写入`channel`中

#### ByteBuf的数据结构

![](https://user-gold-cdn.xitu.io/2018/8/5/1650817a1455afbb?imageslim)

主要分为3个部分：

1. 已经丢弃的字节
2. 可以读取的字节
3. 可以写入的字节

#### 容量API

>Netty的ByteBuf提供了十分方便来判断容量的方法

1. `capacity()`
2. `maxCapacity()`



#### 读写API

- release()与retain():由于Netty使用的是堆外内存，所以并不会被JVM直接管理，也就是申请到的内存无法被垃圾回收器直接回收。
- 