package com.ldxx.xxalib.beans;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by LDXX on 2015/10/30.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
@Table(name = "XXWeather")
public class XXWeather {

    /**
     * date : 2015-10-30
     * week : 星期五
     * curTemp : 4℃
     * aqi : 65
     * fengxiang : 西北风
     * fengli : 3-4级
     * hightemp : 8℃
     * lowtemp : -6℃
     * type : 晴
     */
    @Id
    private String date;
    private String week;
    private String curTemp;
    private String aqi;
    private String fengxiang;
    private String fengli;
    private String hightemp;
    private String lowtemp;
    private String type;
    private String savedate;

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setCurTemp(String curTemp) {
        this.curTemp = curTemp;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
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

    public String getCurTemp() {
        return curTemp;
    }

    public String getAqi() {
        return aqi;
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

    public String getSavedate() {
        return savedate;
    }

    public void setSavedate(String savedate) {
        this.savedate = savedate;
    }
}
