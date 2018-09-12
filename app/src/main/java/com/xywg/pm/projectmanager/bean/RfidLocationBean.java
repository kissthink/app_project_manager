package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;

/**
 * Created by zhangyinlei on 2018/7/17
 */
public class RfidLocationBean implements Serializable {

    private String rfid;//id

    private double latitude;//纬度

    private double longitude;//经度

    public RfidLocationBean(String rfid, double latitude, double longitude) {
        this.rfid = rfid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
