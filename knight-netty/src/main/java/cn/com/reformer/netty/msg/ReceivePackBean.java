package cn.com.reformer.netty.msg;

import cn.com.reformer.netty.bean.BaseParam;
import io.netty.channel.ChannelHandlerContext;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: 封装hanler类
 * @author zhangjin
 * @create 2017-05-08
**/
public class ReceivePackBean {

    private BaseParam msg;

    private ChannelHandlerContext channel;


    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }


    public BaseParam getMsg() {
        return msg;
    }

    public void setMsg(BaseParam msg) {
        this.msg = msg;
    }
}
