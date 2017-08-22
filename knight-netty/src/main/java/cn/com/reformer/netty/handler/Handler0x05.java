package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.event.handler.IUploadHandler;
import cn.com.reformer.netty.guava.SimpleListener;
import cn.com.reformer.netty.msg.MSG_0x05;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: 上传二维码
 * @author zhangjin
 * @create 2017-05-08
**/
public class Handler0x05 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x05.class);

    @Autowired(required = false)
    EventBus eventBus;
    @Autowired(required = false)
    SimpleListener simpleListener;
    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {
        try {
            if (m instanceof MSG_0x05) {
                MSG_0x05 msg = (MSG_0x05) m;
                eventBus.register(simpleListener);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");//设置日期格式
                String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                String event = msg.toString() + "<br>time:" + date;

                eventBus.post(event);

               // eventBus.post(msg);
//                MsgCache.getInstance().remove(msg.getHead() + ";" + msg.getHead().getSeq());
            } else {
                logger.error("convert common reply fail:" + m.toString());
                eventBus.register(simpleListener);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                String event = m.toString() + "<br>time:" + date;
                eventBus.post(event);
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
