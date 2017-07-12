package cn.com.reformer.netty.handler;

import cn.com.reformer.netty.adapter.TCPMessageHandlerAdapter;
import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.bean.TcpUser;
import cn.com.reformer.netty.bean.UserType;
import cn.com.reformer.netty.communication.CarLockTcpMessageSender;
import cn.com.reformer.netty.msg.MSG_0x01;
import cn.com.reformer.netty.util.AESCoder;
import cn.com.reformer.netty.util.SignUtils;
import cn.com.reformer.netty.util.msg.ClientManager;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright 2017 the original author or authors hangzhou Reformer
 *
 * @author zhangjin
 * @Description: 心跳处理类
 * @create 2017-05-08
 **/
public class Handler0x01 extends TCPMessageHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(Handler0x01.class);

    @Autowired(required = true)
    private ClientManager clientManager;
    @Autowired(required = true)
    private CarLockTcpMessageSender carLockTcpMessageSender;


    public void doHandle(BaseParam m, ChannelHandlerContext ctx) {

        MSG_0x01 ret = new MSG_0x01();

        if (null != m) {

            if (null != m.getSn() && null != m.getNonce()) {
                TcpUser user = new TcpUser();
                user.setType(UserType.CARLOCK);
                user.setSn(m.getSn());
                try {
                    byte[] jiemi = AESCoder.decrypt(SignUtils.hexStringToBytes(m.getSign()), AESCoder.keys);
                    byte[] sn_bytes = new byte[9];
                    byte cmd_byte;
                    byte[] nonce_bytes = new byte[4];
                    System.arraycopy(jiemi, 0, sn_bytes, 0, 9);
                    cmd_byte = jiemi[9];
                    System.arraycopy(jiemi, 10, nonce_bytes, 0, 4);

                    String sn_hex = SignUtils.bytes2Hex(sn_bytes);
                    String once_hex = SignUtils.bytes2Hex(nonce_bytes);
                    String comp_sn_hex = SignUtils.bytes2Hex(m.getSn().getBytes());
                    String comp_nonce_hex = SignUtils.bytes2Hex(m.getNonce().substring(0,4).getBytes());
                    boolean sn_is_same = sn_hex.equals(comp_sn_hex);
                    boolean cmd_is_same = cmd_byte == m.getCmd();
                    boolean nonce_is_same = once_hex.equals(comp_nonce_hex);
                    boolean isSame = sn_is_same && cmd_is_same && nonce_is_same;
                    if (isSame) {
                        clientManager.addClient(ctx, user);
                        ret.setSn(m.getSn());
                        ret.setCmd(m.getCmd());
                        ret.setVersion(m.getVersion());
                        ret.setNonce(m.getNonce());
                        ret.setSign(m.getSign());
                        ret.setType(m.getType());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }


                String checkValue = SignUtils.getSigin(m.getSn(), m.getCmd(), m.getNonce());
                logger.debug("recevice checkValue" + m.getSign());
                logger.debug("checkValue" + checkValue);
                String o = new Gson().toJson(ret);
                ctx.writeAndFlush(o);

            } else {

            }
        } else {

        }


    }

    private TcpUser getTcpUserByMac(String mac) {
        return new TcpUser();
    }


    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }


}
