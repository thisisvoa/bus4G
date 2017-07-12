package cn.com.reformer.netty.msg;

import cn.com.reformer.netty.bean.BaseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: （4）上传状态（变化时）
 * @author zhangjin
 * @create 2017-05-08
**/
public class MSG_0x04 extends BaseParam {

    private static final Logger logger = LoggerFactory.getLogger(MSG_0x04.class);
    private static final long serialVersionUID = 1L;


    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public byte getCmd() {
        return MessageID.MSG_0x04;
    }


}
