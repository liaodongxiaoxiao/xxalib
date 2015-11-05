package com.ldxx.xxalib.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LDXX on 2015/11/4.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXDateData implements Parcelable {
    private String d_id;
    private String d_data;

    public XXDateData(String d_id, String d_data) {
        this.d_id = d_id;
        this.d_data = d_data;
    }

    public XXDateData() {
    }

    protected XXDateData(Parcel in) {
        d_id = in.readString();
        d_data = in.readString();
    }

    public static final Creator<XXDateData> CREATOR = new Creator<XXDateData>() {
        @Override
        public XXDateData createFromParcel(Parcel in) {
            return new XXDateData(in);
        }

        @Override
        public XXDateData[] newArray(int size) {
            return new XXDateData[size];
        }
    };

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getD_data() {
        return d_data;
    }

    public void setD_data(String d_data) {
        this.d_data = d_data;
    }

    @Override
    public String toString() {
        return "{" +
                "d_id='" + d_id + '\'' +
                " ,d_data='" + d_data + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(d_id);
        dest.writeString(d_data);
    }
}
