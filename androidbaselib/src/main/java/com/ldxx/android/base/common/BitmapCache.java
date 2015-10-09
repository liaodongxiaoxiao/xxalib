package com.ldxx.android.base.common;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by WANGZHUO on 2015/4/17.
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapCache() {
        this(getDefaultLruCacheSize());
    }

    public BitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

}
