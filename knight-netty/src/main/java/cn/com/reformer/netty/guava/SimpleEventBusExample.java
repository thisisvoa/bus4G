package cn.com.reformer.netty.guava;

import com.google.common.eventbus.EventBus;

/**
 * Created by zhang on 2017/8/22.
 */
public class SimpleEventBusExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        System.out.println("Post Simple EventBus Example");
        eventBus.post("Simple EventBus Example");
    }

}
