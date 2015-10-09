package com.ldxx.android.base.view;

/**
 * Created by WangZhuo on 2015/6/10.
 */
public class DropDownMenuData {
    private String key;
    private String value;

    public DropDownMenuData() {
    }

    public DropDownMenuData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}