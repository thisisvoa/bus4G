package cn.com.reformer.netty.server.thread;

import cn.com.reformer.netty.util.msg.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 检查未登陆的客户端
 */
public class CheckTemClientThread extends AbsThread {

    private static final Logger logger = LoggerFactory
            .getLogger(CheckTemClientThread.class);

    static CheckTemClientThread obj;

    @Autowired
    private ClientManager clientManager;

    @Autowired(required = false)
    private Integer delay;

    @Autowired(required = false)
    private Integer period;


    public static CheckTemClientThread getInstance() {
        if (obj == null)
            obj = new CheckTemClientThread();
        return obj;
    }

    private Timer timer = new Timer();

    @Override
    public void runThread(long delay, long period) {
        timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clientManager.checkTempClient();
                }
            }, delay * 1000, period * 1000);
        } catch (Exception e) {
            logger.error("检查临时客户端异常：", e);
        }

    }

    public void runThread() {
        timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clientManager.checkTempClient();
                }
            }, delay * 1000, period * 1000);
        } catch (Exception e) {
            logger.error("检查临时客户端异常：", e);
        }

    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }


    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
