package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.guava.SimpleListener;
import cn.com.reformer.netty.msg.MSG_0x05;
import cn.com.reformer.netty.msg.MSG_0x06;
import cn.com.reformer.netty.util.MapUtils;
import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright 2017 the original author or authors hangzhou Reformer
 *
 * @author zhangjin
 * @Description: 上传二维码
 * @create 2017-05-08
 **/
public class Handler0x06 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x06.class);
    @Autowired(required = false)
    EventBus eventBus;
    @Autowired(required = false)
    SimpleListener simpleListener;



    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {
        try {
            if (m instanceof MSG_0x06) {
                MSG_0x06 msg = (MSG_0x06) m;

                System.out.println("______________return time_______________");
                System.out.println(System.currentTimeMillis());
                System.out.println(msg.toString());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");//设置日期格式
                Date date1 = new Date();
                String date = df.format(date1);// new Date()为获取当前系统时间，也可使用当前时间戳
                msg.setEndTime(date1.getTime());
                msg.setStrEndTime(date);
                String event =  new Gson().toJson(msg) ;
                logger.error("convert common reply fail:" + m.toString());
                eventBus.register(simpleListener);
                eventBus.post(event);
                // eventBus.post(msg);
//                MsgCache.getInstance().remove(msg.getHead() + ";" + msg.getHead().getSeq());
            } else {
                MSG_0x06 msg = (MSG_0x06) m;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");//设置日期格式
                Date date1 = new Date();
                String date = df.format(date1);// new Date()为获取当前系统时间，也可使用当前时间戳
                msg.setEndTime(date1.getTime());
                msg.setStrEndTime(date);
                String event = msg.toString() ;
                logger.error("convert common reply fail:" + m.toString());
                eventBus.register(simpleListener);
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
}
