package com.sasanka.movies.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Exposes apis related to image downloading and displaying. Apis would remain the same irrespective of the
 * image downloader library used. Only its implementation would change corresponding to the library used.
 */

public interface ImageDownloaderService {
    void loadImage(Context context, String url, int errorDrawable, ImageView target);
}
