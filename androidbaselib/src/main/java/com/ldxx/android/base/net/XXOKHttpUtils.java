package com.ldxx.android.base.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ldxx.utils.StringUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by LDXX on 2015/12/3.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public final class XXOKHttpUtils {
    static final OkHttpClient client = new OkHttpClient();

    static {
        client.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    private Builder builder;

    /**
     * 私有构造器
     *
     * @param builder 参数对象
     */
    private XXOKHttpUtils(Builder builder) {
        this.builder = builder;
    }

    /**
     * 获取http请求对象
     *
     * @param <T> 对象类型
     * @return 返回指定类型的对象
     * @throws IOException       IO异常
     * @throws XXOKHttpException 参数异常
     */
    public <T> T getObject(Class<T> classOfT) throws IOException, XXOKHttpException {
        Gson gson = new Gson();
        ResponseBody body = post();
        return gson.fromJson(body.string(), classOfT);
    }

    /**
     * 获取http请求的list集合
     *
     * @param <T> 对象类型
     * @return 返回指定类型对象的集合
     * @throws IOException       IO异常
     * @throws XXOKHttpException 请求参数设置异常
     */
    public <T> List<T> getList(Class<T> classOfT) throws IOException, XXOKHttpException {

        ResponseBody body = post();
        Gson gson = new Gson();
        List<T> lst = new ArrayList<>();
        JsonArray array = new JsonParser().parse(body.charStream()).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(gson.fromJson(elem, classOfT));
        }
        return lst;
    }

    /**
     * 获取http请求返回的字符串
     *
     * @return 返回请求返回的String字符串
     * @throws IOException       IO异常
     * @throws XXOKHttpException 参数设置异常
     */
    public String getString() throws IOException, XXOKHttpException {
        ResponseBody body = post();
        return body.string();
    }

    /**
     * post 方法
     *
     * @return 返回请求的ResponseBody对象
     * @throws IOException       IO异常
     * @throws XXOKHttpException 参数设置异常
     */
    private ResponseBody post() throws IOException, XXOKHttpException {

        Request.Builder requestBuilder = new Request.Builder();
        if (StringUtils.isEmpty(builder.url)) {
            throw new XXOKHttpException("URL cannot be empty. ");
        }
        requestBuilder.url(builder.url);
        if (builder.header != null) {
            requestBuilder.headers(builder.header);
        }
        if (builder.body != null) {
            requestBuilder.post(builder.body);
        }
        Response response = client.newCall(requestBuilder.build()).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }

    /**
     * OKHttp 参数封装类
     */
    public static class Builder {
        private String url;
        private Headers header;
        private RequestBody body;

        public Builder() {

        }

        /**
         * body单一参数设置方法
         *
         * @param key   参数的key
         * @param value 参数的值
         * @return 返回构造类对象
         */
        public Builder body(String key, String value) {
            FormEncodingBuilder formBody = new FormEncodingBuilder();
            formBody.addEncoded(key, value);
            this.body = formBody.build();
            return this;
        }

        /**
         * body多参数设置方法
         *
         * @param values 参数是一个Map<String key,String value> 形式
         * @return 返回构造类对象
         */
        public Builder bodys(Map<String, String> values) {
            if (values != null && !values.isEmpty()) {
                FormEncodingBuilder formBody = new FormEncodingBuilder();
                Iterator<String> it = values.keySet().iterator();
                String key;
                while (it.hasNext()) {
                    key = it.next();
                    formBody.add(key, values.get(key));
                }
                this.body = formBody.build();
            }
            return this;
        }

        /**
         * body设置方法
         *
         * @param body RequestBody对象
         * @return 返回构造类对象
         */
        public Builder body(RequestBody body) {
            this.body = body;
            return this;
        }

        /**
         * 设置请求的URL
         *
         * @param url URL地址
         * @return 返回构造类对象
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 构造XXOKHttpPostUtils类的实例
         *
         * @return XXOKHttpPostUtils的实例
         */
        public XXOKHttpUtils build() {
            return new XXOKHttpUtils(this);
        }

        /**
         * 设置OKhttp单参数的请求header
         *
         * @param key   参数的键
         * @param value 参数的值
         * @return 返回构造类对象
         */
        public Builder header(String key, String value) {
            Headers.Builder builder = new Headers.Builder();
            builder.add(key, value);
            this.header = builder.build();
            return this;
        }

        /**
         * 设置OKhttp多参数的请求header
         *
         * @param headers 参数，Map<String key,String value>
         * @return 返回构造类对象
         */
        public Builder headers(Map<String, String> headers) {
            if (headers != null && !headers.isEmpty()) {
                Iterator<String> it = headers.keySet().iterator();
                String key;
                Headers.Builder builder = new Headers.Builder();

                while (it.hasNext()) {
                    key = it.next();
                    builder.add(key, headers.get(key));
                }
                this.header = builder.build();
            }
            return this;
        }

        /**
         * 设置OKhttp请求header
         *
         * @param header Headers对象
         * @return 返回构造类对象
         */
        public Builder header(Headers header) {
            this.header = header;
            return this;
        }


    }

}
