package com.ldxx.xxalib.beans;

/**
 * Created by LDXX on 2016/2/4.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class User {
    private String pid;
    private String userName;
    private int age;
    private String password;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
