package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.msg.MSG_0x03;
import cn.com.reformer.netty.msg.MSG_0x04;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: 上传状态
 * @author zhangjin
 * @create 2017-05-08
**/
public class Handler0x04 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x04.class);

    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {
        try {
            if (m instanceof MSG_0x04) {
                MSG_0x04 msg = (MSG_0x04) m;
//                MsgCache.getInstance().remove(msg.getHead() + ";" + msg.getHead().getSeq());
            } else {
                logger.error("convert common reply fail:" + m.toString());
            }
        } catch (Exception e) {
            logger.error("handler common reply fail" + e);
        }
    }

}
