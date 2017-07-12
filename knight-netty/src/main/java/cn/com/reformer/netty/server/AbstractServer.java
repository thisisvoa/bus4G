package cn.com.reformer.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public abstract class AbstractServer implements NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractServer.class);
    protected final NettyConfig nettyConfig;
    protected ChannelInitializer<? extends Channel> channelInitializer;

    public AbstractServer(NettyConfig nettyConfig, ChannelInitializer<? extends Channel> channelInitializer) {
        this.nettyConfig = nettyConfig;
        this.channelInitializer = channelInitializer;
    }

    @Override
    public void startServer(int port) throws Exception {
        nettyConfig.setPortNumber(port);
        nettyConfig.setSocketAddress(new InetSocketAddress(port));
        startServer();
    }

    @Override
    public void startServer(InetSocketAddress socketAddress) throws Exception {
        nettyConfig.setSocketAddress(socketAddress);
        startServer();
    }


    @Override
    public ChannelInitializer<? extends Channel> getChannelInitializer() {
        return channelInitializer;
    }

    @Override
    public NettyConfig getNettyConfig() {
        return nettyConfig;
    }

    protected EventLoopGroup getBossGroup() {
        return nettyConfig.getBossGroup();
    }

    protected OioEventLoopGroup getOioEventLoopGroup() {
        return nettyConfig.getOioEventLoopGroup();
    }

    protected EventLoopGroup getWorkerGroup() {
        return nettyConfig.getWorkerGroup();
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return nettyConfig.getSocketAddress();
    }

    @Override
    public String toString() {
        return "AbstractServer [socketAddress=" + nettyConfig.getSocketAddress()
                + ", portNumber=" + nettyConfig.getPortNumber() + "]";
    }

}
