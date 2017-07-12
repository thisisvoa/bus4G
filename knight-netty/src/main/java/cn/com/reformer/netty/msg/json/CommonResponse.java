package cn.com.reformer.netty.msg.json;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class CommonResponse {

    private short seq;

    private byte state;


    private String msg = "";


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public short getSeq() {
        return seq;
    }

    public void setSeq(short seq) {
        this.seq = seq;
    }
}
