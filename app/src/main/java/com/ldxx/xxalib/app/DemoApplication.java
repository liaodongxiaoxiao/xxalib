package com.ldxx.xxalib.app;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ldxx.android.base.application.XXBaseApplication;

/**
 * Created by wangzhuo-neu on 2017/1/20.
 */

public class DemoApplication extends XXBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
