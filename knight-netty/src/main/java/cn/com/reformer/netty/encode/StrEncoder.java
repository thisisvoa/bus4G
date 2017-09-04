package cn.com.reformer.netty.encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

/**
 * Created by zhang on 2017/9/4.
 */
public class StrEncoder extends StringEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        if(msg.length() != 0) {
            String temp= (String) msg;
            temp+="\r";
            super.encode(ctx, temp, out);
        }
    }
}
