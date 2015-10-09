package com.ldxx.android.base.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ldxx.android.base.common.BitmapCache;
import com.ldxx.android.base.runnable.IOnUiThreadRunner;

/**
 * Created by WangZhuo on 2015/7/2.
 */
public class XXBaseApplication extends Application implements IOnUiThreadRunner {
    private final String TAG = this.getClass().getSimpleName();

    private static XXBaseApplication application;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
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

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        if (requestQueue == null) {
            getRequestQueue();
        }

        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, new BitmapCache());
        }
        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
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
