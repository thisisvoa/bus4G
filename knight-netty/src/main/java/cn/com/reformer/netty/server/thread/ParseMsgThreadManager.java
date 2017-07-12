package cn.com.reformer.netty.server.thread;

import cn.com.reformer.netty.handler.HandlerFactory;
import cn.com.reformer.netty.msg.ReceivePackBean;
import cn.com.reformer.netty.msg.ServerMsgQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池（处理消息）管理
 *
 * @author sid
 */
public class ParseMsgThreadManager extends AbsThread {

    private static final Logger logger = LoggerFactory.getLogger(ParseMsgThreadManager.class);

    static ParseMsgThreadManager obj;
    private Integer corePoolSize;
    private Integer maximunPoolSize;
    private Integer keepAliveTime;
    private Integer queueSize;
    @Autowired
    private HandlerFactory handlerFactory;


    private ThreadPoolExecutor threadPool;

    private volatile boolean isStart = true;

    public ParseMsgThreadManager(int corePoolSize, int maximunPoolSize, int keepAliveTime, int queueSize) {

        threadPool = new ThreadPoolExecutor(corePoolSize, maximunPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public void runThread(long delay, long period) {
        isRun = true;
        isStart = true;

        new Thread(new ParseThreadManage()).start();
        logger.info("服务器消息处理启动完成");

    }

    class ParseThreadManage implements Runnable {

        public void run() {
            while (isStart) {
                ReceivePackBean rpb = null;
                try {
                    LinkedBlockingQueue<ReceivePackBean> recqueue = ServerMsgQueue.getRecqueue();
                    rpb = recqueue.take();
                    threadPool.execute(new ParseMsgThread(rpb, handlerFactory));
                } catch (Exception e) {
                    logger.error("消息解析管理线程运行异常", e);
                }
            }
        }

    }

    public void stop() {
        isRun = false;
        isStart = false;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    public void setHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public Integer getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Integer keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getMaximunPoolSize() {
        return maximunPoolSize;
    }

    public void setMaximunPoolSize(Integer maximunPoolSize) {
        this.maximunPoolSize = maximunPoolSize;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}
