package cn.com.reformer.netty.adapter;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.business.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public abstract class TCPMessageHandlerAdapter implements IMessageHandler<BaseParam> {
    private static final Logger LOG = LoggerFactory.getLogger(TCPMessageHandlerAdapter.class);


}
