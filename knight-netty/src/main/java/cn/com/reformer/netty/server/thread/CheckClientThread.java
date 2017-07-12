package cn.com.reformer.netty.server.thread;

import cn.com.reformer.netty.util.msg.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 检查长时间未活跃的客户端
 */
public class CheckClientThread extends AbsThread {

    @Autowired
    private ClientManager clientManager;
    @Autowired(required = false)
    private Integer clientdelay;
    @Autowired(required = false)
    private Integer period;

    private static final Logger logger = LoggerFactory
            .getLogger(CheckClientThread.class);

    static CheckClientThread obj;

    public static CheckClientThread getInstance() {
        if (obj == null)
            obj = new CheckClientThread();
        return obj;
    }

    private Timer timer = new Timer();

    public void runThread() {
        timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        clientManager.checkClient();
                    } catch (Exception e) {
                        logger.error("检查登陆客户端异常:", e);
                    }
                }
            }, getClientdelay() * 1000, period * 1000);
        } catch (Exception e) {
            logger.info("检查客户端线程异常：", e);
            e.printStackTrace();

        }

    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public Integer getClientdelay() {
        return clientdelay;
    }

    public void setClientdelay(Integer clientdelay) {
        this.clientdelay = clientdelay;
    }

    @Override
    public void runThread(long delay, long period) {
        timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        clientManager.checkClient();
                    } catch (Exception e) {
                        logger.error("检查登陆客户端异常:", e);
                    }
                }
            }, delay * 1000, period * 1000);
        } catch (Exception e) {
            logger.info("检查客户端线程异常：", e);
            e.printStackTrace();

        }
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
