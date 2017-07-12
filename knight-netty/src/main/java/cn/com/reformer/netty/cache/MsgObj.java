/**
 * Project Name:main
 * File Name:MsgObj.java
 * Package Name:com.hdsx.taxi.driver.cq.tcp.cache
 * Date:2014骞?4鏈?16鏃ヤ笂鍗?11:16:54
 * Copyright (c) 2014, sid Jenkins All Rights Reserved.
 */

package cn.com.reformer.netty.cache;


import cn.com.reformer.netty.bean.BaseParam;

import java.io.Serializable;
import java.util.Date;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: map中存的对象
 * @author zhangjin
 * @create 2017-05-08
**/
public class MsgObj implements Serializable {


    private static final long serialVersionUID = 1L;
    Date sendTime;
    Date createTime;
    int sendedCount;

    BaseParam msg;

    public MsgObj(BaseParam msg) {
        this.sendTime = new Date();
        this.createTime = new Date();
        sendedCount = 1;
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getSendedCount() {
        return sendedCount;
    }

    public void setSendedCount(int sendedCount) {
        this.sendedCount = sendedCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BaseParam getMsg() {
        return msg;
    }

    public void setMsg(BaseParam msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgObj [sendTime=" + sendTime + ", sendedCount="
                + sendedCount + ", msg=" + msg + "]";
    }
}