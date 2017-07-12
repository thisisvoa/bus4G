package cn.com.reformer.netty.server;


import cn.com.reformer.netty.exception.ServerStopException;

import java.net.InetSocketAddress;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public interface Server {

    public interface TransmissionProtocol {

    }

    public enum TRANSMISSION_PROTOCOL implements TransmissionProtocol {
        TCP, UDP, RXTX;
    }

    TransmissionProtocol getTransmissionProtocol();

    void startServer() throws Exception, ServerStopException;

    void startServer(int port) throws Exception;

    ;

    void startServer(InetSocketAddress socketAddress) throws Exception;

    void stopServer() throws Exception, ServerStopException;

    InetSocketAddress getSocketAddress();
}
