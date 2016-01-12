package com.ldxx.xxalib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ldxx.android.base.view.DViewGridView;
import com.ldxx.xxalib.R;

import java.io.File;
import java.net.URISyntaxException;

public class CustomCircleViewActivity extends AppCompatActivity {
    DViewGridView gridView;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_circle_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        gridView = (DViewGridView) findViewById(R.id.gr);
        //<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
        //<uses-permission android:name="android.permission.READ_SMS" />

        tv = (TextView) findViewById(R.id.phone_number);
        //TelephonyManager   telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //String number =telephonyManager.getLine1Number();
        //if(TextUtils.isEmpty(number)){
        tv.setText("未获取的本机号码");
        //}else {
        //    tv.setText("本机号码："+number);
        //}
    }

    public void addBall(View view) {
        gridView.addText("国");
    }

    public void useBaidu(View view) {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:41.813914,123.440475|name:卓越大厦&destination=桃仙机场&mode=driving®ion=沈阳&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (isInstallByread("com.baidu.BaiduMap")) {
                startActivity(intent); //启动调用
                Log.e("GasStation", "百度地图客户端已经安装");
            } else {
                Log.e("GasStation", "没有安装百度地图客户端");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    public void useTengxun(View view) {
        try {
            // 卓越大厦：123.440475,41.813914
            // 桃仙机场：123.500902,41.638886
            String drivePlan = "qqmap://map/routeplan?type=drive&" +
                    "from=卓越大厦&fromcoord=41.813914,123.440475&to=桃仙机场&" +
                    "tocoord=41.638886,123.500902&policy=1&referer=" +
                    getResources().getString(R.string.app_name);

            Intent intent = Intent.getIntent(drivePlan);
            startActivity(intent); //启动调用

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
