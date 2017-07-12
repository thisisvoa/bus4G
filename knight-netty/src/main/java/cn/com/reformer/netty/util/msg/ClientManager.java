package cn.com.reformer.netty.util.msg;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.bean.Client;
import cn.com.reformer.netty.bean.TcpUser;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端管理工具
 *
 * @author sid
 */
public class ClientManager {
    @Autowired(required = false)
    private Integer heartbeatDelay;

    private static final Logger logger = LoggerFactory.getLogger(ClientManager.class);

    /**
     * 客户端连接
     */
    private static ConcurrentHashMap<String, Client> clientMap = new ConcurrentHashMap<String, Client>();

    /**
     * 客户端临时连接--未登陆前
     */
    private static ConcurrentHashMap<String, Client> tempClientMap = new ConcurrentHashMap<String, Client>();

    /**
     * 添加一个临时客户端
     *
     * @param ctx
     */

    public static void addTemClient(ChannelHandlerContext ctx) {
        Client client = new Client();
        client.setChannel(ctx);
        client.setLastUpTime(new Date());
        String clientId = getClientId(ctx);
        logger.info("添加临时客户端：" + clientId);
        ChannelHandlerContext channel = null;
        String s = getIP_Port(clientId)[0];
        logger.info("ip and port:" + s);
        for (String key : tempClientMap.keySet()) {
            if (s.equals(getIP_Port(key)[0])) {
                logger.info("新channel接入移除旧临时客户端：");
                channel = tempClientMap.get(key).getChannel();
                removeTemClient(channel);
                if (channel != null && channel.channel().isOpen()) {
                    channel.close();
                }
                channel = null;
            }
        }

        tempClientMap.put(clientId, client);
    }

    /**
     * 移除一个临时客户端
     *
     * @param channel
     */
    public static void removeTemClient(ChannelHandlerContext channel) {

        String clientId = getClientId(channel);
        Client client = tempClientMap.remove(clientId);
        if (client != null) {
            logger.info("------------移除临时客户端：" + clientId + "-------------------");
        }
    }

    /**
     * 添加一个客户端
     *
     * @param channel
     */
    public static void addClient(ChannelHandlerContext channel, TcpUser user) {

        String clientId = getClientId(channel);
        if (clientId == null) {
            return;
        }
        Client client = clientMap.get(clientId);
        if (client != null) {// 客户端已经登陆过了
            logger.info("客户端重复登陆");
        } else {
            client = tempClientMap.get(clientId);
            if (client != null) {
                removeTemClient(channel);
            } else {
                logger.error("channel在临时客户端及客户端库中都不存在");
                return;
            }
        }
        client.setChannel(channel);
        client.setLastUpTime(new Date());
        client.setUser(user);
        logger.info("------------添加客户端：" + clientId + "-------------------");
        clientMap.put(clientId, client);
        String[] ip_port = getIP_Port(clientId);
        //saveLog(client.getJrm(), (byte) 1, ip_port[0], Integer.parseInt(ip_port[1]));
    }

    /**
     * 移除一个客户端
     *
     * @param channel
     */
    public static void removeClient(ChannelHandlerContext channel) {
        String clientId = getClientId(channel);
        Client client = clientMap.remove(clientId);
        if (client != null) {
            logger.info("------------移除客户端：" + clientId + "-------------------");
            String[] ip_port = getIP_Port(clientId);
            //  saveLog(client.getJrm(), (byte) 0, ip_port[0], Integer.parseInt(ip_port[1]));
        }
    }

    /**
     * 移除未登陆客户端
     */
    public void checkTempClient() {
        Iterator<Map.Entry<String, Client>> it = tempClientMap.entrySet()
                .iterator();
        Client client = null;
        Date nowDate = new Date();
        ChannelHandlerContext channel = null;
        while (it.hasNext()) {
            Map.Entry<String, Client> entry = it.next();
            client = entry.getValue();
            if (nowDate.getTime() - client.getLastUpTime().getTime() >= getHeartbeatDelay() * 1000
                    ) {
                it.remove();
                channel = client.getChannel();
                if (channel.channel().isOpen()) {
                    logger.info("移除未登陆的客户端(超时连接)，连接name："
                            + getClientId(channel));
                    channel.close();
                }
                channel = null;
            }
        }
    }


