package cn.com.reformer.netty.server.thread;

import cn.com.reformer.netty.business.IMessageHandler;
import cn.com.reformer.netty.handler.HandlerFactory;
import cn.com.reformer.netty.msg.ReceivePackBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理消息线程
 */
public class ParseMsgThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ParseMsgThread.class);

    private ReceivePackBean rpb;
    private HandlerFactory handlerFactory;

    public ParseMsgThread() {

    }

    public ParseMsgThread(ReceivePackBean rpb, HandlerFactory handlerFactory) {
        this.rpb = rpb;
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void run() {
        try {

            // 交给对应handler处理
            IMessageHandler handler = handlerFactory.getHandler(rpb.getMsg());
            if (handler != null) {
                handler.doHandle(rpb.getMsg(), rpb.getChannel());
            }
        } catch (Exception e) {
            logger.error("接受消息队列处理数据错误", e);
        }
    }





    public HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    public void setHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }
}
