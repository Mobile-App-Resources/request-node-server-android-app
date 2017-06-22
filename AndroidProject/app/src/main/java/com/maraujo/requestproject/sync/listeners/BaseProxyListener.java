package com.maraujo.requestproject.sync.listeners;

import android.support.v4.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.maraujo.requestproject.sync.result.Outcome;

/**
 * Created by maraujo on 6/15/17.
 */

public class BaseProxyListener<T> implements ProxyListener<T> {

    private SwipeRefreshLayout swipeRefreshLayout;
    public BaseProxyListener() {
    }

    public BaseProxyListener(SwipeRefreshLayout swipeRefreshLayout) {
    }

    @Override
    public void onStart() {
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onSuccess(T response) {

    }

    public <E extends Outcome> boolean hasSuccess(E response) {

        return response == null || response.hasSuccess();
    }

    @Override
    public void onFailure(VolleyError error) {

    }

    @Override
    public void onComplete() {
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }
}
