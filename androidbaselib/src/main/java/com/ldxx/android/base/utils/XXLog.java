package com.ldxx.android.base.utils;


import com.ldxx.android.base.BuildConfig;

/**
 * Created by WANGZHUO on 2015/4/15.
 * print log
 */
public class XXLog {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private XXLog() {
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, msg, tr);
        }
    }
}
