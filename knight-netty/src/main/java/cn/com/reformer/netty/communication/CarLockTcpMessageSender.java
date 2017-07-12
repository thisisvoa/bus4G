package cn.com.reformer.netty.communication;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.bean.Client;
import cn.com.reformer.netty.util.SignUtils;
import cn.com.reformer.netty.util.msg.ClientManager;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class CarLockTcpMessageSender extends TCPMessageSender {

    Logger logger = LoggerFactory.getLogger(CarLockTcpMessageSender.class);
    @Autowired(required = true)
    private ClientManager clientManager;

    public CarLockTcpMessageSender(Channel channel) {
        super(channel);
    }

    public CarLockTcpMessageSender() {
        super();
    }

    public void openDoor(String sn) {

        Client client = ClientManager.getClientBySN(sn);

        if(null != client){
            BaseParam baseParam = createBaseParam(sn);

            ChannelHandlerContext channel = client.getChannel();
            if(null != channel){
                String o = new Gson().toJson(baseParam);
                channel.writeAndFlush(o);
            }
            else{
                logger.debug("设备不在线，通道channel为空，执行失败");
            }

        }
        else{
            logger.debug("设备不在线，执行失败");
        }




    }
    public void handlerQrcode(String sn) {

        Client client = ClientManager.getClientBySN(sn);

        if(null != client){
            BaseParam baseParam = createQrBaseParam(sn);

            ChannelHandlerContext channel = client.getChannel();
            if(null != channel){
                String o = new Gson().toJson(baseParam);
                channel.writeAndFlush(o);
            }
            else{
                logger.debug("设备不在线，通道channel为空，执行失败");
            }

        }
        else{
            logger.debug("设备不在线，执行失败");
        }




    }
    public void getStatus(String sn) {

        Client client = ClientManager.getClientBySN(sn);

        if(null != client){
            BaseParam baseParam = createBaseParam2(sn);

            ChannelHandlerContext channel = client.getChannel();
            if(null != channel){
                String o = new Gson().toJson(baseParam);
                channel.writeAndFlush(o);
            }
            else{
                logger.debug("设备不在线，通道channel为空，执行失败");
            }

        }
        else{
            logger.debug("设备不在线，执行失败");
        }




    }
    private BaseParam createBaseParam(String sn) {
        byte cmd=0x02;
        BaseParam baseParam=new BaseParam();
        baseParam.setSn(sn);
        baseParam.setCmd(cmd);
        int randomDig=nextInt(10000,100000);
        baseParam.setNonce(String.valueOf(randomDig));
        baseParam.setSign(SignUtils.getSigin(sn, cmd, String.valueOf(randomDig)));
        return baseParam;
    }
    private BaseParam createQrBaseParam(String sn) {
        byte cmd=0x05;
        BaseParam baseParam=new BaseParam();
        baseParam.setSn(sn);
        baseParam.setCmd(cmd);
        baseParam.setType((byte) 0x02);
        int randomDig=nextInt(10000,100000);
        baseParam.setNonce(String.valueOf(randomDig));
        baseParam.setSign(SignUtils.getSigin(sn, cmd, String.valueOf(randomDig)));
        return baseParam;
    }
    private BaseParam createBaseParam2(String sn) {
        byte cmd=0x03;
        BaseParam baseParam=new BaseParam();
        baseParam.setSn(sn);
        baseParam.setCmd(cmd);
        int randomDig=nextInt(10000,100000);
        baseParam.setNonce(String.valueOf(randomDig));
        baseParam.setSign(SignUtils.getSigin(sn, cmd, String.valueOf(randomDig)));
        return baseParam;
    }

    public int nextInt(final int min, final int max) {

        Random rand = new Random();
        int tmp = Math.abs(rand.nextInt());

        return tmp % (max - min + 1) + min;

    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
}
