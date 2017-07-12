package cn.com.reformer.netty.server;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.cache.MsgCache;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class TCPServer extends AbstractServer {

    public static final ChannelGroup ALL_CHANNELS = new DefaultChannelGroup("KNIGHT-QRCODE-TCP-CHANNELS", GlobalEventExecutor.INSTANCE);

    private static final Logger LOG = LoggerFactory.getLogger(TCPServer.class);

    private ServerBootstrap serverBootstrap;

    public static ChannelHandlerContext chtx;

    @Override
    public TransmissionProtocol getTransmissionProtocol() {
        return TRANSMISSION_PROTOCOL.TCP;
    }

    public TCPServer(NettyConfig nettyConfig, ChannelInitializer<? extends Channel> channelInitializer) {
        super(nettyConfig, channelInitializer);
    }

    @Override
    public void startServer() throws Exception {
        try {
            serverBootstrap = new ServerBootstrap();
            LOG.info("****** Start the CarLock TCP server, port {} ******", this.getNettyConfig().getPortNumber());
            if (getChannelInitializer() == null) {
                LOG.error("****** Start the CarLock TCP server failed, port {}. Please check the server config. ******", nettyConfig.getPortNumber());
                return;
            }

            Map<ChannelOption<?>, Object> chnOptions = nettyConfig.getChannelOptions();
            if (null != chnOptions && chnOptions.size() > 0) {
                Set<ChannelOption<?>> set = chnOptions.keySet();
                for (ChannelOption opt : set) {
                    serverBootstrap.childOption(opt, chnOptions.get(opt));
                }
            }

            serverBootstrap.group(getBossGroup(), getWorkerGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(getChannelInitializer())
                    .handler(new LoggingHandler(LogLevel.INFO));
            final ChannelFuture cf = serverBootstrap.bind(nettyConfig.getSocketAddress());
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        ALL_CHANNELS.add(cf.channel());
                        LOG.info("****** Start the CarLock TCP server successfully, port {} ******", nettyConfig.getPortNumber());
                    } else {
                        LOG.info("****** Start the CarLock TCP server failed, port {}. The cause is {} ******", nettyConfig.getPortNumber(), cf.cause());
                    }
                }
            });
        } catch (Exception e) {

            LOG.error("Visitor TCP Server start error {}, going to shut down", e);
            stopServer();
            throw e;

        }
    }

    @Override
    public void stopServer() throws Exception {
        LOG.debug("In stopServer method of class: {}", this.getClass()
                .getName());
        ChannelGroupFuture future = ALL_CHANNELS.close();
        try {
            future.await();
        } catch (InterruptedException e) {
            LOG.error(
                    "Execption occurred while waiting for channels to close: {}",
                    e);
        } finally {
            // TODO move this part to spring.
            if (null != nettyConfig.getBossGroup()) {
                Future fb = nettyConfig.getBossGroup().shutdownGracefully(0, 1, TimeUnit.MILLISECONDS);
                try {
                    fb.await();
                } catch (InterruptedException ignore) {
                    LOG.error("Exception while waiting for tcpserver to complete shutdown  ", ignore);
                }
            }
            if (null != nettyConfig.getWorkerGroup()) {
                Future fw = nettyConfig.getWorkerGroup().shutdownGracefully(0, 1, TimeUnit.MILLISECONDS);
                try {
                    fw.await();
                } catch (InterruptedException ignore) {
                    LOG.error("Exception while waiting for proxy to tcpserver shutdown  ", ignore);
                }
            }
        }
    }


    @Override
    public void setChannelInitializer(ChannelInitializer<? extends Channel> initializer) {
        this.channelInitializer = initializer;
        serverBootstrap.childHandler(initializer);


    }

    /**
     * send:(发送消息).
     *
     * @param m
     * @author sid
     */
    public void send(BaseParam m) {
        if (chtx != null && chtx.channel().isOpen()) {
            MsgCache.getInstance().put(m);
            chtx.channel().write(m);
            chtx.flush();
        }
    }

    /**
     * 只发消息不缓存，用于心跳类消息
     *
     * @param m
     */
    public void sendWithoutCache(BaseParam m) {
        LOG.debug("CLINET发送WithoutCache：" + m.toString());
        if (chtx != null && chtx.channel().isOpen()) {
            chtx.write(m);
            chtx.flush();
        }
    }


    public static ChannelHandlerContext getChtx() {
        return chtx;
    }

    public static void setChtx(ChannelHandlerContext chtx) {
        TCPServer.chtx = chtx;
    }
}