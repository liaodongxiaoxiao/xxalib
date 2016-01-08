package com.ldxx.android.base.net;

/**
 * Created by LDXX on 2015/12/9.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class Recommend {
    private String issue;
    private String red_balls;
    private String blue_balls;

    public String getBlue_balls() {
        return blue_balls;
    }

    public void setBlue_balls(String blue_balls) {
        this.blue_balls = blue_balls;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getRed_balls() {
        return red_balls;
    }

    public void setRed_balls(String red_balls) {
        this.red_balls = red_balls;
    }

    @Override
    public String toString() {
        return "Recommend{" +
                "blue_balls='" + blue_balls + '\'' +
                ", issue='" + issue + '\'' +
                ", red_balls='" + red_balls + '\'' +
                '}';
    }
}
