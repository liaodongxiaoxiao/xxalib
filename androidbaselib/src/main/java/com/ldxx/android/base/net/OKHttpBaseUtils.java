package com.ldxx.android.base.net;

import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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
public class OKHttpBaseUtils {
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
        return postBase(url, null, formBody.build());
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
        return postBase(url, null, formBody.build());
    }


    public static ResponseBody postBaseWithHeaders(String url, Headers headers, RequestBody body) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (headers != null && headers.size() > 0) {
            builder.headers(headers);
        }
        if (body != null) {
            builder.post(body);
        }
        Response response = client.newCall(builder.build()).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }

    private static ResponseBody postBase(String url, Headers headers, RequestBody body) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (headers != null && headers.size() > 0) {
            builder.headers(headers);
        }
        if (body != null) {
            builder.post(body);
        }
        Response response = client.newCall(builder.build()).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }

    public static ResponseBody postBase(String url, Map<String, String> headers, Map<String, String> body) throws IOException {
        FormEncodingBuilder formBody = new FormEncodingBuilder();
        if (body != null && body.size() > 0) {
            Iterator<String> iterator = body.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                formBody.addEncoded(key, String.valueOf(body.get(key)));
            }
        }
        Headers.Builder builder = new Headers.Builder();
        if (headers != null && headers.size() > 0) {
            Iterator<String> iterator = headers.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                builder.add(key, String.valueOf(headers.get(key)));
            }
        }
        return postBase(url, builder.build(), formBody.build());
    }


}
