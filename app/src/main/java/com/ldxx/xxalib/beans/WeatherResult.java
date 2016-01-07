package com.ldxx.xxalib.beans;


/**
 * Created by LDXX on 2016/1/4.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class WeatherResult {

    /**
     * date : 2016-01-05
     * week : 星期二
     * fengxiang : 北风
     * fengli : 3-4级
     * hightemp : 1℃
     * lowtemp : -6℃
     * type : 晴
     */
    private String date;
    private String week;
    private String fengxiang;
    private String fengli;
    private String hightemp;
    private String lowtemp;
    private String type;
    private String curTemp;
    private String aqi;
    private String city;
    private String cityid;
    private String updatetime;

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(String curTemp) {
        this.curTemp = curTemp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getWeek() {
        return week;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public String getHightemp() {
        return hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public String getType() {
        return type;
    }
}
