package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.business.IMessageHandler;
import cn.com.reformer.netty.msg.MessageID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class HandlerFactory {

    @Autowired
    private Handler0x01 handler0X01;


    @Autowired
    private Handler0x02 handler0X02;


    @Autowired(required=true)
    private Handler0x03 handler0x03;
    @Autowired(required=true)
    private Handler0x04 handler0x04;
    @Autowired(required=true)
    private Handler0x05 handler0x05;


    private static final Logger logger = LoggerFactory.getLogger(HandlerFactory.class);

    public IMessageHandler getHandler(BaseParam m) {

        int cmd = m.getCmd();

        if (logger.isDebugEnabled()) {
            logger.debug("handler  factory create  message:" + Integer.toHexString(cmd)); //$NON-NLS-1$
        }
        IMessageHandler h = null;
        switch (cmd) {
            case MessageID.MSG_0x01:
                h = handler0X01;
                break;
            case MessageID.MSG_0x02:
                h = handler0X02;
                break;
            case MessageID.MSG_0x03:
                h = handler0x03;
                break;
            case MessageID.MSG_0x04:
                h = handler0x04;
                break;
            case MessageID.MSG_0x05:
                h = handler0x05;
                break;
            default:
                break;
        }
        return h;
    }

    public Handler0x01 getHandler0X01() {
        return handler0X01;
    }

    public void setHandler0X01(Handler0x01 handler0X01) {
        this.handler0X01 = handler0X01;
    }

    public Handler0x02 getHandler0X02() {
        return handler0X02;
    }

    public void setHandler0X02(Handler0x02 handler0X02) {
        this.handler0X02 = handler0X02;
    }

    public Handler0x03 getHandler0x03() {
        return handler0x03;
    }

    public void setHandler0x03(Handler0x03 handler0x03) {
        this.handler0x03 = handler0x03;
    }

    public Handler0x04 getHandler0x04() {
        return handler0x04;
    }

    public void setHandler0x04(Handler0x04 handler0x04) {
        this.handler0x04 = handler0x04;
    }

    public Handler0x05 getHandler0x05() {
        return handler0x05;
    }

    public void setHandler0x05(Handler0x05 handler0x05) {
        this.handler0x05 = handler0x05;
    }
}
