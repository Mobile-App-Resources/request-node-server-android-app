package com.maraujo.requestproject.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maraujo.requestproject.sync.AbstractProxy;
import com.maraujo.requestproject.sync.LruBitmapCache;
import com.maraujo.requestproject.utils.DateDeserializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maraujo on 6/15/17.
 */

public class App extends Application {


    private static App instance;

    private LruBitmapCache lruBitmapCache;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private List<AbstractProxy> proxies;
    private Gson gson;



    @Override
    public void onCreate() {
        super.onCreate();

        App.instance = this;
    }

    public static App getInstance() {
        return App.instance;
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    /*.setDateFormat("MM/dd/yyyy")*/
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();
        }

        return gson;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (lruBitmapCache == null) {
            lruBitmapCache = new LruBitmapCache();
        }

        return lruBitmapCache;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, getLruBitmapCache());
        }

        return this.imageLoader;
    }

    public List<AbstractProxy> getProxies() {
        if (proxies == null) {
            proxies = new ArrayList<>();
        }

        return proxies;
    }

    public <T> void addToRequestQueue(final AbstractProxy proxy, final View view, final Request<T> request) {
        if (isOnline()) {
            request.setTag(proxy.getTag());

            getProxies().add(proxy);
            getRequestQueue().add(request);
        } else {
            Snackbar.make(view, "No internet connection", Snackbar.LENGTH_LONG)
                    .setAction("Try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addToRequestQueue(proxy, view, request);
                        }
                    }).show();
        }
    }

    public void cancelRequests(String tag) {
        if (tag == null) {
            return;
        }

        getRequestQueue().cancelAll(tag);

        List<AbstractProxy> proxies = new ArrayList<>();

        proxies.addAll(getProxies());

        for (AbstractProxy proxy : proxies) {
            if (proxy.getTag().equals(tag)) {
                removeProxy(proxy);
            }
        }
    }

    public void removeProxy(AbstractProxy proxy) {
        proxy.destroy();

        getProxies().remove(proxy);
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
