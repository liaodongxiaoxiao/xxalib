package com.ldxx.xxalib.beans;

import com.ldxx.xxalib.beans.serializer.RUser;


import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by wangzhuo-neu on 2016/8/15.
 */

public interface IUser {


    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getUsersOfCurrentUnit")
    Call<List<RUser>> getUsersOfCurrentUnit(@Body RequestBody route);
}
