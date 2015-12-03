package com.ldxx.android.base.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by LDXX on 2015/12/3.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public final class OKHttpPostUtils<T> {
    private final Class<T> tClass;

    private OKHttpPostUtils(Class<T> tClass) {
        this.tClass = tClass;
    }

    public static OKHttpPostUtils get(Class tClass) {
        return new OKHttpPostUtils<>(tClass);
    }


    public T objectWithPost(String url, String key, String value) throws IOException {
        Gson gson = new Gson();
        ResponseBody body = OKHttpBaseUtils.postBase(url, key, value);
        return gson.fromJson(body.charStream(), this.tClass);
    }

    public List<T> listWithPost(String url, String key, String value) throws IOException {
        Gson gson = new Gson();
        ResponseBody body = OKHttpBaseUtils.postBase(url, key, value);
        return gson.fromJson(body.charStream(), new TypeToken<List<T>>() {
        }.getType());
    }

    public T objectWithPost(String url) throws IOException {
        return objectWithPost(url, null, null);
    }

    public List<T> listWithPost(String url) throws IOException {
        return listWithPost(url, null, null);
    }

    public T objectWithPost(String url, Map<String, Object> values) throws IOException {
        Gson gson = new Gson();
        ResponseBody body = OKHttpBaseUtils.postBase(url, values);
        return gson.fromJson(body.charStream(), this.tClass);
    }

    public List<T> listWithPost(String url, Map<String, Object> values) throws IOException {
        Gson gson = new Gson();
        ResponseBody body = OKHttpBaseUtils.postBase(url, values);
        return gson.fromJson(body.charStream(), new TypeToken<List<T>>() {
        }.getType());
    }
}
