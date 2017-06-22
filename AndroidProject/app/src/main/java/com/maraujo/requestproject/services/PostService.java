package com.maraujo.requestproject.services;

import android.app.Activity;
import android.view.View;

import com.android.volley.Request;
import com.maraujo.requestproject.models.Post;
import com.maraujo.requestproject.services.request.Urls;
import com.maraujo.requestproject.sync.AbstractProxy;
import com.maraujo.requestproject.sync.listeners.ProxyListener;

import java.util.ArrayList;

/**
 * Created by maraujo on 6/15/17.
 */

public class PostService extends AbstractProxy {

    public PostService(Activity activity) {
        super(activity);
    }

    public void getPostsByUserId(int userId, View view, ProxyListener<Post> proxyListener) {
        createAndScheduleGsonRequest(Request.Method.GET, Urls.getPostsByUserId(userId), Post.class, null, null, proxyListener, view);
    }

    public void findAllPosts(View view, ProxyListener<ArrayList<Post>> proxyListener) {
        createAndScheduleGsonListRequest(Request.Method.GET, Urls.findAllPosts(), Post.class, null, null, proxyListener, view);
    }

    @Override
    public String getTag() {
        return "PostService";
    }
}
