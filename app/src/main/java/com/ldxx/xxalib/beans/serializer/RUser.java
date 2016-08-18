package com.ldxx.xxalib.beans.serializer;

/**
 * Created by wangzhuo-neu on 2016/8/15.
 */

public class RUser {

    /**
     * user_id : 8ded2fc0-5b17-4810-accb-bece33b73512
     * user_fullname : manageh2
     * user_account : manageh2
     * user_password : xMpCOKC5I4INzFCab3WEmw==
     * user_account_locked : null
     * user_password_changed : null
     * user_account_created : null
     * user_account_enabled : null
     * user_office_id : null
     * user_description : null
     * user_email : null
     * user_mobiletel : null
     * user_unit_id : 402832ba3102696c013102871b9c00bc
     * user_dept_id : null
     * user_officetel : null
     */

    private String user_id;
    private String user_fullname;
    private String user_account;
    private String user_password;
    private String user_unit_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_unit_id() {
        return user_unit_id;
    }

    public void setUser_unit_id(String user_unit_id) {
        this.user_unit_id = user_unit_id;
    }

    @Override
    public String toString() {
        return "RUser{" +
                "user_id='" + user_id + '\'' +
                ", user_fullname='" + user_fullname + '\'' +
                ", user_account='" + user_account + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_unit_id='" + user_unit_id + '\'' +
                '}';
    }
}
