package com.maraujo.requestproject.services.request;

import com.maraujo.requestproject.R;
import com.maraujo.requestproject.app.App;

/**
 * Created by Mateus Emanuel Araújo on 23/10/15.
 * teusemanuel@gmail.com
 */
public class Urls {

    public static String findAllPosts() {
        return String.format(getUrlApi2(), "posts");
    }

    public static String getPostsByUserId(int userId) {
        return String.format(getUrlApi2(), String.format("posts/%d", userId));
    }

    public static String getCountryByName() {
        return String.format(getUrlApi(), "country/all");
    }

    public static String getCountryById() {
        return String.format(getUrlApi(), String.format("country/%d", 23));
    }

    public static String getCountryFlag() {
        return String.format(getUrlApi(), "resource?resourcePath=brazil");
    }

    public static String uploadFiles() {
        return String.format(getUrlApi(), "resource");
    }

    private static String getUrlApi2() {
        return App.getInstance().getResources().getString(R.string.app_url_2);
    }

    private static String getUrlApi() {
        return App.getInstance().getResources().getString(R.string.app_url);
    }
}
