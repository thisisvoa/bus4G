package cn.com.reformer.netty.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.Map;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description:   This class holds configuration information thats useful to start a netty
 * server. It has information on port numbers, {@link io.netty.channel.EventLoopGroup}s and
 * {@link ChannelOption}s.
 * @author zhangjin
 * @create 2017-05-08
**/
public class NettyConfig {


    private Map<ChannelOption<?>, Object> channelOptions;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private OioEventLoopGroup oioEventLoopGroup;//串口group
    private int bossThreadCount;
    private int workerThreadCount;
    private int oioMaxChannels;
    private InetSocketAddress socketAddress;

    //组播查找用
    private String localAddress;
    private String remoteAddress;

    private Object portNumber = 6500;
    protected ChannelInitializer<? extends Channel> channelInitializer;

    public Map<ChannelOption<?>, Object> getChannelOptions() {
        return channelOptions;
    }

    public void setChannelOptions(
            Map<ChannelOption<?>, Object> channelOptions) {
        this.channelOptions = channelOptions;
    }

    public synchronized NioEventLoopGroup getBossGroup() {
        if (null == bossGroup) {
            if (0 >= bossThreadCount) {
                bossGroup = new NioEventLoopGroup();
            } else {
                bossGroup = new NioEventLoopGroup(bossThreadCount);
            }
        }
        return bossGroup;
    }

    public void setBossGroup(NioEventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public synchronized NioEventLoopGroup getWorkerGroup() {
        if (null == workerGroup) {
            if (0 >= workerThreadCount) {
                workerGroup = new NioEventLoopGroup();
            } else {
                workerGroup = new NioEventLoopGroup(workerThreadCount);
            }
        }
        return workerGroup;
    }


    public void setOioEventLoopGroup(OioEventLoopGroup oioEventLoopGroup) {
        this.oioEventLoopGroup = oioEventLoopGroup;
    }

    public synchronized OioEventLoopGroup getOioEventLoopGroup() {
        if (null == oioEventLoopGroup) {
            if (0 >= oioMaxChannels) {
                oioEventLoopGroup = new OioEventLoopGroup();
            } else {
                oioEventLoopGroup = new OioEventLoopGroup(oioMaxChannels);
            }
        }
        return oioEventLoopGroup;
    }

    public void setWorkerGroup(NioEventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public int getBossThreadCount() {
        return bossThreadCount;
    }

    public void setBossThreadCount(int bossThreadCount) {
        this.bossThreadCount = bossThreadCount;
    }

    public int getWorkerThreadCount() {
        return workerThreadCount;
    }

    public void setWorkerThreadCount(int workerThreadCount) {
        this.workerThreadCount = workerThreadCount;
    }

    public int getOioMaxChannels() {
        return oioMaxChannels;
    }

    public void setOioMaxChannels(int oioMaxChannels) {
        this.oioMaxChannels = oioMaxChannels;
    }

    public synchronized InetSocketAddress getSocketAddress() {
        if (null == socketAddress) {
            socketAddress = new InetSocketAddress((Integer.parseInt(String.valueOf(portNumber))));
        }
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public Object getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Object portNumber) {
        this.portNumber = portNumber;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }


}
