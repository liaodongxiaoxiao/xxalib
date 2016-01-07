package com.ldxx.xxalib.beans;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LDXX on 2016/1/5.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class WeatherIndex extends RealmObject {

    /**
     * name : 感冒指数
     * code : gm
     * index :
     * details : 昼夜温差大，且空气湿度较大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
     * otherName :
     */

    private String name;
    private String code;
    private String index;
    private String details;
    private String otherName;
    private String cityid;
    private Date date;
    @PrimaryKey
    private String key;

    public String getKey() {
        return this.cityid+""+this.date.getTime();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getIndex() {
        return index;
    }

    public String getDetails() {
        return details;
    }

    public String getOtherName() {
        return otherName;
    }
}
