package cn.com.reformer.netty.bean;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class Client {

    private ChannelHandlerContext channel;


    private Date lastUpTime = new Date();


    private TcpUser user;


    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    public Date getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(Date lastUpTime) {
        this.lastUpTime = lastUpTime;
    }


    public TcpUser getUser() {
        return user;
    }

    public void setUser(TcpUser user) {
        this.user = user;
    }
}
