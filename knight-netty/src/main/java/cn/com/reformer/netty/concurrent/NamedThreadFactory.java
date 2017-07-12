package cn.com.reformer.netty.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class NamedThreadFactory implements ThreadFactory {
    private static AtomicInteger counter = new AtomicInteger(1);
    private String name = "Lane";
    private boolean daemon;
    private int priority;

    public NamedThreadFactory(String name) {
        this(name, false, -1);
    }

    public NamedThreadFactory(String name, boolean daemon) {
        this(name, daemon, -1);
    }

    public NamedThreadFactory(String name, boolean daemon, int priority) {
        this.name = name;
        this.daemon = daemon;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, name + "[" + counter.getAndIncrement() + "]");
        thread.setDaemon(daemon);
        if (priority != -1) {
            thread.setPriority(priority);
        }
        return thread;
    }
}
