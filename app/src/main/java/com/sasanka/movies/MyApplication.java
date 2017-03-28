package com.sasanka.movies;

import android.app.Application;
import android.content.Context;

import com.sasanka.movies.image.ImageDownloaderModule;


public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .imageDownloaderModule(new ImageDownloaderModule(this))
                .build();
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
