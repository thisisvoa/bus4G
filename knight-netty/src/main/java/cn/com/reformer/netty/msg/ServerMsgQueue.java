package cn.com.reformer.netty.msg;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: 消息队列
 * @author zhangjin
 * @create 2017-05-08
**/
public class ServerMsgQueue {
    /**
     * 接收数据队列
     */
    private static LinkedBlockingQueue<ReceivePackBean> rec_queue = new LinkedBlockingQueue<ReceivePackBean>();

    public static LinkedBlockingQueue<ReceivePackBean> getRecqueue() {
        return rec_queue;
    }
}
