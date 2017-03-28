package com.sasanka.movies.image;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Specified the way to create objects for image downloader service and image downloader library.
 */

@Module
public class ImageDownloaderModule {

    private Context context;

    public ImageDownloaderModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ImageDownloaderService provideImageDownloaderService(Picasso picasso) {
        return new ImageDownloaderServiceImpl(picasso);
    }

    @Provides
    @Singleton
    public Picasso providePicasso() {
        Picasso.Builder builder = new Picasso.Builder(context);
        return builder.build();
    }

    @Provides
    @Singleton
    public ImageDownloaderManager getImageDownloaderManager(ImageDownloaderService imageDownloaderService) {
        return new ImageDownloaderManager(imageDownloaderService);
    }

}
