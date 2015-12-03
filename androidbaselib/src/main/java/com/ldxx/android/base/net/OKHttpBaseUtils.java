package com.ldxx.android.base.net;

import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by LDXX on 2015/12/2.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
class OKHttpBaseUtils {
    static final OkHttpClient client = new OkHttpClient();

    static {
        client.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /*public static final MediaType JSON_TYPE
            = MediaType.parse("application/json; charset=utf-8");*/

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void get(String url, Callback callback) {
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = client.newCall(request);
        //请求加入调度
        call.enqueue(callback);
    }


    public static String post(String url, String key, String value) throws IOException {
        ResponseBody body = postBase(url, key, value);
        return body.string();
    }


    public static ResponseBody postBase(String url, String key, String value) throws IOException {
        FormEncodingBuilder formBody = new FormEncodingBuilder();
        if (!TextUtils.isEmpty(key) && value != null) {
            formBody.add(key, value);
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }

    public static ResponseBody postBase(String url, Map<String, Object> map) throws IOException {
        FormEncodingBuilder formBody = new FormEncodingBuilder();
        if (map != null && map.size() > 0) {
            Iterator<String> iterator = map.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                formBody.add(key, String.valueOf(map.get(key)));
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }
}
