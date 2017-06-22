package com.maraujo.requestproject.services;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.maraujo.requestproject.models.Country;
import com.maraujo.requestproject.services.request.Urls;
import com.maraujo.requestproject.sync.AbstractProxy;
import com.maraujo.requestproject.sync.listeners.ProxyListener;
import com.maraujo.requestproject.sync.requests.toolbox.MultiPartProgressItem;
import com.maraujo.requestproject.sync.requests.toolbox.MultipartBody;
import com.maraujo.requestproject.sync.result.Outcome;
import com.maraujo.requestproject.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by maraujo on 6/19/17.
 */

public class CountryService extends AbstractProxy {

    public CountryService(Activity activity) {
        super(activity);
    }

    public void getCountryes(View view, ProxyListener<Outcome<ArrayList<Country>>> proxyListener) {

        Class<?> countryList = new ArrayList<Country>() {}.getClass();
        createAndScheduleGsonOutcomeRequest(Request.Method.GET, Urls.getCountryByName(), countryList, null, null, proxyListener, view);
    }

    public void getBrazilCountry(View view, ProxyListener<Outcome<Country>> proxyListener) {

        createAndScheduleGsonOutcomeRequest(Request.Method.GET, Urls.getCountryById(), Country.class, null, null, proxyListener, view);
    }

    public void getBrazilCountryFlag(View view, ProxyListener<Bitmap> proxyListener) {

        createAndScheduleImageRequest(Urls.getCountryFlag(), proxyListener, view);
    }

    public void uploadFile(File file, View view, ProxyListener<Country> proxyListener) {

        createAndScheduleMultipartRequest(Request.Method.POST, Urls.uploadFiles(), Country.class, file, null, proxyListener, view);
    }

    public void uploadFiles(File file1, InputStream file2, View view, MultiPartProgressItem progress2, ProxyListener<Country> proxyListener) {

        MultipartBody body = MultipartBody.create();

        try {
            // send file
            body.addImage("devices", file1.getName(), file1);
            body.addFile("facebook", "facebook.svg", null, file2, progress2);

            // Sending text in the same request as the files
            body.addText("testKey", "Mateus E.");
            body.addText("testKey2", "Mateus E. Araújo");

        } catch (IOException ex) {
            LogUtils.logError("IOException writing to FileInputStream stream, building the multipart request.", ex);
            proxyListener.onFailure(new VolleyError(ex));
        }

        createAndScheduleMultipartRequest(Request.Method.POST, Urls.uploadFiles(), Country.class, body, null, proxyListener, view);
    }

    @Override
    public String getTag() {
        return "CountryService";
    }
}
