package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.communication.CarLockTcpMessageSender;
import cn.com.reformer.netty.util.msg.ClientManager;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: 遥控开
 * @author zhangjin
 * @create 2017-05-08
**/
public class Handler0x02 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x02.class);

    @Autowired
    private ClientManager clientManager;

    @Autowired(required = true)
    private CarLockTcpMessageSender carLockTcpMessageSender;
    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {

        System.out.println("开门返回："+m.toString());

    }


    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
}
