package cn.com.reformer.netty.util;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer 
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class Constants {

    /**
     * 协议头字节长度29
     * <p/>
     * B）包序号（2位）+间隔符  3
     * C）MAC（12位）+间隔符  13
     * D）SN（9位）+间隔符  10
     * E）CMD（2位）+间隔符 3
     * <p/>
     * 3+13+10+3=29
     */
    public static final int HEAD_LENGTH = 28;
    /**
     * 标志位 (长度
     */
    public static final int SIGN_STAR_LENGTH = 1;
    /**
     * 尾标识)长度
     */
    public static final int SIGN_END_LENGTH = 1;

    /**
     * 标识头
     */
    public static final byte SIGN_START = 0x28;

    /**
     * 标识尾
     */
    public static final byte SIGN_END = 0x29;


    /**
     * 逗号分隔符
     */
    public static final byte SIGN_COMMA = 0x2C;

    public static final byte FAIL_STATE = (byte) 1;

    public static final byte SUCCESS_STATE = (byte) 0;


}
