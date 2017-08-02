package com.maraujo.requestproject.sync;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.maraujo.requestproject.utils.ImageUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Mateus Emanuel Araújo on 04/12/15.
 * teusemanuel@gmail.com
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    private LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        return maxMemory / 8;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        if (url.contains("file://")) {
            String pathImage = null;
            try {
                pathImage = URLDecoder.decode(url.substring(url.indexOf("file://") + 7), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e("LruBitmapCache", e.getLocalizedMessage(), e);
            }

            return ImageUtils.fixOrientation(pathImage);
        } else {
            return get(url);
        }
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
