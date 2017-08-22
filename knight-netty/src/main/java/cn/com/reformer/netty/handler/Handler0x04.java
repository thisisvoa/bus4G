package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.guava.SimpleListener;
import cn.com.reformer.netty.msg.MSG_0x03;
import cn.com.reformer.netty.msg.MSG_0x04;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: 上传状态
 * @author zhangjin
 * @create 2017-05-08
**/
public class Handler0x04 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x04.class);
    @Autowired(required = false)
    EventBus eventBus;
    @Autowired(required = false)
    SimpleListener simpleListener;
    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {
        try {
            if (m instanceof MSG_0x04) {
                MSG_0x04 msg = (MSG_0x04) m;
//                MsgCache.getInstance().remove(msg.getHead() + ";" + msg.getHead().getSeq());
            } else {
                logger.error("convert common reply fail:" + m.toString());
                eventBus.register(simpleListener);
                eventBus.post(m.toString());
            }
        } catch (Exception e) {
            logger.error("handler common reply fail" + e);
        }
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public SimpleListener getSimpleListener() {
        return simpleListener;
    }

    public void setSimpleListener(SimpleListener simpleListener) {
        this.simpleListener = simpleListener;
    }
}
