package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.msg.MSG_0x05;
import cn.com.reformer.netty.msg.MSG_0x06;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: 上传二维码
 * @author zhangjin
 * @create 2017-05-08
**/
public class Handler0x06 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x06.class);



    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {
        try {
            if (m instanceof MSG_0x06) {
                MSG_0x06 msg = (MSG_0x06) m;


               // eventBus.post(msg);
//                MsgCache.getInstance().remove(msg.getHead() + ";" + msg.getHead().getSeq());
            } else {
                logger.error("convert common reply fail:" + m.toString());
            }
        } catch (Exception e) {
            logger.error("handler common reply fail" + e);
        }
    }


}
