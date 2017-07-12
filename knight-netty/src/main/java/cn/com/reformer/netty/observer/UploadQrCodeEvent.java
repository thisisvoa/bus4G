package cn.com.reformer.netty.observer;

import java.util.Observable;

/**
 * Created by Administrator on 2017/5/18.
 */
public class UploadQrCodeEvent extends Observable {

    private String qrCode;

    public UploadQrCodeEvent() {
    }

    public UploadQrCodeEvent(String qrCode) {
        this.qrCode = qrCode;
        setChanged();
        notifyObservers();
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
         setChanged();
        notifyObservers();
    }
}
