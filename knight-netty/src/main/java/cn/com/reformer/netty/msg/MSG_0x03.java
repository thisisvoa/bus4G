package cn.com.reformer.netty.msg;

import cn.com.reformer.netty.bean.BaseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: 开门指令
 * @author zhangjin
 * @create 2017-05-08
**/
public class MSG_0x03 extends BaseParam {

    private static final Logger logger = LoggerFactory.getLogger(MSG_0x03.class);
    private static final long serialVersionUID = 1L;




    public byte getCmd() {
        return MessageID.MSG_0x03;
    }


}
