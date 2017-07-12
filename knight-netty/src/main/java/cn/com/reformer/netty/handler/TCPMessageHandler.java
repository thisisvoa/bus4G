package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.msg.*;
import cn.com.reformer.netty.util.msg.ClientManager;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
@ChannelHandler.Sharable
public class TCPMessageHandler extends SimpleChannelInboundHandler <String>{
    private static final Logger LOG = LoggerFactory.getLogger(TCPMessageHandler.class);

    @Autowired
    private HandlerFactory handlerFactory;

    @Autowired
    private ClientManager clientManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("收到数据" + msg.toString());
        BaseParam bpg=null;
        Gson g=new Gson();
        try{

            bpg= g.fromJson(msg.toString(), BaseParam.class);
            if(bpg.getCmd()== MessageID.MSG_0x01){
                bpg =    g.fromJson(msg.toString(), BaseParam.class);
            }
            else if(bpg.getCmd()== MessageID.MSG_0x02){
                bpg =    g.fromJson(msg.toString(), BaseParam.class);
            }
            else if(bpg.getCmd()== MessageID.MSG_0x03){
                bpg =    g.fromJson(msg.toString(), BaseParam.class);
            }
            else if(bpg.getCmd()== MessageID.MSG_0x04){
                bpg =    g.fromJson(msg.toString(), MSG_0x04.class);
            }
            else if(bpg.getCmd()== MessageID.MSG_0x05){
                bpg =    g.fromJson(msg.toString(), MSG_0x05.class);
            }
            if(null !=bpg){
                try {

                    ReceivePackBean receivePackBean = new ReceivePackBean();
                    receivePackBean.setChannel(ctx);
                    receivePackBean.setMsg(bpg);
                    ServerMsgQueue.getRecqueue().put(receivePackBean);
                } catch (InterruptedException e) {
                    LOG.error("主handler---接收消息失败", e);
                }
            }

        }catch(Exception e){
            LOG.error(String.valueOf("发送的数据格式有误请重新发送".getBytes("UTF-8")));

        }



       /* ISender sender = new TCPMessageSender(ctx.channel());
        IMessageHandler iMessageHandler= handlerFactory.getHandler(msg);
        iMessageHandler.doHandle(msg,ctx);*/


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            super.channelActive(ctx);
            LOG.info("-------------临时客户端建立连接--------------");
            // TCPServer.setChtx(ctx);
            clientManager.addTemClient(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            super.channelInactive(ctx);
            InetSocketAddress address = (InetSocketAddress) ctx.channel()
                    .remoteAddress();
            InetAddress inetAdd = address.getAddress();
            LOG.info("客户端断开连接：" + ctx.name()
                    + ",IP:" + inetAdd.getHostAddress()
                    + ",port:" + address.getPort());
            // 记录日志
            clientManager.removeClient(ctx);
            clientManager.removeTemClient(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.info("-------------连接异常关闭--------------");
        try {
            cause.printStackTrace();
            ctx.close();
            clientManager.removeClient(ctx);
            clientManager.removeTemClient(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    public void setHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }
}
