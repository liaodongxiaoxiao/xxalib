package com.ldxx.android.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PACKAGE_NAME = this.getPackageName();
    }

    /*public ActionBar initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return actionBar;
    }*/
}
