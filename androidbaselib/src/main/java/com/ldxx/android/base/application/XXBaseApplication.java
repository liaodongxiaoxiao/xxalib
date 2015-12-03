package com.ldxx.android.base.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ldxx.android.base.runnable.IOnUiThreadRunner;

/**
 * Created by WangZhuo on 2015/7/2.
 */
public class XXBaseApplication extends Application implements IOnUiThreadRunner {
    private final String TAG = this.getClass().getSimpleName();

    private static XXBaseApplication application;


    private static Handler handler;
    private static IOnUiThreadRunner onUiThreadRunner;

    public static synchronized XXBaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        handler = new Handler(Looper.getMainLooper());

        onUiThreadRunner = this;
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        } else {
            Log.e(TAG, "no handler");
        }
    }
}
