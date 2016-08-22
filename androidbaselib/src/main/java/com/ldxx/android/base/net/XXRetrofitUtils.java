//package com.ldxx.android.base.net;
//
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by ldxx on 16/8/20.
// */
//
//public class XXRetrofitUtils {
//
//    public static Retrofit getRetrofit(String baseUrl){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl).client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return  retrofit;
//    }
//
//    private static OkHttpClient getOkHttpClient() {
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.connectTimeout(10, TimeUnit.SECONDS);
//        httpClient.readTimeout(10,TimeUnit.SECONDS);
//        httpClient.writeTimeout(10,TimeUnit.SECONDS);
//
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                Request request = original.newBuilder()
//                        .header("User-Agent", "Your-App-Name")
//                        .header("Accept", "application/vnd.yourapi.v1.full+json")
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });
//        return httpClient.build();
//    }
//
//    public static RequestBody parseBody(String body){
//        return RequestBody.create(okhttp3.MediaType
//                        .parse("application/json; charset=utf-8"),
//                body);
//    }
//}
