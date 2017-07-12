package cn.com.reformer.netty.cache;

import cn.com.reformer.netty.bean.BaseParam;
import cn.com.reformer.netty.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: 消息缓存
 * @author zhangjin
 * @create 2017-05-08
**/
public class MsgCache {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(MsgCache.class);

    static MsgCache obj;

    public static MsgCache getInstance() {
        if (obj == null)
            obj = new MsgCache();
        return obj;
    }

    ConcurrentHashMap cache = new ConcurrentHashMap();

    public MsgCache() {
    }

    /**
     * 存放消息
     *
     * @param msg
     */
    public void put(BaseParam msg) {

        //指定部分不需要缓存的消息
        String key = getMsgKey(msg);

        MsgObj m = this.get(key);
        if (m == null)
            m = new MsgObj(msg);
        else {
            m.setSendTime(new Date());
            m.setSendedCount(m.getSendedCount() + 1);
        }
        BaseParam am = m.getMsg();
        this.cache.put(getMsgKey(am), msg);

        if (logger.isDebugEnabled()) {
            logger.debug("消息加入缓存！消息key:" + key);
        }
    }

    /**
     * remove:移除消息
     *
     * @param key
     * @author sid
     */
    public void remove(String key) {
        this.cache.remove(key);
    }

    /**
     * 根据返回消息 流水号
     *
     * @param key
     * @return
     */
    public BaseParam getMsg(String key) {
        Object e = this.cache.get(key);
        MsgObj m = e == null ? null : (MsgObj) e;
        return m == null ? null : m.getMsg();

    }

    /**
     * 根据返回消息 流水号
     *
     * @param key
     * @return
     */
    public MsgObj get(String key) {
        Object e = this.cache.get(key);
        return e == null ? null : (MsgObj) e;

    }

    /**
     * cleanAndgetResendMsg:(清理缓存中的消息，同时将需要重新发送的消息返回).
     *
     * @param minInterval 最小间隔时间，以秒为单位
     * @param maxCount    最大重发次数
     * @param maxTime     最长缓存时间(小时)
     * @return
     * @author sid
     */
    @SuppressWarnings("unchecked")
    public List<MsgObj> cleanAndgetResendMsg(int minInterval, int maxCount,
                                             int maxTime) {
        List<MsgObj> list = new ArrayList<MsgObj>();

        Enumeration<String> keys = cache.keys();
        Date date = new Date();
        while (keys.hasMoreElements()) {
            MsgObj obj = MsgCache.getInstance().get(keys.nextElement());
            Date endtime = DateUtils.addDateHour(obj.getCreateTime(),
                    maxTime);

            if (obj.getSendedCount() < maxCount
                    && endtime.getTime() > date.getTime()) {
                long lasttime = obj.getSendTime().getTime();
                long now = System.currentTimeMillis();
                if ((now - lasttime) > (minInterval * 1000)) {
                    list.add(obj);
                }
            } else
                MsgCache.getInstance().remove(keys.nextElement());
        }
        return list;

    }


    /**
     * 根据消息内容生成消息id
     *
     * @param m
     * @return
     */
    public static String getMsgKey(BaseParam m) {
        return m.getCmd() + ";" + m.getNonce();
    }

}
