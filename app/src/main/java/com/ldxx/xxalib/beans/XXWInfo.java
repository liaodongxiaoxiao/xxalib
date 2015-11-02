package com.ldxx.xxalib.beans;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * Created by LDXX on 2015/10/30.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
@Table(name = "XXWInfo")
public class XXWInfo {

    /**
     * name : 感冒指数
     * code : gm
     * index :
     * details : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
     * otherName :
     */
    @Id
    private int id;
    private String date;
    private String name;
    private String code;
    @Transient
    private String index;
    private String details;
    private String otherName;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
