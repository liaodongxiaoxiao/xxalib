package com.ldxx.xxalib.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ldxx.android.base.bean.XXPM25;
import com.ldxx.android.base.net.XXOKHttpUtils;
import com.ldxx.android.base.view.JsonTextView;
import com.ldxx.xxalib.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomJsonViewActivity extends AppCompatActivity {
    private static final String TAG = "CustomJsonViewActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.pm)
    JsonTextView pmJson;
    public static final String URL = "http://apis.baidu.com/apistore/aqiservice/aqi?city=沈阳";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_json_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.fab)
    void fabButtonClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.btn_pm)
    void pmButtonClick() {
        ProgressDialog dialog = ProgressDialog.show(this, "", "正在拼命获取数据中...");
        new Thread(new PMThread(dialog)).start();

    }

    class PMThread implements Runnable {
        private ProgressDialog dialog;

        public PMThread(ProgressDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void run() {
            XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
            //
            builder.obj(XXPM25.class)
                    .url(URL)
                    .header("apikey", "d6e91c2b841ef37858964106aa83749c");
            XXOKHttpUtils utils = builder.build();
            try {
                final XXPM25 pm = utils.getObject();
                //System.out.print(pm.toString());
                Log.e(TAG, "run: " + pm.toString());
                Thread.sleep(2000L);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pmJson.setText(pm.toString());
                    }
                });


            } catch (Exception e) {
                Log.e(TAG, "run: " + e.getMessage(), e);
            }

            dialog.dismiss();
        }
    }

}
