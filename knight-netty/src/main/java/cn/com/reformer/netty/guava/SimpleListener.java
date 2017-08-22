package cn.com.reformer.netty.guava;

import com.google.common.eventbus.Subscribe;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

/**
 * Created by zhang on 2017/8/22.
 */
public class SimpleListener {

    @Resource
    SimpMessagingTemplate simpMessagingTemplate;

    @Subscribe
    public void task(String s) {
        System.out.println("do task(" + s + ")");
        this.simpMessagingTemplate.convertAndSend("/topic/getResponse", s);
    }
}
