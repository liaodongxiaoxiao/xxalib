package com.ldxx.xxalib.beans;

import com.ldxx.xxalib.contentprovider.NewsContentProvider;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by WANGZHUO on 2015/4/17.
 */
@Table(name= NewsContentProvider.TABLE_NAME)
public class XXNewsInfo {

    private String pid;
    @Id
    private String image_src;
    private String title;
    private String url;
    private String create_time;

    public XXNewsInfo(String create_time, String image_src, String pid, String title, String url) {
        this.create_time = create_time;
        this.image_src = image_src;
        this.pid = pid;
        this.title = title;
        this.url = url;
    }

    public XXNewsInfo() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
