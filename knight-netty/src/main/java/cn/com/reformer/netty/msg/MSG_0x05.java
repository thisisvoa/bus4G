package cn.com.reformer.netty.msg;

import cn.com.reformer.netty.bean.BaseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: （4）上传二维码
 * @author zhangjin
 * @create 2017-05-08
**/
public class MSG_0x05 extends BaseParam {

    private static final Logger logger = LoggerFactory.getLogger(MSG_0x05.class);
    private static final long serialVersionUID = 1L;

    private  String qrdata;
    private Integer result;


    public String getQrdata() {
        return qrdata;
    }

    public void setQrdata(String qrdata) {
        this.qrdata = qrdata;
    }

    public byte getCmd() {
        return MessageID.MSG_0x05;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MSG_0x05{" +
                "qrdata='" + qrdata + '\'' +
                ", result=" + result +
                '}';
    }
}
