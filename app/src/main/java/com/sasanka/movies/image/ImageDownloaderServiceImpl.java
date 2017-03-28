package com.sasanka.movies.image;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Specifies implementation corresponding to image downloader service according to the library used.
 */

public class ImageDownloaderServiceImpl implements ImageDownloaderService {

    private Picasso picasso;

    public ImageDownloaderServiceImpl(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void loadImage(Context context, String url, int errorDrawable, ImageView target) {
        picasso.with(context)
                .load(url)
                .error(errorDrawable)
                .into(target);
    }

}
