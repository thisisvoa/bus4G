package cn.com.reformer.netty.bean;

import java.util.HashMap;


/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description:  Tcp用户实体
 * @author zhangjin
 * @create 2017-05-08
**/
public class TcpUser extends BaseBean {


    private String sn;

    private UserType type;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public HashMap<String, Object> getMapId() {
        HashMap<String, Object> mapId = new HashMap<String, Object>();
        mapId.put("sn", this.sn);
        return mapId;
    }


    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }


}
