package com.xywg.pm.projectmanager.bean.weather;

import java.io.Serializable;

/**
 * Created by zhangyinlei on 2018/7/16
 */
public class WeatherJsonBean implements Serializable {

    private String weather;
    private String tem;
    private String wind;

    public WeatherJsonBean(String weather, String tem, String wind) {
        this.weather = weather;
        this.tem = tem;
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
