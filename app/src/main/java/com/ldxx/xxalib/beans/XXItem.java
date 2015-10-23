package com.ldxx.xxalib.beans;

/**
 * Created by LDXX on 2015/10/21.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXItem {
    private String title;
    private String description;
    private String value;

    public XXItem() {
    }

    public XXItem(String title,String description, String value) {
        this.description = description;
        this.title = title;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
