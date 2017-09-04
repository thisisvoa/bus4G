package cn.com.reformer.netty.server;

import cn.com.reformer.netty.handler.TCPMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class TCPServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private TCPMessageHandler tcpMessageHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("tcpMsgEncoder", new StringEncoder());
        LineBasedFrameDecoder decoder = new LineBasedFrameDecoder(1500);
        pipeline.addLast("tcpMsgDecoder", new StringDecoder());
        pipeline.addLast("tcpMsgHandler", tcpMessageHandler);
        //每出现换行符自动拆包，设置最大长度为1024个字节。

        pipeline.addLast("decoder", decoder);
    }
}
