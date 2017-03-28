package com.sasanka.movies.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * This Class handles all actions related to downloading and displaying images.
 * Acts as a wrapper around image downloader service.
 */

public class ImageDownloaderManager {

    private ImageDownloaderService imageDownloaderService;

    public ImageDownloaderManager(ImageDownloaderService imageDownloaderService) {
        this.imageDownloaderService = imageDownloaderService;
    }

    public void loadImage(Context context, String url, int errorDrawable, ImageView target) {
        imageDownloaderService.loadImage(context, url, errorDrawable, target);
    }

}
