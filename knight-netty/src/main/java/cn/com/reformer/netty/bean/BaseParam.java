package cn.com.reformer.netty.bean;

import cn.com.reformer.netty.msg.MessagePacket;

public     class BaseParam  implements MessagePacket{
   
	private byte cmd;//类型  1心跳 2 遥控开  3 查询状态
	private  String sn; //自己的id
	private  byte version;
	private String nonce;
	private String sign;
	private  String sentid; //发送的用户

    private byte type;
	private  String sentMsg;  // 发送的消息

	public byte getCmd() {
		return cmd;
	}
	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSentid() {
		return sentid;
	}
	public void setSentid(String sentid) {
		this.sentid = sentid;
	}
	public String getSentMsg() {
		return sentMsg;
	}
	public void setSentMsg(String sentMsg) {
		this.sentMsg = sentMsg;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BaseParam{" +
				"cmd=" + cmd +
				", sn='" + sn + '\'' +
				", version=" + version +
				", nonce='" + nonce + '\'' +
				", sign='" + sign + '\'' +
				", sentid='" + sentid + '\'' +
				", type=" + type +
				", sentMsg='" + sentMsg + '\'' +
				'}';
	}
}
