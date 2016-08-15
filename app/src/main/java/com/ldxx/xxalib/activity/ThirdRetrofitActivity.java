package com.ldxx.xxalib.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.IUser;
import com.ldxx.xxalib.beans.serializer.RUser;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdRetrofitActivity extends AppCompatActivity {
    private static final String TAG = "ThirdRetrofitActivityAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_retrofit_activity);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" http://192.168.145.150:8080/phis/service/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        IUser userBiz = retrofit.create(IUser.class);
        RequestBody body= RequestBody.create(okhttp3.MediaType
                .parse("application/json; charset=utf-8"),
                "{\"unitid\":\"402832ba3102696c013102871b9c00bc\"}");
        Call<List<RUser>> call = userBiz.getUsersOfCurrentUnit(body);
        call.enqueue(new Callback<List<RUser>>() {
            @Override
            public void onResponse(Call<List<RUser>> call, Response<List<RUser>> response) {
                Log.e(TAG, "normalGet:" + response.body() + "");
            }

            @Override
            public void onFailure(Call<List<RUser>> call, Throwable t) {

            }
        });

    }

    private OkHttpClient getOkHttpClient(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("User-Agent", "Your-App-Name")
                        .header("Accept", "application/vnd.yourapi.v1.full+json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }

}
