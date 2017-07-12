package cn.com.reformer.netty.business;

import cn.com.reformer.netty.bean.BaseParam;
import io.netty.channel.ChannelHandlerContext;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public interface IMessageHandler<T extends BaseParam> {
    void doHandle(BaseParam m, ChannelHandlerContext ctx);
}