    /**
     * 获取客户端通道
     */
    public  static Client getClientBySN(String sn) {
        Iterator<Map.Entry<String, Client>> it = clientMap.entrySet()
                .iterator();
        Client client = null;
        while (it.hasNext()) {
            Map.Entry<String, Client> entry = it.next();
            client = entry.getValue();
            if (client.getUser().getSn().equalsIgnoreCase(sn)) {

               return  client;
            }
        }
        return null;
    }
    /**
     * 移除登陆客户端
     */
    public void checkClient() {
        Iterator<Map.Entry<String, Client>> it = clientMap.entrySet()
                .iterator();
        Client client = null;
        Date nowDate = new Date();
        ChannelHandlerContext channel = null;
        while (it.hasNext()) {
            Map.Entry<String, Client> entry = it.next();
            client = entry.getValue();
            if (nowDate.getTime() - client.getLastUpTime().getTime() >= getHeartbeatDelay() * 1000
                    ) {
                removeClient(client.getChannel());
                channel = client.getChannel();
                if (channel.channel().isOpen()) {
                    logger.info("移除登陆的客户端(超过空闲时间)，连接name："
                            + getClientId(channel));
                    channel.close();
                }
                channel = null;
            }
        }
    }

    /**
     * 发送消息
     *
     * @param m
     * @param ctx
     */
    public static void sendData(BaseParam m, ChannelHandlerContext ctx) {

        if (ctx != null && ctx.channel().isOpen()) {
            ctx.write(m);
            ctx.flush();
            logger.debug("发送消息：" + m.toString());
        } else {
            logger.info("发送消息---channel断开或者为空：channel" + ctx);
        }
    }





    /**
     * 保存日志
     *
     * @param type
     */
    public static void saveLog(String jrm, byte type, String ip, int port) {
//		TcpClientLog tcpClientLog = new TcpClientLog();
//		tcpClientLog.setConnectype(type);
//		tcpClientLog.setXzqhdm(xzqhdm);
//		tcpClientLog.setTime(new Date());
//		tcpClientLog.setIp(ip);
//		tcpClientLog.setPort(port);
//		MsgQueue.getTcpLogqueue().add(tcpClientLog);
    }

    /**
     * 查询登陆的客户端
     *
     * @param ctx
     * @return
     */
    public static Client getClient(ChannelHandlerContext ctx) {
        String clientId = getClientId(ctx);
        if (clientMap.containsKey(clientId)) {
            return clientMap.get(clientId);
        }
        logger.info("没有找到该客户端:" + clientId);
        return null;
    }

    /**
     * 更新最后一次收到消息时间
     *
     * @param ctx
     * @param client
     */
    public static void setClientLastTime(ChannelHandlerContext ctx,
                                         Client client) {
        client.setLastUpTime(new Date());
        clientMap.put(getClientId(ctx), client);
    }

    /**
     * 获取clientId
     *
     * @param ctx
     * @return
     */
    public static String getClientId(ChannelHandlerContext ctx) {
        try {
            InetSocketAddress address = (InetSocketAddress) ctx.channel()
                    .remoteAddress();
            InetAddress inetAdd = address.getAddress();
            return inetAdd.getHostAddress() + ":"
                    + address.getPort() + "," + ctx.name();
        } catch (Exception e) {
            logger.error("取客户端id异常：", e);
            return null;
        }

    }

    /**
     * 获取ip和端口号：0：ip，1：端口
     *
     * @param clientId
     * @return
     */
    public static String[] getIP_Port(String clientId) {
        String[] ip_port = null;
        if (clientId != null && !"".equals(clientId)) {
            String[] clientString = clientId.split(",");
            if (clientString != null && clientString.length == 2) {
                ip_port = clientString[0].split(":");
            }
        }
        return ip_port;
    }

    public Integer getHeartbeatDelay() {
        return heartbeatDelay;
    }

    public void setHeartbeatDelay(Integer heartbeatDelay) {
        this.heartbeatDelay = heartbeatDelay;
    }

    public static ConcurrentHashMap<String, Client> getClientMap() {
        return clientMap;
    }

    public static void setClientMap(ConcurrentHashMap<String, Client> clientMap) {
        ClientManager.clientMap = clientMap;
    }
}
