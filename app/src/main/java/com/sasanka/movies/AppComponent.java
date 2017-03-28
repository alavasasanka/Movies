package com.sasanka.movies;

import android.content.SharedPreferences;

import com.sasanka.movies.data.api.ApiModule;
import com.sasanka.movies.image.ImageDownloaderModule;
import com.sasanka.movies.ui.activity.component.MainActivityComponent;
import com.sasanka.movies.ui.activity.module.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * The top level component which uses 3 modules with each handling key features.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                ApiModule.class,
                ImageDownloaderModule.class
        }
)
public interface AppComponent {
    MainActivityComponent getActivityComponent(MainActivityModule module);
    SharedPreferences getSharedPreferences();
}