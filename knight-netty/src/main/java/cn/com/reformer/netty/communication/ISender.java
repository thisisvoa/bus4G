package cn.com.reformer.netty.communication;

import cn.com.reformer.netty.msg.MessagePacket;
import io.netty.channel.ChannelFuture;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public interface ISender<T extends MessagePacket> {
    public ChannelFuture send(T t);

    public void close();
}
