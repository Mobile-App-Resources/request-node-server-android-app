package com.maraujo.requestproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.maraujo.requestproject.models.Country;
import com.maraujo.requestproject.models.Post;
import com.maraujo.requestproject.services.CountryService;
import com.maraujo.requestproject.services.PostService;
import com.maraujo.requestproject.sync.listeners.BaseProxyListener;
import com.maraujo.requestproject.sync.requests.toolbox.MultiPartProgressItem;
import com.maraujo.requestproject.sync.result.Outcome;
import com.maraujo.requestproject.utils.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private PostService service;
    private CountryService countryService;

    private ImageView logoImage;

    private InputStream pdf;
    private InputStream devices;
    private InputStream facebookIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoImage = (ImageView) this.findViewById(R.id.imageView);

        service = new PostService(this);
        countryService = new CountryService(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        View currentView = this.findViewById(R.id.content);

        service.findAllPosts(currentView, new BaseProxyListener<ArrayList<Post>>() {
            @Override
            public void onSuccess(ArrayList<Post> response) {
                if(response != null) {

                }
            }
        });

        service.getPostsByUserId(1, currentView, new BaseProxyListener<Post>() {
            @Override
            public void onSuccess(Post response) {
                if(response != null) {

                }
            }
        });

        countryService.getCountryes(currentView, new BaseProxyListener<Outcome<ArrayList<Country>>>() {
            @Override
            public void onSuccess(Outcome<ArrayList<Country>> response) {
                if(super.hasSuccess(response)) {
                    ArrayList<Country> result = response.result();
                }
            }
        });

        countryService.getBrazilCountry(currentView, new BaseProxyListener<Outcome<Country>>() {
            @Override
            public void onSuccess(Outcome<Country> response) {
                if(super.hasSuccess(response)) {
                    Country country = response.result();
                }
            }
        });

        countryService.getBrazilCountryFlag(currentView, new BaseProxyListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap response) {
                if(response != null) {
                    logoImage.setImageBitmap(response);
                }
            }
        });

    }

    public void uploadFileClick(View view) {

        this.devices = getResources().openRawResource(R.raw.devices);
        this.pdf = getResources().openRawResource(R.raw.list_file);
        this.facebookIcon = getResources().openRawResource(R.raw.facebook);


        View currentView = this.findViewById(R.id.content);

        File pdfFile = new File(this.getFilesDir() + File.separator + "pdfFile.pdf");
        File devicesFile = new File(this.getFilesDir() + File.separator + "devices.png");

        FileUtils.rawToFile(pdfFile, pdf);
        FileUtils.rawToFile(devicesFile, devices);

        countryService.uploadFile(pdfFile, currentView, new BaseProxyListener<Country>() {
            @Override
            public void onSuccess(Country response) {
                super.onSuccess(response);
            }
        });

        countryService.uploadFiles(devicesFile, facebookIcon, currentView, progressMultipartItem(), new BaseProxyListener<Country>() {
            @Override
            public void onSuccess(Country response) {
                super.onSuccess(response);
            }
        });
    }

    private MultiPartProgressItem progressMultipartItem() {
        return new MultiPartProgressItem() {
            @Override
            public void updateProgress(String fileKey, long readBytes, long contentLength, long bytesAvailable, int transferredPercent) {
                LogUtils.logInfo("File Progress", String.format("fileKey: %s, readBytes: %d, contentLength: %d, bytesAvailable: %d, transferredPercent: %d", fileKey, readBytes, contentLength, bytesAvailable, transferredPercent) );
            }
        };
    }
}
